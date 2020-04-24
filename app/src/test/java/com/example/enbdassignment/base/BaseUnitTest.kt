package com.example.enbdassignment.base

import android.content.Context
import com.example.enbdassignment.di.modules.OkHttpClientModule
import com.example.enbdassignment.di.modules.RetrofitModule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Retrofit
import java.io.File

@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
abstract class BaseUnitTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var testScheduler: TestScheduler
    lateinit var retrofit: Retrofit
    lateinit var context: Context

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        // Initializes the mock environment
        MockitoAnnotations.initMocks(this)

        context = PowerMockito.mock(Context::class.java)

        // Initializes the mock webserver
        mockWebServer = MockWebServer()
        startMockWebserver()

        // Mocks the generic android dependencies such as Context, Looper, etc.
        testScheduler = TestScheduler()

        // Initializes the retrofit dependencies
        initDependencies()

        // Sets the RXJava schedulers for unit tests
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    /**
     * This method initializes the retrofit module
     */
    private fun initDependencies() {
        val retrofitModule = RetrofitModule()
        val okHttpClientModule = OkHttpClientModule()
        val requestInterceptor = okHttpClientModule.requestInterceptor()
        val createLoggingInterceptor = okHttpClientModule.httpLoggingInterceptor()
        val httpClient = okHttpClientModule.okHttpClient(
            createLoggingInterceptor,
            requestInterceptor
        )
        val gsonConverter = retrofitModule.gsonConverterFactory()
        val serverUrl = mockWebServer.url("/").toString()
        retrofit = retrofitModule.provideRetrofit(serverUrl, httpClient, gsonConverter)
    }

    /**
     * Method which starts the mockwebserver
     */
    private fun startMockWebserver() {
        mockWebServer.start(8081)
    }

    /**
     * Method which stops the mock webserver
     */
    private fun stopMockWebserver() {
        mockWebServer.shutdown()
    }


    fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

    @After
    open fun tearDown() {
        stopMockWebserver()
    }
}