package com.radhecodes.cbctest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.radhecodes.cbctest.repository.NewsRepository

class NewsViewModel(newsRepository: NewsRepository): ViewModel() {
     val news = newsRepository.getNews().asLiveData()

     var selectedChip: MutableLiveData<Pair<String, Int>> = MutableLiveData()

     var typeChips: MutableLiveData<List<String>> = MutableLiveData()

     override fun onCleared() {
          super.onCleared()
     }
}