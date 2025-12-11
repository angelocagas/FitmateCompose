package com.angelodev.fitmatecompose.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelodev.fitmatecompose.database.entities.EntityTasksTable

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): LiveData<List<EntityTasksTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: EntityTasksTable)


}