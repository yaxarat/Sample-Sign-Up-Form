package dev.atajan.interactor.internal.usecases

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import dev.atajan.domain.UserProfile
import dev.atajan.test.FakeUserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Test uses cases
 */
class GetUserProfileImplTest {

    private fun withTestDelegate(testBody: suspend TestDelegate.() -> Unit) = runBlocking {
        testBody(TestDelegate(this))
    }

    @Test
    fun `GetUserProfile - if row exist - return the profile`() = withTestDelegate {
        givenGetUserWithEmailSuccess()
        thenGetUserProfileShouldReturnTheProfile()
    }

    @Test
    fun `GetUserProfile - if row does not exist - throw`() = withTestDelegate {
        givenGetUserWithEmailSuccess(false)
        thenGetUserProfileShouldThrow()
    }

    @Test
    fun `InsertUserProfile - if operation succeed - return the profile`() = withTestDelegate {
        givenUpsertProfileSuccess()
        thenInsertUserProfileShouldReturnTheProfile()
    }

    @Test
    fun `InsertUserProfile - if row does not exist - throw`() = withTestDelegate {
        givenUpsertProfileSuccess(false)
        thenInsertUserProfileShouldThrow()
    }

    private class TestDelegate(scope: CoroutineScope) : CoroutineScope by scope {
        val database = FakeUserDatabase()

        val getProfileUseCase = GetUserProfileImpl(database)
        val insertProfileUseCase = InsertUserProfileImpl(database)

        fun givenGetUserWithEmailSuccess(isSuccess: Boolean = true) = apply {
            database.givenOperationSuccess(isSuccess)
            if (isSuccess) database.givenProfile(userProfile)
        }

        fun givenUpsertProfileSuccess(isSuccess: Boolean = true) = apply {
            database.givenOperationSuccess(isSuccess)
        }

        fun thenGetUserProfileShouldReturnTheProfile() = apply {
            runBlocking {
                val actual = getProfileUseCase(userProfile.email)
                assertEquals(Ok(userProfile), actual)
            }
        }

        fun thenGetUserProfileShouldThrow() = apply {
            runBlocking {
                val actual = getProfileUseCase(userProfile.email)
                assertTrue(actual.component2() is Exception)
            }
        }

        fun thenInsertUserProfileShouldReturnTheProfile() = apply {
            runBlocking {
                val actual = insertProfileUseCase(userProfile)
                assertEquals(Ok(userProfile), actual)
            }
        }

        fun thenInsertUserProfileShouldThrow() = apply {
            runBlocking {
                val actual = insertProfileUseCase(userProfile)
                assertTrue(actual.component2() is Exception)
            }
        }

        companion object {
            private val anException = Exception()
            private val userProfile = UserProfile(
                email = "email@address.com",
                name = "Yashar",
                website = "dev.atajan.com",
                password = "password",
                longLiveToken = "longLiveToken"
            )
        }
    }
}