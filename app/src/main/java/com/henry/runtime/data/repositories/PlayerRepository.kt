package com.henry.runtime.data.repositories

import android.util.Log
import com.henry.runtime.data.models.PlaylistModel
import com.henry.runtime.data.models.toPlaylistModel
import com.henry.runtime.data.sources.SpotifySource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    val spotifySource: SpotifySource
) {

    private val mutablePlaylists = MutableStateFlow<List<PlaylistModel>>(emptyList())
    val playlists: StateFlow<List<PlaylistModel>> = mutablePlaylists
    suspend fun fetchPlaylists() {
        mutablePlaylists.value = spotifySource.getUserPlaylists().map {
            Log.d("TEST", "playlist: $it.name")
            it.toPlaylistModel()
        }
    }
}