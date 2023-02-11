package com.example.taskapp.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapp.ui.home.new_task.TaskDao
import com.example.taskapp.ui.home.new_task.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}