package dev.atajan.common

import java.util.logging.Level
import java.util.logging.Logger

/**
 * A common logging class that will be used for all modules.
 * Normally for a production app, we would want to include a flag that checks if the app is in a debug mode.
 * Since this app will never be in prod, that flag and accompanying factory builders are omitted.
 *
 * @param tag String that will uniquely identify the log/call site.
 */
class ClassLogger(tag: String) {

    private val logger = Logger.getLogger(tag)

    fun logDebug(message: String) {
        logger.log(Level.INFO, message)
    }

    fun logWarn(message: String) {
        logger.log(Level.WARNING, message)
    }

    fun logError(message: String) {
        logger.log(Level.SEVERE, message)
    }
}