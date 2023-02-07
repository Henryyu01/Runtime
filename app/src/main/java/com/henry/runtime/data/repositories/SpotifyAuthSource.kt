package com.henry.runtime.data.repositories

import android.content.Context
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpotifyAuthSource @Inject constructor() {

    @ApplicationContext
    @Inject
    lateinit var context: Context

    @Provides
    @Singleton
    fun credentialStore() = SpotifyDefaultCredentialStore(
        clientId = "YOUR_SPOTIFY_CLIENT_ID",
        redirectUri = "YOUR_SPOTIFY_REDIRECT_URI",
        applicationContext = context
    )



}