package com.ryangunn.techtask.repository

import com.ryangunn.techtask.driver.model.Driver
import com.ryangunn.techtask.login.model.UserBody
import com.ryangunn.techtask.login.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TechTaskAPIInterface {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body userBody: UserBody): UserResponse

    @Headers("Content-Type: application/json")
    @GET("drivers")
    suspend fun getDrivers(): List<Driver>

}