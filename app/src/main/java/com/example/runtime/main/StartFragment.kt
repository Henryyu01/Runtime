package com.example.runtime.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.runtime.Stopwatch
import com.example.runtime.databinding.FragmentStartBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

private const val LOG_TAG = "StartFragment"

private const val PERMISSIONS_REQUEST_CODE = 55

class StartFragment : Fragment() {

    private lateinit var fragmentContext: Context

    // Permissions
    private var runtimePermissions = false
    private var oAuthPermissions = false
    private var apiLevel = android.os.Build.VERSION.SDK_INT

    // Binding
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    // Spotify
    private val clientId = "f09638f3c5fa4d0d9c95ae4ac609ee67"
    private val redirectUri = "http://localhost:8888/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentContext = activity!!
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val button = binding.startButton
        button.setOnClickListener {
            startStopwatch()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Set the connection parameters
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(fragmentContext, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d(LOG_TAG, "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e(LOG_TAG, throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

    // Check that the user's google account has given permission
    //  If not, request the permissions
    //  Otherwise, launch the stopwatch activity
    private fun checkOAuthPermissions() {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_CADENCE, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val account = GoogleSignIn.getAccountForExtension(fragmentContext, fitnessOptions)

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this,
                PERMISSIONS_REQUEST_CODE,
                account,
                fitnessOptions)
        } else {
            oAuthPermissions = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == PERMISSIONS_REQUEST_CODE) {
            oAuthPermissions = true
        } else {
            // Show permission requirement dialogue
        }
    }

    // Check that runtime permissions have been given
    private fun checkRuntimePermissions() {
        Log.i(LOG_TAG, apiLevel.toString())

        // Android automatically gives activity_recognition permissions below Android API 29
        if (apiLevel > 28) {
            when {
                ContextCompat.checkSelfPermission(
                    fragmentContext, Manifest.permission.ACTIVITY_RECOGNITION)
                        == PackageManager.PERMISSION_GRANTED -> {
                    runtimePermissions = true
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION) -> {
                    Log.i(LOG_TAG, "test!")
                    // Show permission requirement dialogue
                }

                else -> {
                    Log.i(LOG_TAG, "requesting permissions!")
                    requestPermissions(
                        arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                        PERMISSIONS_REQUEST_CODE
                    )
                }
            }
        } else {
            runtimePermissions = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                // Request granted, check for empty return
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    runtimePermissions = true
                } else {
                    Log.i(LOG_TAG, "permission refused")
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
                return
            }

            else -> {
                Log.i(LOG_TAG, "unknown request code")
            }
        }
    }

    private fun startStopwatch() {
        checkOAuthPermissions()
        checkRuntimePermissions()

        Log.i(LOG_TAG, "runtime permissions: $runtimePermissions")
        Log.i(LOG_TAG, "oauth permissions: $oAuthPermissions")
        val intent = Intent(fragmentContext, Stopwatch::class.java)
        if (runtimePermissions && oAuthPermissions) {
            startActivity(intent)
        } else {
            // Display dialogue on permission requirement
        }
    }

    private fun connected() {

    }
}