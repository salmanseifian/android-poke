package com.salmanseifian.androidpoke.data_api.di

import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.mapper.*
import com.salmanseifian.androidpoke.utils.POKE_API_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Reusable
    @Provides
    fun providePokemonSpeciesResponseToRepositoryModelMapper(): PokemonSpeciesResponseToRepositoryModelMapper =
        PokemonSpeciesResponseToRepositoryModelMapperImpl()

    @Reusable
    @Provides
    fun provideEvolutionChainResponseToRepositoryModelMapper(): EvolutionChainResponseToRepositoryModelMapper =
        EvolutionChainResponseToRepositoryModelMapperImpl()

    @Reusable
    @Provides
    fun providePokemonDetailsResponseToRepositoryModelMapper(): PokemonDetailsResponseToRepositoryModelMapper =
        PokemonDetailsResponseToRepositoryModelMapperImp()

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PokeService =
        retrofit.create(PokeService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(POKE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}