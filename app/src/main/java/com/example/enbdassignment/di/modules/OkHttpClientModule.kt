package com.example.enbdassignment.di.modules

import com.example.enbdassignment.BuildConfig
import com.example.enbdassignment.data.KEY
import com.example.enbdassignment.util.Configuration.CALL_TIMEOUT
import com.example.enbdassignment.util.Configuration.CONNECT_TIMEOUT
import com.example.enbdassignment.util.Configuration.READ_TIMEOUT
import com.example.enbdassignment.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class OkHttpClientModule {

    /*
     * The method returns the Okhttp object
     * */
    @Provides
    @Singleton
    fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, @Named("requestInterceptor") requestInterceptor: Interceptor)
            : OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    @Provides
    @Named("requestInterceptor")
    fun requestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(KEY, Constants.API_KEY)
                .build()

            val request = originalRequest.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }
}