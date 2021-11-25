package com.example.runtime

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.fitness.request.OnDataPointListener

class StopwatchViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG = "StopwatchViewModel"

    private var _cadence = MutableLiveData<Int>(0)

    val listener = OnDataPointListener { dataPoint ->
        for (field in dataPoint.dataType.fields) {
            val value = dataPoint.getValue(field)
            Log.i(LOG_TAG, "Datapoint field: ${field.name}, value: $value")
            _cadence.value = value.asInt()
        }
    }

    val cadence: LiveData<Int>
        get() = _cadence

}