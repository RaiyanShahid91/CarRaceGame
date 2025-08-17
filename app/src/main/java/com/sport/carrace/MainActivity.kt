package com.sport.carrace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.sport.carrace.db.RacingDatabase
import com.sport.carrace.db.repo.GameRepository
import com.sport.carrace.game.GameViewModel
import com.sport.carrace.game.RacingApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(applicationContext, RacingDatabase::class.java, "racing.db").build()

        val repo = GameRepository(db.gameHistoryDao())
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GameViewModel(repo) as T
            }
        }

        setContent {
            MaterialTheme {
                RacingApp(viewModel(factory = factory))
            }
        }
    }
}
