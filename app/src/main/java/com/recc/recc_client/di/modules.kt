package com.recc.recc_client.di

import com.google.gson.GsonBuilder
import com.recc.recc_client.BuildConfig
import com.recc.recc_client.R
import com.recc.recc_client.http.ErrorInterceptor
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.http.def.LastFmRouteDefinitions
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.http.impl.LastFm
import com.recc.recc_client.layout.auth.LoginViewModel
import com.recc.recc_client.layout.auth.RegisterViewModel
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.user_msg.UserMsgViewModel
import com.recc.recc_client.layout.welcome.WelcomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT: Long = 5

/*** Koin module for Data injection, it creates both singletons and factories that can be used in the
 * entirety of the application ***/
val screenViewModels = module {
    single {
        UserMsgViewModel()
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        WelcomeViewModel(get())
    }
}

val httpModule = module {
    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ErrorInterceptor(get()))
            .build()
    }
    // Recc Server client
    single {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .client(get())
            .baseUrl(androidContext().getString(R.string.api_host))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(ServerRouteDefinitions::class.java)
    }
    // Last.fm API client
    single {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .client(get())
            .baseUrl(androidContext().getString(R.string.last_fm_base_endpoint))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(LastFmRouteDefinitions::class.java)
    }
    single {
        Auth(androidContext(), get())
    }
    single {
        LastFm(androidContext(), get())
    }
}