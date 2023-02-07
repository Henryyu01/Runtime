package com.henry.runtime.ui.history

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.runtime.data.models.HistoryModel
import com.henry.runtime.data.repositories.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import java.time.Duration
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(): ViewModel() {

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
        val index = _histories.withIndex().find { it.value.id == id }?.index ?: return
        val historyModel = _histories[index]
        _histories[index] = historyModel.copy(isExpanded = !historyModel.isExpanded)
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