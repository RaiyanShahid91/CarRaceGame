package com.sport.carrace.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val tracks = listOf("City Street", "Mountain Pass", "Desert Road")

@Composable
fun TrackSelectionScreen(onTrackSelected: (String) -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Select Track", style = MaterialTheme.typography.headlineMedium)
        LazyColumn {
            items(tracks) { track ->
                Card(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { onTrackSelected(track) }
                ) {
                    Text(track, Modifier.padding(16.dp))
                }
            }
        }
    }
}
