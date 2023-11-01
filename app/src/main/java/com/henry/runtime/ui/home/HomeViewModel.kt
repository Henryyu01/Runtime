package com.henry.runtime.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.runtime.data.repositories.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    var isAuthenticated: Boolean = false
        private set

    val playlists = playerRepository.playlists

    private val mutableSelectedPlaylist = MutableStateFlow("")
    val selectedPlaylist: StateFlow<String> = mutableSelectedPlaylist

    init {
        fetchPlaylists()
    }

    fun fetchPlaylists() {
        viewModelScope.launch {
            playerRepository.fetchPlaylists()
        }
    }

    fun onPlaylistSelect(id: String) {
        if (mutableSelectedPlaylist.value == id) {
            mutableSelectedPlaylist.value = ""
        } else {
            mutableSelectedPlaylist.value = id
        }
    }

    fun onSessionStart() {

    }
}