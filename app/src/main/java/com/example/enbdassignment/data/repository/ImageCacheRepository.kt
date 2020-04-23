package com.example.enbdassignment.data.repository

import com.example.enbdassignment.data.local.dao.ImageDao
import com.example.enbdassignment.data.models.entities.ImageEntity
import javax.inject.Inject

class ImageCacheRepository @Inject constructor(private val imageDao: ImageDao) {
    fun insertAll(images: List<ImageEntity>?) {
        if (images != null)
            imageDao.insertAll(images)
    }

    fun search(word: String) = imageDao.getImagesBySearchWord(word)
}