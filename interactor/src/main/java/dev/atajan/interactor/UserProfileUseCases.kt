package dev.atajan.interactor

import com.squareup.sqldelight.db.SqlDriver
import dev.atajan.interactor.internal.usecases.GetUserProfileImpl
import dev.atajan.interactor.internal.usecases.InsertUserProfileImpl
import dev.atajan.interactor.usecases.GetUserProfile
import dev.atajan.interactor.usecases.InsertUserProfile
import dev.atajan.live.database.UserDatabase

data class UserProfileUseCases(
    val insertUserProfile: InsertUserProfile,
    val getUserProfile: GetUserProfile
) {
    companion object {
        val schema = UserDatabase.sqlDriverSchema
        const val databaseName = UserDatabase.APP_DATABASE_NAME

        fun build(sqlDriver: SqlDriver): UserProfileUseCases {
            val database = UserDatabase.build(sqlDriver)
            val insertUserProfile = InsertUserProfileImpl(database)
            val getUserProfile = GetUserProfileImpl(database)

            return UserProfileUseCases(
                insertUserProfile = insertUserProfile,
                getUserProfile = getUserProfile
            )
        }
    }
}
