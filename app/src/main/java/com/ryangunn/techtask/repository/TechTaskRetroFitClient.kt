package com.ryangunn.techtask.repository

import com.ryangunn.techtask.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TechTaskRetroFitClient {

    private const val BASE_URL = "https://private-f699e3-masteryinterview.apiary-mock.com"

   private fun getRetroFit():Retrofit {
       val builder = OkHttpClient.Builder()
       val okHttpClient = builder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
           val request: Request =
               chain.request().newBuilder().addHeader("clientId", BuildConfig.CLIENT_ID).build()
           chain.proceed(request)
       }).build()
       return Retrofit.Builder().baseUrl(BASE_URL)
           .client(okHttpClient)
           .addConverterFactory(GsonConverterFactory.create()).build()
   }

    val instance : TechTaskAPIInterface by lazy {
        getRetroFit().create(TechTaskAPIInterface::class.java)
    }
}