package com.example.moviesdbapp.di

import com.example.moviesdbapp.data.remote.MoviesApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.sin

const val connectTimeout: Long = 15L // 15 seconds
const val readTimeout: Long = 15L // 15 seconds
val networkModule = module {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    single {
        OkHttpClient.Builder().connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    single {
        GsonBuilder().create()
    }

    single {
        provideRetrofitInstance(get())
    }

    single {
        get<Retrofit>().create(MoviesApi::class.java)
    }
}

fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder().setLenient().create()
    return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient).build()
}