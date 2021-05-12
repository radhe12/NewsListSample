package com.radhecodes.cbctest.services

import com.radhecodes.cbctest.repository.model.NewsItem
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    companion object {
        const val BASE_URL = "https://www.cbc.ca/aggregate_api/v1/"
    }

    @GET("items?lineupSlug=news")
    suspend fun getNews(): List<NewsItem>
}