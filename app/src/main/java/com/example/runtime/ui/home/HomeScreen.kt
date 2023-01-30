package com.example.runtime.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.runtime.data.models.PlaylistModel
import com.example.runtime.ui.components.SimpleButton

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onSpotifyAuthentication: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val modifier = Modifier.padding(8.dp).weight(0.5f)
        if (homeViewModel.isAuthenticated) {
            SimpleButton(
                onClick = onSpotifyAuthentication,
                text = "Spotify Web Authentication",
                modifier = modifier
            )
        } else {
            Text("Authenticated!")
        }
    }

    LazyColumn(
    ) {

    }
}

fun testCallback() {

}