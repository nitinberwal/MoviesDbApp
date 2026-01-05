package com.example.moviesdbapp

import android.app.Application
import android.content.Context
import androidx.startup.AppInitializer

class MoviesApplication: Application() {

    companion object{
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        MoviesApplication.applicationContext = this
        initLibs(this)
    }

    private fun initLibs(application: MoviesApplication) {
        AppInitializer.getInstance(application).initializeComponent(KoinInitializer::class.java)
    }
}