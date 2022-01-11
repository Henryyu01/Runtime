package com.example.runtime.stopwatch

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject

class spotifyWebRepository {

}

class spotifySingleton constructor(context: Context) {
    companion object{
        @Volatile
        private var INSTANCE: spotifySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: spotifySingleton(context).also {
                    INSTANCE = it
                }
            }
    }

    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    val url = "http://my-json-feed"

    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
        { response ->

        },
        { error ->
            // TODO: Handle error
        }
    )

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

}