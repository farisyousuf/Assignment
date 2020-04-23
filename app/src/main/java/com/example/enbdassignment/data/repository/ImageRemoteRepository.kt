package com.example.enbdassignment.data.repository

import com.example.enbdassignment.data.api.PixabayApi
import com.example.enbdassignment.data.models.entities.PixabyImageReponse
import com.example.enbdassignment.util.Constants
import io.reactivex.Single
import javax.inject.Inject

class ImageRemoteRepository @Inject constructor(private val pixabayApi: PixabayApi) {

    fun search(word: String, pageNumber: Int): Single<PixabyImageReponse> {
        return pixabayApi.searchImage(Constants.API_KEY, word, Constants.IMAGE_TYPE, pageNumber)
    }
}