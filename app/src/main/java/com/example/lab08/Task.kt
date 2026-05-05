package com.example.lab08

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity marca esta clase como una tabla de base de datos [cite: 45]
@Entity(tableName = "tasks")
data class Task(
    // Define la clave primaria con generación automática [cite: 46]
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Especifica el nombre de la columna en la BD [cite: 47]
    @ColumnInfo(name = "description") val description: String,

    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false
)