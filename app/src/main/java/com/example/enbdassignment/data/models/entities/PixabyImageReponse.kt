package com.example.enbdassignment.data.models.entities

import android.os.Parcelable
import com.example.enbdassignment.data.HITS
import com.example.enbdassignment.data.TOTAL
import com.example.enbdassignment.data.TOTAL_HITS
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PixabyImageReponse(
    @SerializedName(HITS) val hits: List<ImageEntity>,
    @SerializedName(TOTAL) val total: Int,
    @SerializedName(TOTAL_HITS) val totalHits: Int
) : Parcelable