package com.recc.recc_client.utils

import android.content.Context
import android.content.SharedPreferences

private const val CACHE_KEYS_FILENAME = "shared_preferences_auth"
private const val AUTH_TOKEN_KEY = "auth_token_file"
private const val SPOTIFY_STATUS_KEY = "spotify_status_key"
private const val SPOTIFY_ACCESS_TOKEN = "spotify_access_token"

class SharedPreferences(private val context: Context) {
    private fun getSharedPref(): SharedPreferences = context.getSharedPreferences(
        CACHE_KEYS_FILENAME,
        Context.MODE_PRIVATE
    )

    fun getToken(): String? = getSharedPref().getString(AUTH_TOKEN_KEY, null)

    fun saveToken(token: String) {
        with(getSharedPref().edit()) {
            this?.putString(AUTH_TOKEN_KEY, token)
            this?.apply()
        }
    }

    fun removeToken() {
        with(getSharedPref().edit()) {
            this?.remove(AUTH_TOKEN_KEY)
            this?.apply()
        }
    }

    fun getSpotifyStatus(): Boolean = getSharedPref().getBoolean(SPOTIFY_STATUS_KEY, false)

    fun saveSpotifyStatus(status: Boolean) {
        with (getSharedPref().edit()) {
            this?.putBoolean(SPOTIFY_STATUS_KEY, status)
            this?.apply()
        }
    }

    fun getSpotifyToken(): String = getSharedPref().getString(SPOTIFY_ACCESS_TOKEN, null).orEmpty()

    fun saveSpotifyToken(token: String) {
        with (getSharedPref().edit()) {
            this?.putString(SPOTIFY_ACCESS_TOKEN, token)
            this?.apply()
        }
    }

    fun removeSpotifyStatus() {
        with(getSharedPref().edit()) {
            this?.remove(SPOTIFY_STATUS_KEY)
            this?.apply()
        }
    }
}