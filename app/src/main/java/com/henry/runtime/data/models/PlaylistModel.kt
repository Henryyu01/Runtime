package com.henry.runtime.data.models

import com.adamratzman.spotify.models.SimplePlaylist

data class PlaylistModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
)

fun SimplePlaylist.toPlaylistModel(): PlaylistModel {
    return PlaylistModel(
        id = this.id,
        title = this.name,
        imageUrl = this.images.firstOrNull()?.url ?: "",
        description = this.description ?: "",
    )
}