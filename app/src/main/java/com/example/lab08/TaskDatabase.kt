package com.example.lab08

import androidx.room.Database
import androidx.room.RoomDatabase

// Define las entidades y la versión de la BD [cite: 57, 60]
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    // Expone el DAO creado anteriormente [cite: 57]
    abstract fun taskDao(): TaskDao
}