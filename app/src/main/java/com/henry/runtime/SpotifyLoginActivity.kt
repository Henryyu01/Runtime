package com.henry.runtime

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.auth.pkce.AbstractSpotifyPkceLoginActivity
import com.henry.runtime.data.repositories.SpotifyAuthSource
import com.henry.runtime.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

internal var pkceClassBackTo: Class<out Activity>? = null

@AndroidEntryPoint
class SpotifyLoginActivity @Inject constructor() : AbstractSpotifyPkceLoginActivity() {

    @Inject
    private lateinit var spotifyAuthSource: SpotifyAuthSource

    override val clientId = BuildConfig.SPOTIFY_CLIENT_ID
    override val redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI
    override val scopes = listOf(
        SpotifyScope.USER_READ_PLAYBACK_STATE,
        SpotifyScope.USER_MODIFY_PLAYBACK_STATE,
        SpotifyScope.STREAMING,
        SpotifyScope.PLAYLIST_READ_PRIVATE,
        SpotifyScope.APP_REMOTE_CONTROL,
    )

    override fun onFailure(exception: Exception) {
        exception.printStackTrace()
        pkceClassBackTo = null
        Log.d("SPOTIFY", "Auth failed: ${exception.message}")
    }

    override fun onSuccess(api: SpotifyClientApi) {
        spotifyAuthSource.credentialStore().setSpotifyApi(api)
        val classBackTo = pkceClassBackTo ?: MainActivity::class.java
        pkceClassBackTo = null
        Log.d("SPOTIFY", "Auth succeeded")
        startActivity(Intent(this, classBackTo))
    }
}
