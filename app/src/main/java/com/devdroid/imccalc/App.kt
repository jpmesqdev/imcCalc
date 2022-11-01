package com.devdroid.imccalc

import android.app.Application
import com.devdroid.imccalc.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}