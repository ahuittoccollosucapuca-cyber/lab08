package com.example.lab08

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo de texto superior
        TextField(
            value = newTaskDescription,
            onValueChange = { newTaskDescription = it },
            label = { Text("Nueva tarea") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFE6EBF8) // Color azulado claro de la imagen
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Agregar Tarea
        Button(
            onClick = {
                if (newTaskDescription.isNotBlank()) {
                    viewModel.addTask(newTaskDescription)
                    newTaskDescription = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF536493)) // Color de la imagen
        ) {
            Text("Agregar tarea", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de tareas con peso para que el botón de abajo se quede al fondo
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tasks) { task ->
                TaskItemRow(
                    task = task,
                    onToggle = { viewModel.toggleTaskCompletion(task) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Eliminar todas las tareas
        Button(
            onClick = { viewModel.deleteAllTasks() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF536493))
        ) {
            Text("Eliminar todas las tareas", fontSize = 16.sp)
        }
    }
}

@Composable
fun TaskItemRow(task: Task, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = task.description,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onToggle,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (task.isCompleted) Color.Gray else Color(0xFF536493)
            ),
            modifier = Modifier.width(120.dp)
        ) {
            Text(if (task.isCompleted) "Completado" else "Pendiente")
        }
    }
}