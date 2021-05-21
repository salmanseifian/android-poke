package com.salmanseifian.androidpoke.data.repository

import androidx.paging.PagingData
import com.salmanseifian.androidpoke.data.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokeRepository {
    fun getAllPokemonSpecies(): Flow<PagingData<PokemonSpecies>>
}


