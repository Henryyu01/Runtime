package com.henry.runtime.ui.player

import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


@HiltViewModel
class PlayerViewModel @Inject constructor(

) : ViewModel() {

    private val mutableTimeElapsed: MutableStateFlow<String> =
        MutableStateFlow(DateUtils.formatElapsedTime(0))
    val timeElapsed: StateFlow<String> = mutableTimeElapsed

    private var isTimerActive: Boolean = true

    private val timeElapsedFlow = flow {
        while (true) {
            if (isTimerActive) {
                emit(Unit)
            }
            delay(1.seconds)
        }
    }

    init {
        onStartTimer()
    }


    private fun onStartTimer() {
        var timeElapsedSeconds = 0L
        timeElapsedFlow
            .map {
                timeElapsedSeconds++
            }
            .onEach {
                mutableTimeElapsed.emit(DateUtils.formatElapsedTime(timeElapsedSeconds))
            }
            .launchIn(viewModelScope)
    }

    fun onResumeTimer() {
        isTimerActive = true
    }

    fun onPauseTimer() {
        timeElapsedFlow.cancellable()
    }
}