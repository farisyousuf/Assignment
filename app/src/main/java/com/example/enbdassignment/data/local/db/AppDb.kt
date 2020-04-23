package com.example.enbdassignment.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.enbdassignment.data.local.dao.ImageDao

@Database(entities = [], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}