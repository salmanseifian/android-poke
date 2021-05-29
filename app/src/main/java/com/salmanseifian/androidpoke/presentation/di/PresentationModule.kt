package com.salmanseifian.androidpoke.presentation.di

import com.salmanseifian.androidpoke.presentation.mapper.*
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class PresentationModule {

    @Provides
    @Reusable
    fun provideEvolutionChainRepositoryToUiModelMapper(): EvolutionChainRepositoryToUiModelMapper =
        EvolutionChainRepositoryToUiModelMapperImpl()

    @Provides
    @Reusable
    fun providePokemonDetailsRepositoryToUiModelMapper(): PokemonDetailsRepositoryToUiModelMapper =
        PokemonDetailsRepositoryToUiModelMapperImpl()

    @Provides
    @Reusable
    fun providePokemonSpeciesRepositoryToUiModelMapper(): PokemonSpeciesRepositoryToUiModelMapper =
        PokemonSpeciesRepositoryToUiModelMapperImp()


}