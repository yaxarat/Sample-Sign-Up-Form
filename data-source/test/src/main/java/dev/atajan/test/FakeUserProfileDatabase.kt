package dev.atajan.test

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.atajan.domain.UserProfile
import dev.atajan.live.database.UserDatabase

class FakeUserDatabase : UserDatabase {

    private lateinit var userProfile: UserProfile

    private var operationSuccess: Boolean = true

    /**
     * Returns [UserProfile] associated with this email wrapped in [Result].
     * [Result] will contain [Throwable] if there was any error during the operation.
     */
    override suspend fun getUserWithEmail(email: String): Result<UserProfile, Throwable> {
        return if (operationSuccess) Ok(userProfile) else Err(NullPointerException())
    }

    /**
     * Returns [UserProfile] initially passed wrapped in [Result] if the upsert operation succeeds.
     * [Result] will contain [Throwable] if there was any error during the operation.
     */
    override suspend fun upsertUser(user: UserProfile): Result<UserProfile, Throwable> {
        return if (operationSuccess) Ok(user) else Err(Exception())
    }

    fun givenOperationSuccess(isSuccess: Boolean = true) {
        operationSuccess = isSuccess
    }

    fun givenProfile(profile: UserProfile) {
        userProfile = profile
    }
}