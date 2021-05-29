package com.salmanseifian.androidpoke.data.repository

import androidx.paging.PagingData
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonSpeciesRepositoryModel
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

    fun getAllPokemonSpecies(): Flow<PagingData<PokemonSpeciesRepositoryModel>>

    suspend fun getPokemonDetails(id: Int): Resource<PokemonDetailsRepositoryModel>

    suspend fun getEvolutionChain(chainId: Int): Resource<EvolutionChainRepositoryModel>
}


