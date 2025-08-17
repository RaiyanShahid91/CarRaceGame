package com.sport.carrace.db.repo

import com.sport.carrace.db.GameHistoryDao
import com.sport.carrace.db.GameHistoryEntity
import kotlinx.coroutines.flow.Flow

class GameRepository(private val dao: GameHistoryDao) {
    fun getAllGames(): Flow<List<GameHistoryEntity>> = dao.getAllGames()
    suspend fun insertGame(game: GameHistoryEntity) = dao.insert(game)
}
