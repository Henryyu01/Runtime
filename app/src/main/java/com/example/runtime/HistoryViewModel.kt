package com.example.runtime

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtime.models.HistoryModel
import com.example.runtime.repositories.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Duration
import java.util.*

class HistoryViewModel: ViewModel() {

    private val historyRepository = HistoryRepository()

    private val _histories = mutableStateListOf<ExpandableHistoryModel>()
    val histories: List<ExpandableHistoryModel>
        get() = _histories

    init {
        viewModelScope.launch {
            _histories.clear()
            _histories.addAll(historyRepository.getHistories().map { historyModel ->
                ExpandableHistoryModel(historyModel)
            })
        }
    }

    fun expandCard(id: Int) {
        Log.d(logTag, "Card $id expanded")
        _histories.forEachIndexed { index, expandableHistoryModel ->
            if (expandableHistoryModel.id == id) {
                _histories[index] = _histories[index].copy(
                    isExpanded = !_histories[index].isExpanded
                )
            }
        }
    }

    fun deleteHistory() {

    }

    fun renameHistory() {

    }

    companion object {
        const val logTag = "HistoryViewModel"
    }
}

data class ExpandableHistoryModel(
    val id: Int,
    val date: Date,
    val distance: Double,
    val duration: Duration,
    val name: String,
    val steps: Int,
    val isExpanded: Boolean,
) {
    constructor (historyModel: HistoryModel):
        this(
            historyModel.id,
            historyModel.date,
            historyModel.distance,
            historyModel.duration,
            historyModel.name,
            historyModel.steps,
            false
        )
}