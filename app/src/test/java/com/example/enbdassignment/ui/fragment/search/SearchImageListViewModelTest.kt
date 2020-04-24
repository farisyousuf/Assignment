package com.example.enbdassignment.ui.fragment.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.example.enbdassignment.base.BaseUnitTest
import com.example.enbdassignment.data.api.PixabayApi
import com.example.enbdassignment.data.local.dao.ImageDao
import com.example.enbdassignment.data.local.db.AppDb
import com.example.enbdassignment.data.repository.ImageCacheRepository
import com.example.enbdassignment.data.repository.ImageRemoteRepository
import com.example.enbdassignment.data.repository.ImageRepository
import com.example.enbdassignment.utils.MockResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(JUnit4::class)
class SearchImageListViewModelTest : BaseUnitTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: SearchImageListViewModel
    lateinit var imageRepository: ImageRepository
    lateinit var imageRemoteRepository: ImageRemoteRepository
    lateinit var imageCacheRepository: ImageCacheRepository
    lateinit var appDb: AppDb
    lateinit var imageDao: ImageDao

    override fun setUp() {
        super.setUp()
        appDb = Room.inMemoryDatabaseBuilder(
            context, AppDb::class.java
        ).allowMainThreadQueries().build()
        imageDao = appDb.imageDao()
        imageRemoteRepository = ImageRemoteRepository(retrofit.create(PixabayApi::class.java))
        imageCacheRepository = ImageCacheRepository(imageDao)
        imageRepository = ImageRepository(imageRemoteRepository, imageCacheRepository)
        viewModel = SearchImageListViewModel(imageRepository)
    }

    @Test
    fun testSearch() {
        mockWebServer.enqueue(
            MockResponse.createMockResponse(
                "image_list_response",
                HttpURLConnection.HTTP_OK
            )
        )
        viewModel.search("apple")
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS)
        Assert.assertTrue(viewModel.items.isNotEmpty())
        Assert.assertTrue(viewModel.lastSearchedWord == "apple")
        Assert.assertFalse(viewModel.requestInProgress)
        Assert.assertFalse(viewModel.allHitsLoaded)
    }

    override fun tearDown() {
        super.tearDown()
        appDb.close()
    }
}