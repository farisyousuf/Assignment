package com.example.enbdassignment.data.api

import com.example.enbdassignment.data.IMAGE_TYPE
import com.example.enbdassignment.data.KEY
import com.example.enbdassignment.data.QUERY
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET
    fun searchImage(@Query(KEY) key: String, @Query(QUERY) query: String, @Query(IMAGE_TYPE) type: String)
}