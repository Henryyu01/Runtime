package com.example.runtime.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.runtime.ui.history.ExpandableHistoryModel

class HomeViewModel: ViewModel() {

    var isAuthenticated: Boolean = false
        private set

    private val _playlists = mutableStateListOf<ExpandableHistoryModel>()
    val playlists: List<ExpandableHistoryModel>
        get() = _playlists
}