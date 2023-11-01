package com.henry.runtime.data.sources

import android.content.Context
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.henry.runtime.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyCredentialStore @Inject constructor(
    @ApplicationContext val context: Context,
) {
    val credentialStore by lazy {
        SpotifyDefaultCredentialStore(
            clientId = BuildConfig.SPOTIFY_CLIENT_ID,
            redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI,
            applicationContext = context,
        )
    }

    fun getSpotifyApi(): SpotifyClientApi? {
        return credentialStore.getSpotifyClientPkceApi()
    }
}