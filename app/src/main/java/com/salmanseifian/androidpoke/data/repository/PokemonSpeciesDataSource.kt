package com.salmanseifian.androidpoke.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.salmanseifian.androidpoke.data.model.PokemonSpecies
import com.salmanseifian.androidpoke.data.remote.PokeService
import com.salmanseifian.androidpoke.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonSpeciesDataSource @Inject constructor(private val pokeService: PokeService) :
    PagingSource<Int, PokemonSpecies>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonSpecies>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonSpecies> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                pokeService.getPokemonSpecies(params.loadSize, offset(page, params.loadSize))
            val allSpecies = response.pokemonSpecies
            LoadResult.Page(
                data = allSpecies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.next == null) null else page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


    private fun offset(page: Int, itemsPerPage: Int) = (page - 1) * itemsPerPage
}