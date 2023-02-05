package com.henry.runtime.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.henry.runtime.data.models.PlaylistModel

@Composable
fun SelectablePlaylistItem(
    playlist: PlaylistModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        Column {
            Text(playlist.title)
            Text(playlist.author)
        }
        Spacer(modifier = Modifier.weight(1f))
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun SelectablePlaylistItemPreview() {
    SelectablePlaylistItem(
        playlist = PlaylistModel(
            title = "Playlist 1",
            author = "Penguin",
            id = 1,
        ),
        isSelected = true,
        onClick = {}
    )
}