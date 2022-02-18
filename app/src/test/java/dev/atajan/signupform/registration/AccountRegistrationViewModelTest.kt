package dev.atajan.signupform.registration

import dev.atajan.interactor.UserProfileUseCases
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.Intention
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.State
import dev.atajan.test.FakeUserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue

class AccountRegistrationViewModelTest {

    private fun withTestDelegate(testBody: suspend TestDelegate.() -> Unit) = runBlocking {
        testBody(TestDelegate(this))
    }

    /**
     * Tests go here
     */

    private class TestDelegate(scope: CoroutineScope) : CoroutineScope by scope {
        private val database = FakeUserDatabase()
        private val usecase = UserProfileUseCases.buildFake(database)
        private val viewModel = AccountRegistrationViewModel(userProfileUseCases = usecase)
        val currentState = viewModel.state.value

        fun givenIntention(intention: Intention) = apply {
            viewModel.onIntention(intention)
        }

        fun thenStateIs(state: State) = apply {
            assertTrue(state == currentState)
        }

        /**
         * We want to test every intention and state like above
         */
    }
}