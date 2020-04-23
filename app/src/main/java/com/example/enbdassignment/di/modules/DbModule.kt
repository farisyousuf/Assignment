package com.example.enbdassignment.di.modules

import android.content.Context
import androidx.room.Room
import com.example.enbdassignment.data.local.dao.ImageDao
import com.example.enbdassignment.data.local.db.AppDb
import com.example.enbdassignment.util.Configuration
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DbModule {
    
    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context, @Named("dbName") dbName: String): AppDb {
        return Room.databaseBuilder(
            context, AppDb::class.java, dbName
        ).allowMainThreadQueries()
            // Clear DB while upgrade or downgrade
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideBankBranchDao(appDb: AppDb): ImageDao {
        return appDb.imageDao()
    }


    @Singleton
    @Provides
    @Named("dbName")
    fun provideDbName(): String {
        return Configuration.DB_NAME
    }
}