package com.example.enbdassignment.data.api

import com.example.enbdassignment.data.IMAGE_TYPE
import com.example.enbdassignment.data.KEY
import com.example.enbdassignment.data.PAGE
import com.example.enbdassignment.data.QUERY
import com.example.enbdassignment.data.models.entities.PixabyImageReponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("/api/")
    fun searchImage(
        @Query(QUERY) query: String,
        @Query(IMAGE_TYPE) type: String,
        @Query(PAGE) pageNumber: Int
    ): Single<PixabyImageReponse>
}