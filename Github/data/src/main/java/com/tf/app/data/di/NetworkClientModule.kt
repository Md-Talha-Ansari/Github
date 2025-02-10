package com.tf.app.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkClientModule {

    @Provides
    fun provideRetrofitInstance(httpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
    }


    @Provides
    fun provideOkHttpClient(cache: Cache) : OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .cache(cache)//Add this to cache request. This respects header values for cache.
            .build()
    }


    @Provides
    fun getHttpClientCache(@ApplicationContext context: Context) : Cache {
        return Cache(context.cacheDir, 5 * 1024 * 1024)
    }
}