package com.example.moviesdbapp.utils

sealed class DataStatus<T>(val data: T? = null, val message: String? = null) {

    class Loading<T>(val isLoading: Boolean) : DataStatus<T>(null)

    class Pass<T>(data: T?) : DataStatus<T>(data)

    class Fail<T>(message: String?, data: T? = null) : DataStatus<T>(data, message)
}
