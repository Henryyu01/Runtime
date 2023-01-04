package com.example.runtime.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.runtime.ExpandableHistoryModel
import com.example.runtime.HistoryViewModel
import com.example.runtime.ui.HistoryCard

@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel = viewModel()) {
    val histories = historyViewModel.histories

    HistoryList(historyViewModel, histories)
}

@Composable
fun HistoryList(historyViewModel: HistoryViewModel, historyItems: List<ExpandableHistoryModel>) {
    LazyColumn() {
        items(items = historyItems) { item ->
            HistoryCard(
                expandableHistoryModel = item,
                onExpand = { historyViewModel.expandCard(item.id) },
                isExpanded = item.isExpanded)
        }
    }
}