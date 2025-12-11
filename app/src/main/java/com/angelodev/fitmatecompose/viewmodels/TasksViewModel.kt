package com.angelodev.fitmatecompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelodev.fitmatecompose.models.TaskModel
import com.angelodev.fitmatecompose.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val tasksRepository: TasksRepository) :
    ViewModel() {
        val tasksList = tasksRepository.tasksList

    fun addTask(task: TaskModel) {
        viewModelScope.launch {
            tasksRepository.addTask(task)
        }
    }

    fun getSize() {
        viewModelScope.launch {
            tasksRepository.getSize()
        }

    }
}