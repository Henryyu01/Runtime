package com.henry.runtime.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.henry.runtime.data.models.PlaylistModel
import com.henry.runtime.ui.components.SimpleButton

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onSpotifyAuthentication: () -> Unit,
    onNavigateToPlayerScreen: () -> Unit,
) {
    val playlists = homeViewModel.playlists.collectAsState().value
    val selectedPlaylist = homeViewModel.selectedPlaylist.collectAsState().value

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        if (!homeViewModel.isAuthenticated) {
            SimpleButton(
                onClick = onSpotifyAuthentication,
                text = "Spotify Web Authentication",
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
        } else {
            Text(
                "Authenticated!",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SimpleButton(
            onClick = onNavigateToPlayerScreen,
            text = "Start Session",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            playlists.forEach {
                item(it.id) {
                    PlaylistCard(
                        playlist = it,
                        selected = selectedPlaylist == it.id,
                        onClick = homeViewModel::onPlaylistSelect,
                    )
                }
            }
        }
    }
}

@Composable
private fun PlaylistCard(
    playlist: PlaylistModel,
    selected: Boolean,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(playlist.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillHeight,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp),
            )
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = playlist.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = playlist.description,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = selected,
                onCheckedChange = { onClick(playlist.id) }
            )
        }
    }
}
