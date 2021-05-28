package com.salmanseifian.androidpoke.data.di

import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.data.repository.PokeRepositoryImp
import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.data.PokemonSpeciesDataSource
import com.salmanseifian.androidpoke.data_api.mapper.EvolutionChainResponseToRepositoryModelMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@InstallIn(ActivityRetainedComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Reusable
    fun providePokeRepository(
        pokeService: PokeService,
        pokemonSpeciesDataSource: PokemonSpeciesDataSource,
        evolutionChainResponseToRepositoryModelMapper: EvolutionChainResponseToRepositoryModelMapper
    ): PokeRepository = PokeRepositoryImp(
        pokeService, pokemonSpeciesDataSource,
        evolutionChainResponseToRepositoryModelMapper
    )
}