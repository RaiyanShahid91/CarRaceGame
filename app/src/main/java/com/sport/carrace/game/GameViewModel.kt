package com.sport.carrace.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sport.carrace.db.GameHistoryEntity
import com.sport.carrace.db.repo.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameViewModel(private val repo: GameRepository) : ViewModel() {

    var userName by mutableStateOf("")
    var selectedCar by mutableStateOf("")
    var selectedTrack by mutableStateOf("")
    var score by mutableStateOf(0)

    val history: StateFlow<List<GameHistoryEntity>> =
        repo.getAllGames().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun saveGame() {
        viewModelScope.launch {
            repo.insertGame(
                GameHistoryEntity(
                    userName = userName,
                    carName = selectedCar,
                    trackName = selectedTrack,
                    score = score
                )
            )
        }
    }
}
