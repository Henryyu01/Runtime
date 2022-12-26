package com.example.runtime.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import com.example.runtime.HistoryViewModel
import com.example.runtime.ui.HistoryElement

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    HistoryList(viewModel)
}

@Composable
fun HistoryList(viewModel: HistoryViewModel) {
    LazyColumn() {
        items(items = viewModel.getHistories()) { item ->
            HistoryElement(item)
        }
    }
}