package com.salmanseifian.androidpoke.di

import com.salmanseifian.androidpoke.data.remote.PokeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): PokeService = Retrofit.Builder()
        .baseUrl(PokeService.POKE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokeService::class.java)

}