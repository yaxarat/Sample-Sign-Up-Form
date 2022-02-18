package dev.atajan.live.database

import com.github.michaelbull.result.Result
import com.squareup.sqldelight.db.SqlDriver
import dev.atajan.live.database.internal.UserDatabaseImpl
import dev.atajan.domain.UserProfile

/**
 * We abstract away the actual implementation for loose coupling to the database/backend we use.
 * This also allows easier testing via fakes.
 */
interface UserDatabase {

    /**
     * Returns [UserProfile] associated with this email wrapped in [Result].
     * [Result] will contain [Throwable] if there was any error during the operation.
     */
    suspend fun getUserWithEmail(email: String): Result<UserProfile, Throwable>

    suspend fun upsertUser(user: UserProfile): Result<UserProfile, Throwable>

    companion object {
        const val APP_DATABASE_NAME = "AppDatabase"
        val sqlDriverSchema = AppDatabase.Schema

        /**
         * Platform agnostic factory for UserDatabase.
         * By passing specific sql driver, we can create user database
         * that will work in other platforms, such as iOS.
         */
        fun build(sqlDriver: SqlDriver): UserDatabase {
            return UserDatabaseImpl(AppDatabase(sqlDriver))
        }
    }
}