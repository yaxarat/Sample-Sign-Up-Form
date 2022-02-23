package dev.atajan.signupform.screens.registration

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.atajan.common.ClassLogger
import dev.atajan.domain.UserProfile
import dev.atajan.interactor.UserProfileUseCases
import dev.atajan.signupform.R
import dev.atajan.signupform.common.UserInput
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountRegistrationViewModel
@Inject
constructor(private val userProfileUseCases: UserProfileUseCases) : ViewModel() {

    private val logger = ClassLogger(this.javaClass.name)

    /**
     * MVI/Redux architecture set up:
     * Intent is emitted to actor, which is then reduced in reducer, which returns a new state.
     *
     *  user action -> [onIntention -> actor -> reducer -> newState] -> user
     */

    var state = mutableStateOf(State.initialState())
        private set

    private val actor = viewModelScope.actor<Intention>(capacity = Channel.UNLIMITED) {
        for (intention in channel) {
            state.value = reduce(
                currentState = state.value,
                intention = intention
            )
        }
    }

    fun onIntention(intention: Intention) {
        actor.trySend(intention)
    }

    private fun reduce(currentState: State, intention: Intention): State {
        logger.logDebug("state: $currentState \nintention: $intention")
        return when (intention) {
            is Intention.UpdateEmail -> currentState.copy(email = intention.email)
            is Intention.UpdateName -> currentState.copy(name = intention.name)
            is Intention.UpdatePassword -> currentState.copy(password = intention.password)
            is Intention.UpdateWebsite -> currentState.copy(website = intention.website)
            is Intention.SaveUserInformation -> currentState.also { state ->
                attemptSaveUserInformation(
                    state = state,
                    onSaveSuccess = intention.onSuccess
                )
            }
        }
    }

    /**
     * Intent actions
     */

    private fun attemptSaveUserInformation(state: State, onSaveSuccess: () -> Unit) {
        val email = state.email.value.trim()
        val password = state.password.value
        val url = state.website.value.trim()

        val fieldsAreValid = validateFields(
            email = email,
            password = password,
            website = url
        )

        val formattedUrl = url.let {
            when {
                ((it.isNotEmpty() && !it.startsWith("https://"))) -> {
                    "https://$it"
                }
                it.isEmpty() -> {
                    ""
                }
                else -> {
                    it
                }
            }
        }

        if (fieldsAreValid) {
            viewModelScope.launch {
                userProfileUseCases.insertUserProfile.invoke(
                    UserProfile(
                        name = state.name,
                        email = state.email.value,
                        website = formattedUrl,
                        password = state.password.value, // NEVER DO THIS IN A REAL PROD APP
                        longLiveToken = ""
                    )
                ).mapBoth(
                    success = {
                        logger.logDebug("Profile saved")
                        onSaveSuccess()
                    },
                    failure = {
                        logger.logDebug("attemptSaveUserInformation failed: $it")
                    }
                )
            }
        }
    }

    private fun validateFields(
        email: String,
        password: String,
        website: String
    ): Boolean {
        val validEmail = email.isEmailValid()
            .also { valid ->
                if (!valid) {
                    onIntention(
                        Intention.UpdateEmail(
                            UserInput(email, RegistrationInputError.InvalidEmail)
                        )
                    )
                }
            }

        val validPassword = password.isPasswordValid()
            .also { valid ->
                if (!valid) {
                    onIntention(
                        Intention.UpdatePassword(
                            UserInput("", RegistrationInputError.InvalidPassword)
                        )
                    )
                }
            }

        val validWebUrlIfEntered = website
            .isWebUrlValid()
            .also { valid ->
                if (!valid) {
                    onIntention(
                        Intention.UpdateWebsite(
                            UserInput(website, RegistrationInputError.InvalidWebUrl)
                        )
                    )
                }
            }

        return validEmail && validPassword && validWebUrlIfEntered
    }

    private fun String.isWebUrlValid(): Boolean {
        return if (this.isNotEmpty()) {
            Patterns.WEB_URL.matcher(this).matches()
        } else {
            true
        }
    }

    private fun String.isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun String.isPasswordValid(): Boolean {
        return this.length >= 8
    }

    /**
     * State holders
     */

    data class State(
        val name: String,
        val email: UserInput<String, RegistrationInputError>,
        val password: UserInput<String, RegistrationInputError>,
        val website: UserInput<String, RegistrationInputError>
    ) {
        companion object {
            fun initialState(): State {
                return State(
                    name = "",
                    email = UserInput(""),
                    password = UserInput(""),
                    website = UserInput("")
                )
            }
        }
    }

    sealed class RegistrationInputError(val message: Int) {
        object InvalidPassword : RegistrationInputError(R.string.password_field_error_text)
        object InvalidEmail : RegistrationInputError(R.string.email_field_error_text)
        object InvalidWebUrl : RegistrationInputError(R.string.website_field_error_text)
    }

    sealed class Intention {
        data class UpdateName(val name: String) : Intention()
        data class UpdateEmail(val email: UserInput<String, RegistrationInputError>) : Intention()
        data class UpdatePassword(val password: UserInput<String, RegistrationInputError>) : Intention()
        data class UpdateWebsite(val website: UserInput<String, RegistrationInputError>) : Intention()
        data class SaveUserInformation(val onSuccess: () -> Unit) : Intention()
    }
}