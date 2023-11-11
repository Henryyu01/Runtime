package com.henry.runtime.data.repositories

import android.util.Log
import com.adamratzman.spotify.models.AudioFeatures
import com.adamratzman.spotify.models.Track
import com.henry.runtime.data.models.PlaylistModel
import com.henry.runtime.data.models.toPlaylistModel
import com.henry.runtime.data.sources.SensorSource
import com.henry.runtime.data.sources.SpotifySource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs
import kotlin.math.roundToLong

@Singleton
class PlayerRepository @Inject constructor(
    private val spotifySource: SpotifySource,
    private val sensorSource: SensorSource,
) {

    private val mutablePlaylists = MutableStateFlow<List<PlaylistModel>>(emptyList())
    val playlists: StateFlow<List<PlaylistModel>> = mutablePlaylists

    private val mutableSelectedPlaylist = MutableStateFlow<String>("")
    val selectPlaylist: StateFlow<String> = mutableSelectedPlaylist

    private val runningCadenceHistory: ArrayDeque<Long> = ArrayDeque()

    private val selectedPlaylistTracks: MutableList<TrackWithAudioFeatures> = mutableListOf()

    val runningCadenceFlow = MutableStateFlow<Long>(0L)

    suspend fun startStepRecording() {
        sensorSource.startStepCountRecording()
        var prevTimeStamp = 0L

        sensorSource.stepCounterFlow
            .collect { currentTimeStamp ->
                val diff = if (prevTimeStamp == 0L) {
                    0L
                } else {
                    currentTimeStamp - prevTimeStamp
                }

                val cadence = SECONDS_IN_MINUTES / diff * MILLSECONDS_IN_SECONDS

                runningCadenceHistory.addFirst(cadence)
                if (runningCadenceHistory.size >= CADENCE_BUFFER_SIZE) {
                    runningCadenceHistory.removeLast()
                }
                runningCadenceFlow.emit(runningCadenceHistory.average().roundToLong())
            }
    }

    suspend fun pauseStepRecording() {
        sensorSource.pauseStepCountRecording()
    }

    suspend fun stopStepRecording() {
        runningCadenceHistory.clear()
        sensorSource.pauseStepCountRecording()
    }

    suspend fun populateTracksWithAudioFeatures() {
        val selectedPlaylist = spotifySource.getPlaylist(mutableSelectedPlaylist.value) ?: return
        val tracks = selectedPlaylist.tracks.mapNotNull {
            it?.track?.asTrack
        }
        val trackWithAudioFeatures = spotifySource.getAudioFeatures(tracks)
        selectedPlaylistTracks.clear()
        selectedPlaylistTracks.addAll(trackWithAudioFeatures)
    }

    private suspend fun getClosestBpmTrack(): TrackWithAudioFeatures {
        val runnngCadence = runningCadenceFlow.value.toFloat()

        return selectedPlaylistTracks.minBy {
            abs(runnngCadence - it.audioFeatures.tempo)
        }
    }

    suspend fun queueNextTrack() {
        val trackWithAudioFeatures = getClosestBpmTrack()

        spotifySource.addToQueue(trackWithAudioFeatures.track.uri)
    }

    suspend fun fetchPlaylists() {
        mutablePlaylists.value = spotifySource.getUserPlaylists().map {
            Log.d("TEST", "playlist: $it.name")
            it.toPlaylistModel()
        }
    }

    suspend fun setSelectedPlaylist(id: String) {
        mutableSelectedPlaylist.value = id
    }

    companion object {
        private const val MILLSECONDS_IN_SECONDS = 1000
        private const val SECONDS_IN_MINUTES = 60
        private const val CADENCE_BUFFER_SIZE = 120
    }

    data class TrackWithAudioFeatures(
        val track: Track,
        val audioFeatures: AudioFeatures,
    )
}