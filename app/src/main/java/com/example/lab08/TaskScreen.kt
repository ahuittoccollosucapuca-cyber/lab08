package com.example.lab08

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Tareas - Lab08",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo para añadir nueva tarea
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTaskDescription,
                onValueChange = { newTaskDescription = it },
                label = { Text("Nueva tarea...") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newTaskDescription.isNotBlank()) {
                        viewModel.addTask(newTaskDescription)
                        newTaskDescription = ""
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Añadir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de tareas
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = { viewModel.toggleTaskCompletion(task) },
                    onDelete = { viewModel.deleteTask(task) },
                    onEdit = { newDesc -> viewModel.editTask(task, newDesc) }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editDescription by remember { mutableStateOf(task.description) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() }
            )

            if (isEditing) {
                TextField(
                    value = editDescription,
                    onValueChange = { editDescription = it },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    onEdit(editDescription)
                    isEditing = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Guardar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Text(
                    text = task.description,
                    modifier = Modifier.weight(1f),
                    style = if (task.isCompleted) {
                        MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    } else {
                        MaterialTheme.typography.bodyLarge
                    }
                )
                IconButton(onClick = { isEditing = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}