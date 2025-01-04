package com.example.educational_practice

import android.app.Application
import androidx.room.Room

class App : Application() {
    companion object {
        lateinit var instance: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        instance = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

}
