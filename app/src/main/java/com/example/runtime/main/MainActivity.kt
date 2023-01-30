package com.example.runtime.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import com.example.runtime.SpotifyLoginActivity
import com.example.runtime.ui.main.MainScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Permissions
    private var runtimePermissions = false
    private var oAuthPermissions = false
    private var apiLevel = android.os.Build.VERSION.SDK_INT

    // Spotify
    private val clientId = "f09638f3c5fa4d0d9c95ae4ac609ee67"
    private val redirectUri = "http://localhost:8888/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(
                onSpotifyAuthentication = {
                    initializeSpotify()
                }
            )
        }
    }

    private fun initializeSpotify() {
        lifecycleScope.launch {
            connectToSpotifyAndroidAPI()
            connectToSpotifyWebAPI()
        }
    }

    private fun connectToSpotifyWebAPI() {
        startSpotifyClientPkceLoginActivity(SpotifyLoginActivity::class.java)
    }

    private fun connectToSpotifyAndroidAPI() {
        // Set the connection parameters
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(
            this,
            connectionParams,
            object : Connector.ConnectionListener {

                override fun onConnected(appRemote: SpotifyAppRemote) {
                    spotifyAppRemote = appRemote
                    Log.d(TAG, "Connected! Yay!")
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e(TAG, throwable.message, throwable)
                    Log.d(TAG, "Spotify failed to connect :/")
                }
            }
        )
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
            .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
            .build()

        val account = GoogleSignIn.getAccountForExtension(applicationContext, fitnessOptions)

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

    /**
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == Companion.PERMISSIONS_REQUEST_CODE) {
            oAuthPermissions = true
        } else {
            // Show permission requirement dialogue
        }
    }
    **/

    // Check that runtime permissions have been given
    private fun checkRuntimePermissions() {
        Log.i(TAG, apiLevel.toString())

        // Android automatically gives activity_recognition permissions below Android API 29
        if (apiLevel > 28) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext, Manifest.permission.ACTIVITY_RECOGNITION)
                        == PackageManager.PERMISSION_GRANTED -> {
                    runtimePermissions = true
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION) -> {
                    Log.i(TAG, "test!")
                    // Show permission requirement dialogue
                }

                else -> {
                    Log.i(TAG, "requesting permissions!")
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

    /**
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Companion.PERMISSIONS_REQUEST_CODE -> {
                // Request granted, check for empty return
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    runtimePermissions = true
                } else {
                    Log.i(TAG, "permission refused")
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
                return
            }

            else -> {
                Log.i(TAG, "unknown request code")
            }
        }
    }
    */

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val PERMISSIONS_REQUEST_CODE = 55
    }

}