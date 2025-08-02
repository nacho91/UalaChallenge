package com.nacho.uala.challenge.domain.util

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    sealed class Error : Result<Nothing>() {
        data class Network(val exception: Throwable) : Error()
        data class Database(val exception: Throwable) : Error()
        data class Unknown(val exception: Throwable) : Error()
    }
}