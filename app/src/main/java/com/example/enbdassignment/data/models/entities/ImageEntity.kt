package com.example.enbdassignment.data.models.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey
    val id: Int,
    val comments: Int? = null,
    val previewURL: String? = null,
    val largeImageURL: String? = null,
    val likes: Int? = null,
    val tags: String? = null,
    val type: String? = null,
    var searchWord: String? = null
) : Parcelable