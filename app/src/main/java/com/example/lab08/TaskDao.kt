package com.example.lab08

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface TaskDao {
    // Obtener todas las tareas guardadas [cite: 50]
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    // Insertar una nueva tarea [cite: 50]
    @Insert
    suspend fun insertTask(task: Task)

    // Actualizar estado de una tarea (completada/pendiente) [cite: 50]
    @Update
    suspend fun updateTask(task: Task)

    // ELIMINAR UNA TAREA (Requerido para Ejercicio 1) [cite: 80]
    @Delete
    suspend fun deleteTask(task: Task)

    // Eliminar todas las tareas de la tabla [cite: 50]
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}