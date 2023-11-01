package com.henry.runtime.ui.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.henry.runtime.ui.components.CircleButton

@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val timeElapsed = playerViewModel.timeElapsed.collectAsState().value

    Column {
        AsyncImage(
            model = "",
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(128.dp)
                .width(128.dp)
        )
        Row(

        ) {
            Text(
                text = timeElapsed
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            CircleButton(
                onClick = { /*TODO*/ },
                icon = Icons.Filled.SkipNext,
                description = "Play}"
            )
            CircleButton(
                onClick = { /*TODO*/ },
                icon = Icons.Filled.PlayArrow,
                description = "Next"
            )
            IconButton(
                onClick = playerViewModel::onResumeTimer,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                )
            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            IconButton(
                onClick = playerViewModel::onPauseTimer,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "Pause",
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPlayerScreen() {
    PlayerScreen()
}