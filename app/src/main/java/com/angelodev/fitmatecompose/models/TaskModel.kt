package com.angelodev.fitmatecompose.models

import com.angelodev.fitmatecompose.database.entities.EntityTasksTable

data class TaskModel(
    val title: String,
    val description: String,
    val date: String,
    val time: String,
)

fun TaskModel.toEntity() = EntityTasksTable(
    title = title,
    description = description,
    date = date,
    time = time,
)


