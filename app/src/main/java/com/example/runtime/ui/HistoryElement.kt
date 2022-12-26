package com.example.runtime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Man
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.runtime.models.HistoryModel

@Composable
fun HistoryElement(historyModel: HistoryModel) {
    Row() {
        Icon(Icons.Default.Man, "Running Icon")

        Column() {
            Text(historyModel.name)
            Row() {
                Text(historyModel.length.toString())
                Text(historyModel.distance.toString())
                Text(historyModel.steps.toString())
                Text(historyModel.date.toString())
            }
        }
    }
}