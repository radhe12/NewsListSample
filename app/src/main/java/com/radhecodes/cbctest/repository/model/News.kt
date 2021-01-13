package com.radhecodes.cbctest.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News (
    @PrimaryKey
    private val newsId: Int,
    private val title: String,
    private val imageUrl: String,
    private val publishedTime: String,
    private val type: String) {
    fun getNewsId(): Int {
        return newsId
    }

    fun getTitle(): String {
        return title
    }

    fun getImageUrl(): String {
        return imageUrl
    }

    fun getPublishedTime(): String {
        return publishedTime
    }

    fun getType(): String {
        return type
    }
}