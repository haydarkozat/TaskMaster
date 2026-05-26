package com.taskmaster.app

import android.app.Application
import com.taskmaster.app.data.TaskDatabase
import com.taskmaster.app.data.TaskRepository

/**
 * Custom Application sınıfı.
 * Database ve Repository'yi lazy olarak başlatır,
 * böylece tüm uygulamada tek bir instance kullanılır (singleton pattern).
 */
class TaskMasterApp : Application() {
    // Database lazy başlatılır - yalnızca ihtiyaç duyulduğunda oluşturulur
    val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }
    val repository: TaskRepository by lazy { TaskRepository(database.taskDao()) }
}
