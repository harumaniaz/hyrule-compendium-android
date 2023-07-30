package com.example.hyrulecompendium.di

import com.example.hyrulecompendium.BuildConfig
import com.example.hyrulecompendium.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiModule {
    private fun provideLogging() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    private fun provideClient() = OkHttpClient().newBuilder().apply {
        addInterceptor(provideLogging())
    }.build()

    fun provideService(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(provideClient())
        .build()
        .create(ApiService::class.java)
}