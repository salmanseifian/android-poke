package com.salmanseifian.androidpoke.data

import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import kotlinx.coroutines.flow.Flow

interface PokeRemoteSource {

    suspend fun getSpecieDetails(id: Int): Flow<PokemonDetailsRepositoryModel>

    suspend fun getEvolutionChain(chainId: Int): Flow<EvolutionChainRepositoryModel>

}