package com.angelodev.fitmatecompose.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.angelodev.fitmatecompose.models.TaskModel

@Entity(tableName = "tasks_table")
data class EntityTasksTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val title: String,
    val description: String,
    val date: String,
    val time: String,
)

fun EntityTasksTable.toModel() = TaskModel(
    title = title,
    description = description,
    date = date,
    time = time,
)