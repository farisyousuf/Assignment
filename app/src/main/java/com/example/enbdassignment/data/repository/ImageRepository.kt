package com.example.enbdassignment.data.repository

import com.example.enbdassignment.data.models.common.ResultState
import com.example.enbdassignment.data.models.entities.ImageEntity
import com.example.enbdassignment.data.models.entities.PixabyImageReponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageRepository @Inject
constructor(
    private val remoteRepository: ImageRemoteRepository,
    private val cacheRepository: ImageCacheRepository
) {
    fun searchImage(query: String, pageNumber: Int): Single<ResultState<PixabyImageReponse>> {
        return remoteRepository.search(query, pageNumber).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).map { imageList ->
                imageList.hits.onEach {
                    it.searchWord = query
                }
                cacheRepository.insertAll(imageList.hits)
                ResultState.Success(imageList) as ResultState<PixabyImageReponse>
            }.onErrorReturn {
                ResultState.Error("No Data Found")
            }
    }

    fun getImagesFromCache(query: String): List<ImageEntity> {
        return cacheRepository.search(query)
    }
}