package com.example.enbdassignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.enbdassignment.data.models.entities.ImageEntity

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(images: List<ImageEntity>)

    @Query("SELECT * FROM images WHERE searchWord = :word")
    fun getImagesBySearchWord(word: String): List<ImageEntity>

}