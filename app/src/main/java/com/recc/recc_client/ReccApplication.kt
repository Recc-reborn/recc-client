package com.recc.recc_client

import android.app.Application
import com.recc.recc_client.di.httpModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReccApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(httpModule)
        }
    }
}