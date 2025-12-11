package com.angelodev.fitmatecompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angelodev.fitmatecompose.database.dao.TasksDao
import com.angelodev.fitmatecompose.database.entities.EntityTasksTable

@Database(
    entities = [
        EntityTasksTable::class

    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}