package com.example.runtime.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.runtime.ui.SimpleButton

@Preview
@Composable
fun HomeScreen() {
    Text(
        text = "Home"
    )
    
    SimpleButton(onClick = ::testCallback, text = "testing!")
}

fun testCallback() {

}