package com.example.runtime.models


import java.time.Duration
import java.util.*

data class HistoryModel (
    val date: Date,
    val distance: Double,
    val length: Duration,
    val name: String,
    val steps: Int,
)