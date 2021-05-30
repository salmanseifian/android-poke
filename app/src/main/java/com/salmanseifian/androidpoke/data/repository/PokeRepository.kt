package com.salmanseifian.androidpoke.data.repository

import androidx.paging.PagingData
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

    fun getAllPokemonSpecies(): Flow<PagingData<SpeciesRepositoryModel>>

    fun getPokemonDetails(id: Int): Flow<Result<PokemonDetailsRepositoryModel>>

    fun getEvolutionChain(chainId: Int): Flow<Result<EvolutionChainRepositoryModel>>
}


