package dev.atajan.domain

/**
 * A data class that represents an instance of a user.
 *
 * We ned to be careful when logging data classes that contain sensitive info.
 * Since it will show up in a stack trace, we should obfuscate the data when sending it to
 * logging services like crashlytics.
 */
data class UserProfile(
    val name: String,
    val email: String,
    val website: String,
    val password: String,
    val longLiveToken: String
)