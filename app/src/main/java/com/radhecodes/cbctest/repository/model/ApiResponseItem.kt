package com.radhecodes.cbctest.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponseItem {
    @SerializedName("description")
    @Expose
    private var description: String? = null
    
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    
    @SerializedName("images")
    @Expose
    private var images: Images? = null
    
    @SerializedName("language")
    @Expose
    private var language: String? = null
    
    @SerializedName("readablePublishedAt")
    @Expose
    private var readablePublishedAt: String? = null
    
    @SerializedName("readableUpdatedAt")
    @Expose
    private var readableUpdatedAt: String? = null
    
    @SerializedName("source")
    @Expose
    private var source: String? = null
    
    @SerializedName("sourceId")
    @Expose
    private var sourceId: String? = null
    
    @SerializedName("title")
    @Expose
    private var title: String? = null
    
    @SerializedName("type")
    @Expose
    private var type: String? = null
    
    @SerializedName("version")
    @Expose
    private var version: String? = null

    fun getTitle(): String? {
        return title
    }

    fun getPublishedTime(): String? {
        return readablePublishedAt
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    fun getImageUrl(): String? {
        return images?.square_140
    }

    fun getNewsId(): Int? {
        return id
    }

    fun getType(): String? {
        return type
    }
}