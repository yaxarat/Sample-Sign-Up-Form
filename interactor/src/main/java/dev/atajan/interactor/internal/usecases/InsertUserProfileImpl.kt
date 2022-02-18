package dev.atajan.interactor.internal.usecases

import com.github.michaelbull.result.Result
import dev.atajan.domain.UserProfile
import dev.atajan.interactor.usecases.InsertUserProfile
import dev.atajan.live.database.UserDatabase

internal class InsertUserProfileImpl(private val database: UserDatabase): InsertUserProfile {

    override suspend fun invoke(userProfile: UserProfile): Result<UserProfile, Throwable> = database.upsertUser(userProfile)
}