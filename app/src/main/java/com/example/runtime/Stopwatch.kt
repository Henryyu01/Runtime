package com.example.runtime

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.runtime.databinding.ActivityStopwatchBinding

class Stopwatch : AppCompatActivity() {

    // Chronometer vars
    private var running = false

    private var timeOffset = 0L

    private lateinit var chronometer: Chronometer

    // Binding
    private lateinit var binding: ActivityStopwatchBinding

    // View Model
    private val viewModel: StopwatchViewModel by viewModels()

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher. You can use either a val, as shown in this snippet,
    // or a lateinit var in your onAttach() or onCreate() method.
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getData()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

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
        viewModel.steps.observe(this, stepObserver)
    }

    private fun onTimeStart() {
        if (!running) {
            checkPermission()
            chronometer.base = SystemClock.elapsedRealtime() - timeOffset
            chronometer.start()
            running = true
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

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                    == PackageManager.PERMISSION_GRANTED -> {
                viewModel.getData()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION) -> {

            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
            }

        }
    }
}