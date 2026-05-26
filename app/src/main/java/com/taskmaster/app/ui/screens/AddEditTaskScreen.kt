package com.taskmaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskmaster.app.data.Priority
import com.taskmaster.app.data.Task
import com.taskmaster.app.ui.theme.PriorityHigh
import com.taskmaster.app.ui.theme.PriorityLow
import com.taskmaster.app.ui.theme.PriorityMedium
import com.taskmaster.app.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    viewModel: TaskViewModel,
    taskId: Int?,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var existingTask by remember { mutableStateOf<Task?>(null) }
    var isLoaded by remember { mutableStateOf(taskId == null) }

    // Düzenleme modu: mevcut görevi yükle
    LaunchedEffect(taskId) {
        if (taskId != null && !isLoaded) {
            viewModel.getTaskById(taskId)?.let { task ->
                title = task.title
                description = task.description
                priority = task.priority
                existingTask = task
            }
            isLoaded = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Yeni Görev" else "Görevi Düzenle") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Geri")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()) {
                                val current = existingTask
                                if (current == null) {
                                    viewModel.addTask(title, description, priority)
                                } else {
                                    viewModel.updateTask(
                                        current.copy(
                                            title = title.trim(),
                                            description = description.trim(),
                                            priority = priority
                                        )
                                    )
                                }
                                onNavigateBack()
                            }
                        },
                        enabled = title.isNotBlank()
                    ) {
                        Icon(Icons.Default.Check, "Kaydet")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Başlık") },
                placeholder = { Text("Görev başlığı...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Açıklama (opsiyonel)") },
                placeholder = { Text("Görev detayları...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Column {
                Text(
                    text = "Öncelik",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Priority.values().forEach { p ->
                    val color = when (p) {
                        Priority.HIGH -> PriorityHigh
                        Priority.MEDIUM -> PriorityMedium
                        Priority.LOW -> PriorityLow
                    }
                    PriorityOption(
                        priority = p,
                        color = color,
                        isSelected = priority == p,
                        onSelect = { priority = p }
                    )
                }
            }
        }
    }
}

@Composable
private fun PriorityOption(
    priority: Priority,
    color: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onSelect,
                role = Role.RadioButton
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(Modifier.width(8.dp))
        Text(priority.displayName, style = MaterialTheme.typography.bodyLarge)
    }
}
