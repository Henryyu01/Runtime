package com.henry.runtime.data.repositories

import com.henry.runtime.data.models.PlaylistModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpotifySource @Inject constructor() {

    @Provides
    @Singleton
    fun getDownloadedPlaylists(): PlaylistModel {
        return PlaylistModel("s", 1, "s")
    }
}