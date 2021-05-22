package com.salmanseifian.androidpoke.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.salmanseifian.androidpoke.data.Resource
import com.salmanseifian.androidpoke.data.model.PokemonDetailsResponse
import com.salmanseifian.androidpoke.data.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

    fun getAllPokemonSpecies(): Flow<PagingData<PokemonSpecies>>

    suspend fun getPokemonDetails(id: Int): Resource<PokemonDetailsResponse>
}


