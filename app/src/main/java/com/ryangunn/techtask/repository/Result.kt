package com.ryangunn.techtask.repository

import java.lang.Exception

sealed class Result<out T>{
    data class Success<out T>(val data:T):Result<T>()
    data class Loading(val loading:Boolean):Result<Nothing>()
    data class Error(val exception: Exception):Result<Nothing>()
}
