package com.taskmaster.app.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository - DAO ile ViewModel arasında soyutlama katmanı.
 * Bu sayede ViewModel veri kaynağını (Room, API, vb.) bilmek zorunda kalmaz.
 */
class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun getTaskById(id: Int): Task? = taskDao.getTaskById(id)

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun deleteCompletedTasks() = taskDao.deleteCompletedTasks()
}
