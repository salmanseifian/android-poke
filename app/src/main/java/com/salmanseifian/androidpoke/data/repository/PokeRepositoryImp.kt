package com.salmanseifian.androidpoke.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.salmanseifian.androidpoke.data_api.model.EvolutionChainResponse
import com.salmanseifian.androidpoke.data_api.model.PokemonDetailsResponse
import com.salmanseifian.androidpoke.data.model.PokemonRepositoryModel
import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.data.PokemonSpeciesDataSource
import com.salmanseifian.androidpoke.utils.NETWORK_PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class PokeRepositoryImp @Inject constructor(
    private val pokeService: PokeService,
    private val pokemonSpeciesDataSource: PokemonSpeciesDataSource
) : PokeRepository {

    override fun getAllPokemonSpecies(): Flow<PagingData<PokemonRepositoryModel>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {
                pokemonSpeciesDataSource
            }
        ).flow
    }

    override suspend fun getPokemonDetails(id: Int): Resource<PokemonDetailsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(pokeService.getPokemonDetails(id))
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }

    override suspend fun getEvolutionChain(chainId: Int): Resource<EvolutionChainResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(pokeService.getEvolutionChain(chainId))
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}