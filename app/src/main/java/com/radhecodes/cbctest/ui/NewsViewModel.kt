package com.radhecodes.cbctest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhecodes.cbctest.repository.NewsRepository
import com.radhecodes.cbctest.repository.model.News
import com.radhecodes.cbctest.repository.model.Status
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    fun getNewsFromApi(): LiveData<Status?> {
        return newsRepository.getNews()
    }

    fun getNewsFromLocalDb(): LiveData<List<News>> {
        return newsRepository.getNewsFromDb()
    }

    fun deleteAndInsertAllNews(newsList: List<News>) = viewModelScope.launch{
        newsRepository.deleteAndInsertAllNews(newsList)
    }
}