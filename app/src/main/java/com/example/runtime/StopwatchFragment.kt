package com.example.runtime

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.runtime.databinding.StopwatchFragmentBinding

class StopwatchFragment : Fragment() {

    // Chronometer variables
    private var running = false

    private var timeOffset = 0L

    private lateinit var chronometer: Chronometer

    // View model and data binding
    private val viewModel: StopwatchViewModel by viewModels()

    private lateinit var binding: StopwatchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.stopwatch_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set data binding variables
        binding.stopwatchViewModel = viewModel

        // Set lifecycle
        binding.lifecycleOwner = viewLifecycleOwner

        // Find the chronometer
        chronometer = binding.stopwatch

        // Setup click listeners
        binding.start.setOnClickListener { onTimeStart() }
        binding.stop.setOnClickListener { onTimeStop() }
        binding.pause.setOnClickListener { onTimePause() }
    }

    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)

    }
    */

    private fun onTimeStart() {
        if (!running) {
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
            chronometer.base = SystemClock.elapsedRealtime()
            timeOffset = 0
            running = false
        }
    }

}