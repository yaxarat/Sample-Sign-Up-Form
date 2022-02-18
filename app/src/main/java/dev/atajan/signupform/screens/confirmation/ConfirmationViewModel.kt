package dev.atajan.signupform.screens.confirmation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.atajan.common.ClassLogger
import dev.atajan.interactor.UserProfileUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val userProfileUseCases: UserProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val logger = ClassLogger(this.javaClass.name)

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

    init {
        savedStateHandle.get<String>("email").let { email ->
            if (email != null) {
                logger.logDebug("savedStateHandle returned email: $email")
                onIntention(Intention.GetUserWithEmail(email))
            } else {
                logger.logError("savedStateHandle returned null for key: email")
                onIntention(Intention.EmailNotProvided)
            }
        }
    }

    fun onIntention(intention: Intention) {
        actor.trySend(intention)
    }

    private fun reduce(currentState: State, intention: Intention): State {
        logger.logDebug("state: $currentState \nintention: $intention")
        return when (intention) {
            is Intention.GetUserWithEmail -> {
                getUserWithEmail(intention.email)
                currentState
            }
            is Intention.UserLoaded -> {
                currentState.copy(
                    name = intention.name,
                    email = intention.email,
                    website = intention.website,
                    isLoading = false
                )
            }
            Intention.CouldNotFindUser -> currentState.copy(showError = true)
            Intention.EmailNotProvided -> currentState.copy(showError = true)
        }
    }

    /**
     * Intent actions
     */

    private fun getUserWithEmail(email: String) {
        viewModelScope.launch {
            userProfileUseCases.getUserProfile(email)
                .mapBoth(
                    success = { retrievedProfile ->
                        onIntention(
                            Intention.UserLoaded(
                                name = retrievedProfile.name,
                                email = retrievedProfile.email,
                                website = retrievedProfile.website,
                            )
                        )
                    },
                    failure = {
                        onIntention(Intention.CouldNotFindUser)
                    }
                )
        }
    }

    /**
     * State holders
     */

    data class State(
        val name: String,
        val email: String,
        val website: String,
        val isLoading: Boolean,
        val showError: Boolean
    ) {
        companion object {
            fun initialState(): State {
                return State(
                    name = "",
                    email = "",
                    website = "",
                    isLoading = true,
                    showError = false
                )
            }
        }
    }

    sealed class Intention {
        data class GetUserWithEmail(val email: String) : Intention()
        data class UserLoaded(
            val name: String,
            val email: String,
            val website: String,
        ) : Intention()
        object CouldNotFindUser: Intention()
        object EmailNotProvided: Intention()
    }
}