package com.example.enbdassignment.data.models.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.enbdassignment.data.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey
    @SerializedName(ID) val id: Int,
    @SerializedName(COMMENTS) val comments: Int? = null,
    @SerializedName(PREVIEW_URL) val previewURL: String? = null,
    @SerializedName(WEB_FORMAT_URL) val webformatURL: String? = null,
    @SerializedName(LARGE_IMAGE_URL) val largeImageURL: String? = null,
    @SerializedName(LIKES) val likes: Int? = null,
    @SerializedName(TAGS) val tags: String? = null,
    @SerializedName(TYPE) val type: String? = null,
    @SerializedName(SEARCH_WORD) var searchWord: String? = null
) : Parcelable