package dev.atajan.live.database.internal

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.atajan.common.ClassLogger
import dev.atajan.domain.UserProfile
import dev.atajan.live.database.AppDatabase
import dev.atajan.live.database.UserDatabase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor

internal class UserDatabaseImpl(private val appDatabase: AppDatabase) : UserDatabase {

    private val logger = ClassLogger(this.javaClass.name)

    private val scope = CoroutineScope(Dispatchers.Default)

    /**
     * Thread-safe concurrency
     */
    private val actor = scope.actor<Intention>(capacity = Channel.UNLIMITED) {
        for (intention in channel) {
            logger.logDebug("intention: $intention")

            when (intention) {
                is Intention.GetUserWithEmail -> {
                    val result = try {
                        val profile = appDatabase.appDatabaseQueries
                            .getUserWithEmail(intention.email)
                            .executeAsOne()
                            .toUserProfile()

                        Ok(profile)
                    } catch (exception: Exception) {
                        logger.logError("Exception during GetUserWithEmail: $exception")
                        Err(exception)
                    }

                    intention.deferredUserProfile.complete(result)
                }

                is Intention.UpsertUser -> {
                    intention.user.let { profile ->

                        val result = try {
                            appDatabase.appDatabaseQueries
                                .upsertUser(
                                    email = profile.email,
                                    name = profile.name,
                                    website = profile.website,
                                    password = profile.password,
                                    longLiveToken = profile.longLiveToken
                                )

                            Ok(profile)
                        } catch (exception: Exception) {
                            logger.logError("Exception during UpsertUser: $exception")
                            Err(exception)
                        }

                        intention.deferredUserProfile.complete(result)
                    }
                }
            }
        }
    }

    override suspend fun getUserWithEmail(email: String): Result<UserProfile, Throwable> {
        val deferredUserProfile = CompletableDeferred<Result<UserProfile, Throwable>>()

        actor.trySend(
            Intention.GetUserWithEmail(
                deferredUserProfile = deferredUserProfile,
                email = email
            )
        )

        return deferredUserProfile.await()
    }

    override suspend fun upsertUser(user: UserProfile): Result<UserProfile, Throwable> {
        val deferredUserProfile = CompletableDeferred<Result<UserProfile, Throwable>>()

        actor.trySend(
            Intention.UpsertUser(
                deferredUserProfile = deferredUserProfile,
                user = user
            )
        )

        return deferredUserProfile.await()
    }

    sealed class Intention {
        data class GetUserWithEmail(
            val deferredUserProfile: CompletableDeferred<Result<UserProfile, Throwable>>,
            val email: String
        ) : Intention()

        data class UpsertUser(
            val deferredUserProfile: CompletableDeferred<Result<UserProfile, Throwable>>,
            val user: UserProfile
        ) : Intention()
    }
}