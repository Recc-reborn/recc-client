package com.recc.recc_client.di

import com.google.gson.GsonBuilder
import com.recc.recc_client.BuildConfig
import com.recc.recc_client.R
import com.recc.recc_client.http.ErrorInterceptor
import com.recc.recc_client.http.InterceptorViewModel
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.http.def.MockApiRouteDefinitions
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.http.def.SpotifyApiRouteDefinitions
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.MockApi
import com.recc.recc_client.http.impl.Spotify
import com.recc.recc_client.layout.auth.LoginViewModel
import com.recc.recc_client.layout.auth.RegisterViewModel
import com.recc.recc_client.layout.common.MeDataViewModel
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.home.PagerViewModel
import com.recc.recc_client.layout.playlist.PlaylistViewModel
import com.recc.recc_client.layout.settings.SettingsViewModel
import com.recc.recc_client.layout.user_msg.UserMsgViewModel
import com.recc.recc_client.layout.views.NoConnectionViewModel
import com.recc.recc_client.layout.welcome.SelectPreferredArtistsViewModel
import com.recc.recc_client.layout.welcome.SelectPreferredTracksViewModel
import com.recc.recc_client.utils.SharedPreferences
import com.spotify.android.appremote.api.ConnectionParams
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
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
        SelectPreferredTracksViewModel(get(), get())
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
        SelectPreferredArtistsViewModel(get(), get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        SettingsViewModel(get(), get())
    }
    viewModel{
        PlaylistViewModel(get(), get(), get())
    }
    single {
        NoConnectionViewModel(get(), get(), get())
    }
    single{
        MeDataViewModel()
    }
    single {
        InterceptorViewModel()
    }
}

val spotify = module {
    single {
        ConnectionParams.Builder(androidContext().getString(R.string.spotify_client_id))
            .setRedirectUri(androidContext().getString(R.string.spotify_redirect_uri))
            .showAuthView(true)
            .build()
    }
}

val sharedPreferences = module {
    single {
        SharedPreferences(androidApplication())
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
    // Spotify API
    single {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .client(get())
            .baseUrl(androidContext().getString(R.string.spotify_base_endpoint))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(SpotifyApiRouteDefinitions::class.java)
    }
    single {
        Auth(get())
    }
    single {
        Control(get())
    }
    single {
        MockApi(get())
    }
    single {
        Spotify(get())
    }
}