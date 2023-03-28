package com.faruqabdulhakim.githubuser.data

sealed class Result<out R> private constructor() {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}