package com.radhecodes.cbctest.repository

import com.radhecodes.cbctest.repository.database.NewsDao
import com.radhecodes.cbctest.repository.model.News
import com.radhecodes.cbctest.services.NewsService
import com.radhecodes.cbctest.utils.Resource
import com.radhecodes.cbctest.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<Resource<List<News>>>
}

class NewsRepositoryImpl(private val newsDao: NewsDao): NewsRepository {

    private var retrofitHelper = RetrofitHelper()
    private val newsService: NewsService = retrofitHelper.getNewsService()

     override fun getNews()  = networkBoundResource(
        query = {
            newsDao.getAllNews()
        },
        fetch = {
            newsService.getNews()
        },
        saveFetchResult = { it1 ->
            if(!it1.isNullOrEmpty()) {
                val newsList = it1.map {
                    News(
                        it.id!!,
                        it.title!!,
                        it.images?.square_140!!,
                        it.readablePublishedAt!!,
                        it.type!!
                    )
                }
                newsDao.deleteAndInsertAll(newsList)
            }
        }
    )

}