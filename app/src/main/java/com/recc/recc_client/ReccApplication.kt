package com.recc.recc_client

import android.app.Application
import com.recc.recc_client.di.httpModule
import com.recc.recc_client.di.screenViewModels
import com.recc.recc_client.di.sharedPreferences
import com.recc.recc_client.di.spotify
import com.recc.recc_client.utils.Status
import com.spotify.android.appremote.api.SpotifyAppRemote
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import se.michaelthelin.spotify.SpotifyApi

class ReccApplication: Application() {
    var spotifyApiOld: SpotifyAppRemote? = null
    var spotifyApi: SpotifyApi? = null

    override fun onCreate() {
        super.onCreate()
        Status("Launching ReccApplication...")
        startKoin {
            androidContext(applicationContext)
            modules(
                httpModule,
                screenViewModels,
                spotify,
                sharedPreferences
            )
        }
    }
}