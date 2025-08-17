package com.sport.carrace.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GameOverScreen(score: Int, onContinue: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("💥 Game Over!", style = MaterialTheme.typography.headlineLarge, color = Color.Red)
        Spacer(Modifier.height(16.dp))
        Text("Your Score: $score", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))

        Button(onClick = onContinue) {
            Text("See History")
        }
    }
}
