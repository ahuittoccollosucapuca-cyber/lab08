package com.example.lab08

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {

    // Estado interno (Mutable): Solo el ViewModel puede cambiarlo
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    // Estado público (Read-only): La UI observa esto
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        // Al iniciar, cargamos las tareas automáticamente
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _tasks.value = dao.getAllTasks()
        }
    }

    fun addTask(description: String) {
        val newTask = Task(description = description)
        viewModelScope.launch {
            dao.insertTask(newTask)
            fetchTasks() // Refrescamos la lista
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            dao.updateTask(updatedTask)
            fetchTasks()
        }
    }

    // --- REQUERIMIENTO EJERCICIO 1 ---
    fun deleteAllTasks() {
        viewModelScope.launch {
            dao.deleteAllTasks()
            fetchTasks()
        }
    }

    fun editTask(task: Task, newDescription: String) {
        viewModelScope.launch {
            val updatedTask = task.copy(description = newDescription)
            dao.updateTask(updatedTask)
            fetchTasks()
        }
    }
}