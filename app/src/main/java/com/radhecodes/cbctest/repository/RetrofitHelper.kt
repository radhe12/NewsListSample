package com.radhecodes.cbctest.repository

import com.radhecodes.cbctest.services.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    lateinit var retrofit: Retrofit

    private fun getHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun getNewsService(): NewsService {
        retrofit = Retrofit.Builder()
            .baseUrl(NewsService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()

        return retrofit.create(NewsService::class.java)
    }
}