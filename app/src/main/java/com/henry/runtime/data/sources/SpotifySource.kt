package com.henry.runtime.data.sources

import android.util.Log
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.models.PlayableUri
import com.adamratzman.spotify.models.Playlist
import com.adamratzman.spotify.models.SimplePlaylist
import com.adamratzman.spotify.models.SpotifyUserInformation
import com.adamratzman.spotify.models.Track
import com.henry.runtime.data.repositories.PlayerRepository
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

    private suspend fun getUserInformation(): SpotifyUserInformation? {
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

    suspend fun getPlaylist(playlistId: String): Playlist? {
        return spotifyApi?.let {
            it.playlists.getPlaylist(
                playlist = playlistId,
                market = null,
            )
        }
    }

    suspend fun getAudioFeatures(tracks: List<Track>): List<PlayerRepository.TrackWithAudioFeatures> {
        return spotifyApi?.let { spotifyClientApi ->
            val trackids = tracks.mapNotNull { track ->
                track.id
            }
            val audioFeatures = spotifyClientApi.tracks.getAudioFeatures(
                tracks = trackids.toTypedArray()
            )

            return@let audioFeatures.mapNotNull { audioFeature ->
                if (audioFeature == null) return@mapNotNull null

                val track = tracks.find { track ->
                    track.uri == audioFeature.uri
                } ?: return@mapNotNull null

                PlayerRepository.TrackWithAudioFeatures(
                    track = track,
                    audioFeatures = audioFeature
                )
            }
        } ?: emptyList()
    }
}