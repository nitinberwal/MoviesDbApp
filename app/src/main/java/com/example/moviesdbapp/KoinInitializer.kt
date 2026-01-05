package com.example.moviesdbapp

import android.content.Context
import androidx.startup.Initializer
import com.example.moviesdbapp.di.appModule
import com.example.moviesdbapp.di.networkModule
import com.example.moviesdbapp.di.repoModule
import com.example.moviesdbapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class KoinInitializer: Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin { androidLogger(Level.ERROR)
        androidContext(context)
        modules(networkModule, viewModelModule, repoModule, appModule)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }


}