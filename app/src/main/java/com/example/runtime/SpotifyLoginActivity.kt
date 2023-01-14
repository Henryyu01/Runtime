package com.example.runtime

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.auth.pkce.AbstractSpotifyPkceLoginActivity
import com.example.runtime.ui.MainActivity

internal var pkceClassBackTo: Class<out Activity>? = null

class SpotifyLoginActivity() : AbstractSpotifyPkceLoginActivity() {

    override val clientId = BuildConfig.SPOTIFY_CLIENT_ID
    override val redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI
    override val scopes = listOf(
        SpotifyScope.USER_READ_PLAYBACK_STATE,
        SpotifyScope.USER_MODIFY_PLAYBACK_STATE,
        SpotifyScope.STREAMING,
        SpotifyScope.PLAYLIST_READ_PRIVATE,
        SpotifyScope.APP_REMOTE_CONTROL,
    )

    private val credentialStore by lazy {
        SpotifyDefaultCredentialStore(
            clientId = "YOUR_SPOTIFY_CLIENT_ID",
            redirectUri = "YOUR_SPOTIFY_REDIRECT_URI",
            applicationContext = this
        )
    }

    override fun onFailure(exception: Exception) {
        exception.printStackTrace()
        pkceClassBackTo = null
        Log.d("SPOTIFY", "Auth failed: ${exception.message}")
    }

    override fun onSuccess(api: SpotifyClientApi) {
        credentialStore.setSpotifyApi(api)
        val classBackTo = pkceClassBackTo ?: MainActivity::class.java
        pkceClassBackTo = null
        Log.d("SPOTIFY", "Auth succeeded")
        startActivity(Intent(this, classBackTo))
    }
}

interface SpotifyWebAPI {

}