package com.example.enbdassignment.di.modules

import com.example.enbdassignment.data.api.PixabayApi
import com.example.enbdassignment.util.Configuration
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class])
class RetrofitModule {

    companion object {
        private const val BASE_URL = "baseUrl"
    }

    /*
   * The method returns the Retrofit object
   * */
    @Provides
    @Singleton
    fun provideRetrofit(
        @Named(BASE_URL) baseUrl: String, okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun gsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return Configuration.BASE_URL
    }

    @Singleton
    @Provides
    fun providePixabayApi(retrofit: Retrofit): PixabayApi {
        return retrofit.create(PixabayApi::class.java)
    }
}