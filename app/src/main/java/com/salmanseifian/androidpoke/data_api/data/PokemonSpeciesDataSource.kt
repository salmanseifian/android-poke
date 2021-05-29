package com.salmanseifian.androidpoke.data_api.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.salmanseifian.androidpoke.data.model.PokemonRepositoryModel
import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.mapper.PokemonSpeciesResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.utils.STARTING_OFFSET_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonSpeciesDataSource constructor(
    private val pokeService: PokeService,
    private val pokemonSpeciesResponseToRepositoryModelMapper: PokemonSpeciesResponseToRepositoryModelMapper
) :
    PagingSource<Int, PokemonRepositoryModel>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonRepositoryModel>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonRepositoryModel> {
        val offset = params.key ?: STARTING_OFFSET_INDEX
        val loadSize = params.loadSize
        return try {
            val response =
                pokeService.getPokemonSpecies(offset, params.loadSize)
            val allSpecies =
                pokemonSpeciesResponseToRepositoryModelMapper.toRepositoryModel(response).pokemons
            LoadResult.Page(
                data = allSpecies,
                prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - loadSize,
                nextKey = if (response.next == null) null else offset + loadSize
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}