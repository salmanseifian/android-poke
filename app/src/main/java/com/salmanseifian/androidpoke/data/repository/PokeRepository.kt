package com.salmanseifian.androidpoke.data.repository

import androidx.paging.PagingData
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.EvolutionChainResponse
import com.salmanseifian.androidpoke.data_api.model.PokemonDetailsResponse
import com.salmanseifian.androidpoke.data.model.PokemonRepositoryModel
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

    fun getAllPokemonSpecies(): Flow<PagingData<PokemonRepositoryModel>>

    suspend fun getPokemonDetails(id: Int): Resource<PokemonDetailsRepositoryModel>

    suspend fun getEvolutionChain(chainId: Int): Resource<EvolutionChainRepositoryModel>
}


