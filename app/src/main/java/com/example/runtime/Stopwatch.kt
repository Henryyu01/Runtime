package com.example.runtime

/**
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.runtime.databinding.ActivityStopwatchBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.SensorRequest
import java.util.concurrent.TimeUnit

class Stopwatch : AppCompatActivity() {

    private var LOG_TAG = "Stopwatch"

    // Chronometer vars
    private var running = false

    private var timeOffset = 0L

    private lateinit var chronometer: Chronometer

    // Binding
    private lateinit var binding: ActivityStopwatchBinding

    // View Model
    private val viewModel: StopwatchViewModel by viewModels()

    // Fitness API

    private val fitnessOptions: GoogleSignInOptionsExtension = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_CADENCE, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    private lateinit var account: GoogleSignInAccount

    private val cadenceRequest = SensorRequest.Builder()
        .setDataType(DataType.TYPE_STEP_COUNT_CADENCE)
        .setSamplingRate(5, TimeUnit.SECONDS)
        .build()

    private val stepRequest = SensorRequest.Builder()
        .setDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
        .setSamplingRate(1, TimeUnit.SECONDS)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the view binding
        binding = ActivityStopwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the chronometer
        chronometer = binding.stopwatch

        // Setup click listeners
        binding.start.setOnClickListener { onTimeStart() }
        binding.stop.setOnClickListener { onTimeStop() }
        binding.pause.setOnClickListener { onTimePause() }

        // Setup observers to livedata
        val stepObserver = Observer<Int> { steps ->
            binding.steps.text = steps.toString()
        }

        val cadenceObserver = Observer<Int> { cadence ->
            binding.cadence.text = cadence.toString()
        }
        viewModel.steps.observe(this, stepObserver)
        viewModel.cadence.observe(this, cadenceObserver)

        // Initialize Fitness API
        account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)
    }

    override fun onPause() {
        super.onPause()
        Fitness.getSensorsClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .remove(viewModel.stepListener)
            .addOnSuccessListener {
                Log.i(LOG_TAG, "Step Listener removed")
            }
            .addOnFailureListener {
                Log.i(LOG_TAG, "Step Listener removal failed")
            }
        Fitness.getSensorsClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .remove(viewModel.cadenceListener)
            .addOnSuccessListener {
                Log.i(LOG_TAG, "Cadence Listener removed")
            }
            .addOnFailureListener {
                Log.i(LOG_TAG, "Cadence Listener removal failed")
            }
    }

    private fun onTimeStart() {
        if (!running) {
            chronometer.base = SystemClock.elapsedRealtime() - timeOffset
            chronometer.start()
            running = true
            createFitnessClient()
        }
    }

    private fun onTimePause() {
        if (running) {
            chronometer.stop()
            timeOffset = SystemClock.elapsedRealtime() - chronometer.base
            running = false
        }
    }

    private fun onTimeStop() {
        if (running) {
            chronometer.stop()
        }
        chronometer.base = SystemClock.elapsedRealtime()
        timeOffset = 0
        running = false
    }

    private fun createFitnessClient() {
        val cadenceResponse = Fitness.getSensorsClient(this, account)
            .add(cadenceRequest, viewModel.cadenceListener)
            .addOnSuccessListener {
                Log.i(LOG_TAG, "Listener successful")
            }
            .addOnFailureListener {
                Log.i(LOG_TAG, "Listener failed")
            }

        val stepResponse = Fitness.getSensorsClient(this, account)
            .add(stepRequest, viewModel.stepListener)
            .addOnSuccessListener {
                Log.i(LOG_TAG, "Listener successful")
            }
            .addOnFailureListener {
                Log.i(LOG_TAG, "Listener failed")
            }
    }
}**/