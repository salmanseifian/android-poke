package com.salmanseifian.androidpoke.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.salmanseifian.androidpoke.data.model.PokemonSpecies
import com.salmanseifian.androidpoke.data.remote.PokeService
import com.salmanseifian.androidpoke.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemotePokeRepository @Inject constructor(private val pokeService: PokeService): PokeRepository {

    override fun getAllPokemonSpecies(): Flow<PagingData<PokemonSpecies>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE, initialLoadSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                PokemonSpeciesDataSource(pokeService)
            }
        ).flow
    }
}