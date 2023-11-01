package com.henry.runtime.data.sources

import android.util.Log
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.models.PlayableUri
import com.adamratzman.spotify.models.SimplePlaylist
import com.adamratzman.spotify.models.SpotifyUserInformation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifySource @Inject constructor(
    private val spotifyCredentialStore: SpotifyCredentialStore
) {

    var spotifyApi: SpotifyClientApi? = spotifyCredentialStore.getSpotifyApi()

    suspend fun addToQueue(playableUri: PlayableUri) {
        spotifyApi?.let {
            it.player.addItemToEndOfQueue(playableUri)
        }
    }

    suspend fun getSongBpm() {
        spotifyApi?.let {

        }
    }

    suspend fun getUserInformation(): SpotifyUserInformation? {
        return spotifyApi?.let {
            Log.d("TEST", it.getUserId())
            it.users.getClientProfile()
        }
    }

    suspend fun getUserPlaylists(): List<SimplePlaylist> {
        val userInformation = getUserInformation() ?: return emptyList()
        val userId = userInformation.id
        Log.d("TEST", "userInformation: $userInformation")

        return spotifyApi?.let {
            it.playlists.getUserPlaylists(
                userId
            ).items
        } ?: emptyList()
    }
}