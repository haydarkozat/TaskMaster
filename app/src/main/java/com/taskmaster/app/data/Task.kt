package com.taskmaster.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Veritabanındaki "tasks" tablosunu temsil eden entity.
 * Her bir görev (task) için bu sınıftan bir nesne oluşturulur.
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val createdAt: Long = System.currentTimeMillis()
)

enum class Priority(val displayName: String) {
    LOW("Düşük"),
    MEDIUM("Orta"),
    HIGH("Yüksek")
}
