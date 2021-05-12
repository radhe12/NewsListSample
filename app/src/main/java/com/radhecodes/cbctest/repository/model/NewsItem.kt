package com.radhecodes.cbctest.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsItem(
        @SerializedName("description")
        @Expose
        private val description: String? = null,

        @SerializedName("id")
        @Expose val id: Int? = null,

        @SerializedName("images")
        @Expose
        val images: Images? = null,

        @SerializedName("language")
        @Expose
        private val language: String? = null,

        @SerializedName("readablePublishedAt")
        @Expose
        val readablePublishedAt: String? = null,

        @SerializedName("readableUpdatedAt")
        @Expose
        private val readableUpdatedAt: String? = null,

        @SerializedName("source")
        @Expose
        private val source: String? = null,

        @SerializedName("sourceId")
        @Expose
        private val sourceId: String? = null,

        @SerializedName("title")
        @Expose
        val title: String? = null,

        @SerializedName("type")
        @Expose
        val type: String? = null,

        @SerializedName("version")
        @Expose
        private val version: String? = null)
