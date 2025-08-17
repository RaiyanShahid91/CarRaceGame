package com.sport.carrace.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sport.carrace.sensor.TiltSensorHelper

@Composable
fun RacingApp(viewModel: GameViewModel = viewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val tiltSensor = remember { TiltSensorHelper(context) }

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("username") {
            UsernameScreen {
                viewModel.userName = it
                navController.navigate("game")
            }
        }
        composable("carSelection") {
            CarSelectionScreen {
                viewModel.selectedCar = it
                navController.navigate("game")
            }
        }
        composable("game") {
            GameScreen(
                userName = viewModel.userName,
                carName = viewModel.selectedCar,
                trackName = "",
                tiltSensor = tiltSensor
            ) { score ->
                viewModel.score = score
                viewModel.saveGame()
                navController.navigate("gameOver")
            }
        }
        composable("gameOver") {
            GameOverScreen(score = viewModel.score, onContinue = {
                navController.navigate("history")
            })
        }

        composable("history") { HistoryScreen(viewModel, navController) }
    }
}
