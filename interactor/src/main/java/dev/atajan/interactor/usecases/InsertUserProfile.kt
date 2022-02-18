package dev.atajan.interactor.usecases

import com.github.michaelbull.result.Result
import dev.atajan.domain.UserProfile

interface InsertUserProfile {
    suspend operator fun invoke(userProfile: UserProfile): Result<UserProfile, Throwable>
}