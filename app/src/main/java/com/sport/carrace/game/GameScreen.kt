package com.sport.carrace.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sport.carrace.R
import com.sport.carrace.sensor.TiltSensorHelper
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(
    userName: String,
    carName: String,
    trackName: String,
    tiltSensor: TiltSensorHelper,
    onGameOver: (score: Int) -> Unit
) {
    val tilt by tiltSensor.tilt.collectAsState()

    var playerX by remember { mutableFloatStateOf(0f) }
    var obstacles by remember { mutableStateOf(listOf(200f to -200f)) }
    var score by remember { mutableIntStateOf(0) }
    var crashed by remember { mutableStateOf(false) }

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    val carSize = 80.dp
    val carSizePx = with(LocalDensity.current) { carSize.toPx() }

    val playerY = screenHeightDp - carSize - 40.dp

    DisposableEffect(Unit) {
        tiltSensor.start()
        onDispose { tiltSensor.stop() }
    }

    LaunchedEffect(tilt) {
        playerX -= tilt * 2f
        val maxX = screenWidthDp - carSize
        playerX = playerX.coerceIn(0f, maxX.value)
    }

    LaunchedEffect(Unit) {
        while (!crashed) {
            delay(50)
            obstacles = obstacles.map { it.first to it.second + 10f }
                .filter { it.second < screenHeightDp.value + 200f }

            if (Random.nextInt(100) < 5) {
                val laneX = listOf(50f, 200f, 350f).random()
                obstacles = obstacles + (laneX to -100f)
            }

            score++
        }
    }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.road),
            contentDescription = "Road",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        val density = LocalDensity.current

        obstacles.forEach { (x, y) ->
            Image(
                painter = painterResource(id = R.drawable.truck),
                contentDescription = "Enemy Car",
                modifier = Modifier
                    .offset(x.dp, y.dp)
                    .size(carSize)
            )

            val collision = with(density) {
                isColliding(
                    playerX.dp.toPx(), playerY.toPx(), carSizePx, carSizePx,
                    x.dp.toPx(), y.dp.toPx(), carSizePx, carSizePx
                )
            }
            if (!crashed && collision) {
                crashed = true
                onGameOver(score)
            }
        }

        if (!crashed) {
            Image(
                painter = painterResource(id = R.drawable.car),
                contentDescription = "Player Car",
                modifier = Modifier
                    .offset(playerX.dp, playerY)
                    .size(carSize)
            )
        } else {
            Box(
                Modifier
                    .offset(playerX.dp, playerY)
                    .size(carSize)
                    .background(Color.Red, RoundedCornerShape(8.dp))
            )
        }

        Text(
            "Score: $score",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )
    }
}

fun isColliding(
    playerX: Float, playerY: Float, playerWidth: Float, playerHeight: Float,
    enemyX: Float, enemyY: Float, enemyWidth: Float, enemyHeight: Float
): Boolean {
    return playerX < enemyX + enemyWidth &&
            playerX + playerWidth > enemyX &&
            playerY < enemyY + enemyHeight &&
            playerY + playerHeight > enemyY
}