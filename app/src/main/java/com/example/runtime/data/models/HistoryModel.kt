package com.example.runtime.data.models


import java.time.Duration
import java.util.*

data class  HistoryModel (
    val id: Int,
    val date: Date,
    val distance: Double,
    val duration: Duration,
    val name: String,
    val steps: Int,
)