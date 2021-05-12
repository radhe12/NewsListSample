package com.radhecodes.cbctest.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News (
    @PrimaryKey
    val newsId: Int,
    val title: String,
    val imageUrl: String,
    val publishedTime: String,
    val type: String)