package com.example.lab08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Instanciar la base de datos
        val db = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "task_db"
        ).build()

        // 2. Obtener el DAO
        val dao = db.taskDao()

        // 3. Crear el ViewModel (Pasando el DAO)
        val viewModel = TaskViewModel(dao)

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // 4. Cargar la pantalla principal
                    TaskScreen(viewModel)
                }
            }
        }
    }
}