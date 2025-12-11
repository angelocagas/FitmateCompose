package com.angelodev.fitmatecompose.utilities.di

import android.content.Context
import androidx.room.Room
import com.angelodev.fitmatecompose.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideTasksDao(database: AppDatabase) = database.tasksDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "fitmate_database"
        ).fallbackToDestructiveMigration().build()
    }
}