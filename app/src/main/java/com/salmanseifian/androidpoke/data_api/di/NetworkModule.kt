package com.salmanseifian.androidpoke.data_api.di

import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.data.PokemonSpeciesDataSource
import com.salmanseifian.androidpoke.data_api.mapper.EvolutionChainResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.EvolutionChainResponseToRepositoryModelMapperImpl
import com.salmanseifian.androidpoke.data_api.mapper.PokemonSpeciesResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonSpeciesResponseToRepositoryModelMapperImpl
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

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun providePokemonSpeciesDataSource(
        pokeService: PokeService,
        pokemonSpeciesResponseToRepositoryModelMapper: PokemonSpeciesResponseToRepositoryModelMapper,
    ): PokemonSpeciesDataSource =
        PokemonSpeciesDataSource(pokeService, pokemonSpeciesResponseToRepositoryModelMapper)


    @Reusable
    @Provides
    fun providePokemonSpeciesResponseToRepositoryModelMapper(): PokemonSpeciesResponseToRepositoryModelMapper =
        PokemonSpeciesResponseToRepositoryModelMapperImpl()

    @Reusable
    @Provides
    fun provideEvolutionChainResponseToRepositoryModelMapper(): EvolutionChainResponseToRepositoryModelMapper =
        EvolutionChainResponseToRepositoryModelMapperImpl()

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