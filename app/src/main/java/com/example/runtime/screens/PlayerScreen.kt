package com.example.runtime.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.runtime.ui.circleButton

@Composable
fun PlayerScreen() {
    Row() {
        circleButton(onClick = { /*TODO*/ }, icon = Icons.Filled.PlaylistPlay, description = "Play}")
        circleButton(onClick = { /*TODO*/ }, icon = Icons.Filled.SkipNext, description = "Next")
        circleButton(onClick = { /*TODO*/ }, icon = Icons.Filled.SkipPrevious, description = "play")
    }
}