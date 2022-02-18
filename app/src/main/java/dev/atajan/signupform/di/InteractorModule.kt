package dev.atajan.signupform.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.atajan.interactor.UserProfileUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun providesUserProfileUseCases(driver: AndroidSqliteDriver): UserProfileUseCases {
        return UserProfileUseCases.build(driver)
    }
}