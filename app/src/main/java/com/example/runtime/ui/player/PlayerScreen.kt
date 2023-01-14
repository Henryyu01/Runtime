package com.example.runtime.ui.player

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.runtime.ui.components.CircleButton

@Composable
fun PlayerScreen() {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        CircleButton(onClick = { /*TODO*/ }, icon = Icons.Filled.SkipNext, description = "Play}")
        CircleButton(onClick = { /*TODO*/ }, icon = Icons.Filled.PlayArrow, description = "Next")
        CircleButton(onClick = { /*TODO*/ }, icon = Icons.Filled.SkipPrevious, description = "play")
    }
}