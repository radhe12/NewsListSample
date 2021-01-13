package com.radhecodes.cbctest.repository.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.radhecodes.cbctest.repository.RetrofitHelper
import com.radhecodes.cbctest.services.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface NewsRepository {
    fun getNews(): MutableLiveData<ApiResponse>
}

class NewRepositoryImpl: NewsRepository {
    private var fetchedResponse: MutableLiveData<ApiResponse> = MutableLiveData()

    private var retrofitHelper = RetrofitHelper()
    private val newsService: NewsService = retrofitHelper.getNewsService()
    override fun getNews(): MutableLiveData<ApiResponse> {
        val call: Call<ApiResponse> = newsService.getNews()

        call.enqueue(object : Callback<ApiResponse?> {
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                if(response.isSuccessful) {
                    fetchedResponse.value = response.body()
                } else {
                    Log.d("error", response.code().toString())
                }
            }

            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                t.message?.let { Log.d("error", it) }
            }

        })
        return fetchedResponse
    }

}