package com.example.runtime

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.runtime.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CODE = 55

    private var runtimePermissions = false

    private var oAuthPermissions = false

    private var LOG_TAG = "MainActivity"

    private var apiLevel = android.os.Build.VERSION.SDK_INT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkOAuthPermissions()
    }

    // Check that the user's google account has given permission
    //  If not, request the permissions
    //  Otherwise, launch the stopwatch activity
    private fun checkOAuthPermissions() {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_CADENCE, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)

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
        if (resultCode == RESULT_OK && requestCode == PERMISSIONS_REQUEST_CODE) {
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
                    this, Manifest.permission.ACTIVITY_RECOGNITION)
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

    fun startStopwatch(view: View) {
        checkRuntimePermissions()
        Log.i(LOG_TAG, "runtime permissions: $runtimePermissions")
        Log.i(LOG_TAG, "oauth permissions: $oAuthPermissions")
        val intent = Intent(this, Stopwatch::class.java)
        if (runtimePermissions && oAuthPermissions) {
            startActivity(intent)
        } else {
            // Display dialogue on permission requirement
        }
    }
}