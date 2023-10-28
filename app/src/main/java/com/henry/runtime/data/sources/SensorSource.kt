package com.henry.runtime.data.sources

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorSource @Inject constructor(
    @ApplicationContext val context: Context,
) {

    private val sensorManager =
        context.applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val stepDetectorSensor: Sensor =
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    private val stepDetectorStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val stepCounterFlow: Flow<Int> = stepDetectorStateFlow

    private val listener = StepCounterSensorEventListener()

    suspend fun resetStepCountRecording() {
        stepDetectorStateFlow.emit(0)
    }

    fun startStepCountRecording() {
        sensorManager.registerListener(
            listener,
            stepDetectorSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun pauseStepCountRecording() {
        sensorManager.unregisterListener(listener)
    }

    inner class StepCounterSensorEventListener() : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            stepDetectorStateFlow.tryEmit(stepDetectorStateFlow.value + 1)
        }

        override fun onAccuracyChanged(event: Sensor?, accuracy: Int) {

        }

    }
}
