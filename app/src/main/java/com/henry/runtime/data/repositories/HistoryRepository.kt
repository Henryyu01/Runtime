package com.henry.runtime.data.repositories

import com.henry.runtime.data.models.HistoryModel
import java.time.Duration
import java.util.*

class HistoryRepository {

    private val histories: MutableList<HistoryModel> = mutableListOf()
    private var runCount: Int = 3

    init {
        histories.addAll(
            listOf(
                HistoryModel(1, Date(System.currentTimeMillis()), 1240.5, Duration.ofMinutes(15), "Test run 1", 5000),
                HistoryModel(2, Date(System.currentTimeMillis() - 2000), 509.5, Duration.ofMinutes(12), "Test run 2", 3200),
                HistoryModel(3, Date(System.currentTimeMillis() - 1000), 800.0, Duration.ofMinutes(8), "Test run 3", 1251)
            )
        )
    }

    fun addHistory(
        date: Date,
        distance: Double,
        length: Duration,
        steps: Int,
    ) {
        runCount++
        histories.add(HistoryModel(runCount, date, distance, length, "Run $runCount", steps))
    }

    fun getHistories() = histories.toList()

}