package com.example.tsundokubreak

import android.app.Application
import androidx.room.Room
import com.example.tsundokubreak.domain.service.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree




@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.tag("mizukiTag")

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }
}
