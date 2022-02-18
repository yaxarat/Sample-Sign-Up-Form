package dev.atajan.signupform.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.atajan.interactor.UserProfileUseCases
import dev.atajan.signupform.MainApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SqliteModule {

    @Singleton
    @Provides
    fun provideAndroidSqlDriver(app: MainApplication): AndroidSqliteDriver {
        return AndroidSqliteDriver(
            schema = UserProfileUseCases.schema,
            context = app,
            name = UserProfileUseCases.databaseName
        )
    }
}
