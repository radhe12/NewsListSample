package com.radhecodes.cbctest.repository.database

import androidx.room.*
import com.radhecodes.cbctest.repository.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Transaction
    suspend fun deleteAndInsertAll(newsList: List<News>) {
        removeAll()
        insertNewsList(newsList)
    }

    @Query("SELECT * FROM News")
    fun getAllNews(): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsList(newsList: List<News>)

    @Query("DELETE FROM News")
    suspend fun removeAll()

}