package com.radhecodes.cbctest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.radhecodes.cbctest.repository.database.NewsDao
import com.radhecodes.cbctest.repository.model.ApiResponse
import com.radhecodes.cbctest.repository.model.News
import com.radhecodes.cbctest.repository.model.Status
import com.radhecodes.cbctest.repository.model.StatusType
import com.radhecodes.cbctest.services.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface NewsRepository {
    fun getNews(): MutableLiveData<Status>

    fun getNewsFromDb(): LiveData<List<News>>

    suspend fun insertNewsList(news: List<News>)

    suspend fun deleteAndInsertAllNews(news: List<News>)
}

class NewRepositoryImpl(private val newsDao: NewsDao): NewsRepository {
    private var fetchedResponse: MutableLiveData<Status> = MutableLiveData()

    private var retrofitHelper = RetrofitHelper()
    private val newsService: NewsService = retrofitHelper.getNewsService()
    override fun getNews(): MutableLiveData<Status> {
        val call: Call<ApiResponse> = newsService.getNews()

        call.enqueue(object : Callback<ApiResponse?> {
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                if(response.isSuccessful) {
                    fetchedResponse.value = Status(StatusType.SUCCESS, response.body())
                } else {
                    if(response.errorBody() != null) {
                        fetchedResponse.value = Status(StatusType.ERROR, response.errorBody())
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                fetchedResponse.value = Status(StatusType.NETWORK_ERROR, t.message)
            }

        })
        return fetchedResponse
    }

    override fun getNewsFromDb(): LiveData<List<News>> {
        return newsDao.getAllNews()
    }

    override suspend fun insertNewsList(news: List<News>) {
        newsDao.insertNewsList(news)
    }

    override suspend fun deleteAndInsertAllNews(news: List<News>) {
        newsDao.deleteAndInsertAll(news)
    }

}