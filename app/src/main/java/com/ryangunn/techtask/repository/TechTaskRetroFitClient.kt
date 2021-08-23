package com.ryangunn.techtask.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TechTaskRetroFitClient {

    private const val BASE_URL = "https://private-f699e3-masteryinterview.apiary-mock.com"

   private fun getRetroFit():Retrofit{
        val okHttpClient = OkHttpClient.Builder().build()
       return Retrofit.Builder().baseUrl(BASE_URL)
           .client(okHttpClient)
           .addConverterFactory(GsonConverterFactory.create()).build()
   }

    val instance : TechTaskAPIInterface by lazy {
        getRetroFit().create(TechTaskAPIInterface::class.java)
    }
}