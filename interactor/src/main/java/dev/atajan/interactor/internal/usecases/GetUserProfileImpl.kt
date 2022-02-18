package dev.atajan.interactor.internal.usecases

import com.github.michaelbull.result.Result
import dev.atajan.domain.UserProfile
import dev.atajan.interactor.usecases.GetUserProfile
import dev.atajan.live.database.UserDatabase

internal class GetUserProfileImpl(private val database: UserDatabase): GetUserProfile {

    override suspend fun invoke(email: String): Result<UserProfile, Throwable> = database.getUserWithEmail(email)
}