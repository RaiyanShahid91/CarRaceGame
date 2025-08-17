package com.sport.carrace.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_history")
data class GameHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userName: String,
    val carName: String,
    val trackName: String,
    val score: Int,
    val timestamp: Long = System.currentTimeMillis()
)