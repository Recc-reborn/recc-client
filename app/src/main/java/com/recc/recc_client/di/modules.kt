package com.recc.recc_client.di

import com.google.gson.GsonBuilder
import com.recc.recc_client.BuildConfig
import com.recc.recc_client.R
import com.recc.recc_client.http.ErrorInterceptor
import com.recc.recc_client.http.InterceptorViewModel
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.http.def.MockApiRouteDefinitions
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.MockApi
import com.recc.recc_client.layout.auth.LoginViewModel
import com.recc.recc_client.layout.auth.RegisterViewModel
import com.recc.recc_client.layout.common.MeDataViewModel
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.home.PagerViewModel
import com.recc.recc_client.layout.playlist.PlaylistViewModel
import com.recc.recc_client.layout.settings.SettingsViewModel
import com.recc.recc_client.layout.user_msg.UserMsgViewModel
import com.recc.recc_client.layout.common_views.NoConnectionViewModel
import com.recc.recc_client.layout.welcome.WelcomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT: Long = 2

/*** Koin module for Data injection, it creates both singletons and factories that can be used in the
 * entirety of the application ***/
val screenViewModels = module {
    single {
        UserMsgViewModel()
    }
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        PagerViewModel(get())
    }
    viewModel {
        WelcomeViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SettingsViewModel(get())
    }
    viewModel{
        PlaylistViewModel(get())
    }
    single {
        NoConnectionViewModel(androidContext(), get(), get())
    }
    single{
        MeDataViewModel()
    }
    single {
        InterceptorViewModel()
    }
}

val httpModule = module {
    // Client that prevent no connection errors and adds Http log for debug mode
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
            .addInterceptor(ErrorInterceptor(androidContext(), get(), get()))
            .build()
    }
    // Recc Server
    single {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.recc_base_endpoint))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
        retrofit.create(ServerRouteDefinitions::class.java)
    }
    // MockApi
    single {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .client(get())
            .baseUrl(androidContext().getString(R.string.mockapi_base_endpoint))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(MockApiRouteDefinitions::class.java)
    }
    single {
        Auth(androidContext(), get())
    }
    single {
        Control(androidContext(), get())
    }
    single {
        MockApi(androidContext(), get())
    }
}