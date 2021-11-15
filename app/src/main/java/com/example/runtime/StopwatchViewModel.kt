package com.example.runtime

import android.icu.util.TimeUnit
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.Duration.Companion.seconds

class StopwatchViewModel: ViewModel() {
    private val _steps = MutableLiveData<Int>()

    val steps: LiveData<Int>
        get() = _steps

    fun getData() {

    }

    fun accessGoogleFit() {

    }
}