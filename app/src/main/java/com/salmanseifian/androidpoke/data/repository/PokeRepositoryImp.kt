package com.salmanseifian.androidpoke.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.salmanseifian.androidpoke.data.di.IoDispatcher
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.data.PokemonSpeciesDataSource
import com.salmanseifian.androidpoke.data_api.mapper.EvolutionChainResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonDetailsResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonSpeciesResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.utils.NETWORK_PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject

class PokeRepositoryImp @Inject constructor(
    private val pokeService: PokeService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
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

    override fun getPokemonDetails(id: Int): Flow<Resource<PokemonDetailsRepositoryModel>> {
        return flow {
            emit(
                Resource.Success(
                    pokemonDetailsResponseToRepositoryModelMapperImp.toRepositoryModel(
                        pokeService.getPokemonDetails(id)
                    )
                )
            )
        }
            .catch {
                when (it) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            it.code(),
                            it.response()?.errorBody()
                        )
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getEvolutionChain(chainId: Int): Flow<Resource<EvolutionChainRepositoryModel>> {
        return flow {
            emit(
                Resource.Success(
                    evolutionChainResponseToRepositoryModelMapper.toRepositoryModel(
                        pokeService.getEvolutionChain(chainId)
                    )
                )
            )
        }
            .catch {
                when (it) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            it.code(),
                            it.response()?.errorBody()
                        )
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
            .flowOn(ioDispatcher)

    }

}