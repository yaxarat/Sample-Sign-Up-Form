package dev.atajan.live.database.internal

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.atajan.domain.UserProfile
import dev.atajan.live.database.AppDatabase
import dev.atajan.live.database.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Test [UserDatabaseImpl] to make sure our intention/response code is working correctly.
 */
class UserDatabaseImplTest {

    private fun withTestDelegate(testBody: suspend TestDelegate.() -> Unit) = runBlocking {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
            AppDatabase.Schema.create(it)
        }

        val userDatabase = UserDatabaseImpl(AppDatabase(driver))

        testBody(
            TestDelegate(
                userDatabase = userDatabase,
                scope = this
            )
        )
    }

    @Test
    fun `UserDatabase - given existing email - associated user returned`() = withTestDelegate {
        givenUserInsertedInDatabase()
        thenCorrectProfileReturned(true)
    }

    @Test
    fun `UserDatabase - given non-existing email - should throw`() = withTestDelegate {
        givenUserInsertedInDatabase(false)
        thenCorrectProfileReturned(false)
    }

    @Test
    fun `UserDatabase - given upsert success - original profile returned`() = withTestDelegate {
        upsertSucceeded()
    }

    private class TestDelegate(
        private val userDatabase: UserDatabase,
        scope: CoroutineScope
    ) : CoroutineScope by scope {

        fun givenUserInsertedInDatabase(userExistInDatabase: Boolean = true) = apply {
            runBlocking {
                if (userExistInDatabase) userDatabase.upsertUser(userProfile)
            }
        }

        fun thenCorrectProfileReturned(correctProfileReturned: Boolean = true) = apply {
            runBlocking {
                val result = userDatabase.getUserWithEmail(email)

                if (correctProfileReturned) {
                    assertEquals(userProfile, result.component1())
                } else {
                    assertTrue(result.component2() is Exception)
                }
            }
        }

        fun upsertSucceeded() = apply {
            runBlocking {
                userDatabase.upsertUser(userProfile).apply {
                    assertEquals(userProfile, this.component1())
                }
            }
        }

        companion object {
            private const val email = "email@address.com"
            private val userProfile = UserProfile(
                email = email,
                name = "Yashar",
                website = "dev.atajan.com",
                password = "password",
                longLiveToken = "longLiveToken"
            )
        }
    }
}