package com.example.enbdassignment.data.repository

import com.example.enbdassignment.data.local.dao.ImageDao
import com.example.enbdassignment.data.models.entities.ImageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageCacheRepository @Inject constructor(private val imageDao: ImageDao) {
    fun insertAll(images: List<ImageEntity>?) {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            if (images != null)
                imageDao.insertAll(images)
        }
    }

    fun search(word: String) = imageDao.getImagesBySearchWord(word)
}