package com.sport.carrace.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameHistoryEntity::class], version = 1)
abstract class RacingDatabase : RoomDatabase() {
    abstract fun gameHistoryDao(): GameHistoryDao
}