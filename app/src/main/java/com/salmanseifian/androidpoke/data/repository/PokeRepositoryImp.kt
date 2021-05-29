package com.salmanseifian.androidpoke.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.data.PokemonSpeciesDataSource
import com.salmanseifian.androidpoke.data_api.mapper.EvolutionChainResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonDetailsResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonSpeciesResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.utils.NETWORK_PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class PokeRepositoryImp @Inject constructor(
    private val pokeService: PokeService,
    private val pokemonSpeciesResponseToRepositoryModelMapper: PokemonSpeciesResponseToRepositoryModelMapper,
    private val pokemonDetailsResponseToRepositoryModelMapperImp: PokemonDetailsResponseToRepositoryModelMapper,
    private val evolutionChainResponseToRepositoryModelMapper: EvolutionChainResponseToRepositoryModelMapper
) : PokeRepository {

    override fun getAllPokemonSpecies(): Flow<PagingData<SpeciesRepositoryModel>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {
                PokemonSpeciesDataSource(pokeService)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                pokemonSpeciesResponseToRepositoryModelMapper.toRepositoryModel(it)
            }
        }
    }

    override suspend fun getPokemonDetails(id: Int): Resource<PokemonDetailsRepositoryModel> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    pokemonDetailsResponseToRepositoryModelMapperImp.toRepositoryModel(
                        pokeService.getPokemonDetails(id)
                    )
                )
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

    override suspend fun getEvolutionChain(chainId: Int): Resource<EvolutionChainRepositoryModel> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    evolutionChainResponseToRepositoryModelMapper.toRepositoryModel(
                        pokeService.getEvolutionChain(chainId)
                    )
                )
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