package com.weesnerdevelopment.serialcabinet.middleware

import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit

/**
 * Maps the given string to a "Auth Bearer" string.
 */
val String.asBearer get() = "Bearer $this"

/**
 * Save the [key]: [value] pair to shared preferences.
 */
fun SharedPreferences.saveItem(key: String, value: String) = edit { putString(key, value) }

/**
 * get the item from shared preferences matching the given [key].
 */
fun SharedPreferences.getItem(key: String) = getString(key, "")

/**
 * Deletes the item with the given [key] from shared preferences.
 */
fun SharedPreferences.removeItem(key: String) = edit { remove(key) }

/**
 * Base Url for the backend.
 */
const val BASE_URL = "http://api.weesnerdevelopment.com"

/**
 * SerialCabinet base endpoint.
 */
const val SERIAL_CABINET_URL = "$BASE_URL/serialCabinet/"

/**
 * Decodes the auth token to get the needed user info out of it.
 */
fun getEncodedUserFromJwt(jwt: String): Pair<String, String>? {
    if (jwt.isBlank()) return null

    try {
        val parts = jwt.split(".")
        val tokenBytes = parts[1].toByteArray(charset("UTF-8"))
        val userInfoFromToken = String(Base64.decode(tokenBytes, Base64.DEFAULT)).split(",")
            .filter { it.contains("attr-") }
        val userInfo = userInfoFromToken.map { it.split(":")[1].replace("\"".toRegex(), "") }
        return userInfo[0] to userInfo[1]
    } catch (e: Exception) {
        throw RuntimeException("Couldnt decode jwt $jwt", e)
    }
}