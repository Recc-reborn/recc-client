package com.recc.recc_client.utils

import android.content.Context
import android.content.SharedPreferences
import com.recc.recc_client.http.impl.Spotify
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val CACHE_KEYS_FILENAME = "shared_preferences_auth"
private const val AUTH_TOKEN_KEY = "auth_token_file"
private const val SPOTIFY_STATUS_KEY = "spotify_status_key"
private const val SPOTIFY_ACCESS_TOKEN = "spotify_access_token"
private const val SPOTIFY_USER_ID = "spotify_user_id"

class SharedPreferences(
    private val context: Context,
    private val spotifyApi: Spotify) {
    private fun getSharedPref(): SharedPreferences = context.getSharedPreferences(
        CACHE_KEYS_FILENAME,
        Context.MODE_PRIVATE
    )

    fun getToken(): String = getSharedPref().getString(AUTH_TOKEN_KEY, null).orEmpty()

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
        CoroutineScope(Dispatchers.IO).launch {
            spotifyApi.me(token)
                .onSuccess {
                    saveSpotifyUserId(it.id)
                }.onFailure {
                    Alert("Error retrieving Spotify me data")
                }
        }
    }

    fun saveSpotifyUserId(id: String) {
        with (getSharedPref().edit()) {
            this?.putString(SPOTIFY_USER_ID, id)
            this?.apply()
        }
    }

    fun getSpotifyUserId(): String = getSharedPref().getString(SPOTIFY_USER_ID, null).orEmpty()

    fun removeSpotifyStatus() {
        with(getSharedPref().edit()) {
            this?.remove(SPOTIFY_STATUS_KEY)
            this?.apply()
        }
    }
}