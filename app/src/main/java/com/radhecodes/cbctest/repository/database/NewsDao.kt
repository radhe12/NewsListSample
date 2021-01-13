package com.radhecodes.cbctest.repository.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.radhecodes.cbctest.repository.model.News

@Dao
interface NewsDao {

    @Transaction
    suspend fun deleteAndInsertAll(newsList: List<News>) {
        removeAll()
        insertNewsList(newsList)
    }

    @Query("SELECT * FROM News")
    fun getAllNews(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsList(newsList: List<News>)

    @Query("DELETE FROM News")
    suspend fun removeAll()

}