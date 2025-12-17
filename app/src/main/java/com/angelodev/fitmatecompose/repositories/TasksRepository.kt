package com.angelodev.fitmatecompose.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.angelodev.fitmatecompose.database.AppDatabase
import com.angelodev.fitmatecompose.database.entities.toModel
import com.angelodev.fitmatecompose.models.TaskModel
import com.angelodev.fitmatecompose.models.toEntity
import javax.inject.Inject

class TasksRepository @Inject constructor(private val database: AppDatabase) {
    val tasksList: LiveData<List<TaskModel>> =
        database.tasksDao().getAllTasks().map { list ->
            list.map { it.toModel() }
        }


    suspend fun addTask(task: TaskModel) {
        database.tasksDao().insertTask(task.toEntity())
    }


}