package com.henry.runtime.ui.history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import com.henry.runtime.ui.components.HistoryCard

@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel) {
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