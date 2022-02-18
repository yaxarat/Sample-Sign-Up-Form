package dev.atajan.interactor.usecases

import com.github.michaelbull.result.Result
import dev.atajan.domain.UserProfile

interface GetUserProfile {
    suspend operator fun invoke(email: String): Result<UserProfile, Throwable>
}