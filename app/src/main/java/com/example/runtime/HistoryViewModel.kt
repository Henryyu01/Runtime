package com.example.runtime

import androidx.lifecycle.ViewModel
import com.example.runtime.repositories.HistoryRepository

class HistoryViewModel(private val historyRepository: HistoryRepository): ViewModel() {

    fun getHistories() = historyRepository.getHistories()
}