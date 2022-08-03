package com.recc.recc_client

import android.app.Application
import com.recc.recc_client.di.httpModule
import com.recc.recc_client.di.screenViewModels
import com.recc.recc_client.utils.L
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReccApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        L.status("Launching ReccApplication...")
        startKoin {
            androidContext(applicationContext)
            modules(httpModule, screenViewModels)
        }
    }
}