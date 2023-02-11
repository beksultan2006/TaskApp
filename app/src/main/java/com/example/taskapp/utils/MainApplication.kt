package com.example.taskapp.utils

import android.app.Application
import androidx.room.Room

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "tasks_db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        var appDatabase: AppDatabase? = null
    }
}