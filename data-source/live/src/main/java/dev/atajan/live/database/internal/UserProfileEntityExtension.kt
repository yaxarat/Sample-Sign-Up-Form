package dev.atajan.live.database.internal

import dev.atajan.domain.UserProfile
import dev.atajan.live.database.UserProfileEntity

/**
 * Convenience function to convert from database entity to domain object.
 */
internal fun UserProfileEntity.toUserProfile(): UserProfile {
    return UserProfile(
        name = name,
        email = email,
        website = website,
        password = password,
        longLiveToken = longLiveToken
    )
}