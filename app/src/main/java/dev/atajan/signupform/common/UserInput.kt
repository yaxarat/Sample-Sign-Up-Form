package dev.atajan.signupform.common

data class UserInput<T, V>(
    val value: T,
    val error: V? = null
)
