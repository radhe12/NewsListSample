package com.radhecodes.cbctest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.radhecodes.cbctest.repository.model.ApiResponse
import com.radhecodes.cbctest.repository.model.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    fun getNews(): LiveData<ApiResponse?> {
        return newsRepository.getNews()
    }
}