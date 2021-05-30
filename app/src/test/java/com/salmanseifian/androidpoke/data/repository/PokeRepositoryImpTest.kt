package com.salmanseifian.androidpoke.data.repository

import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.mapper.EvolutionChainResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonDetailsResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonSpeciesResponseToRepositoryModelMapper
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PokeRepositoryImpTest {

    private lateinit var cut: PokeRepositoryImp

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var pokeService: PokeService

    @Mock
    private lateinit var pokemonSpeciesResponseToRepositoryModelMapper: PokemonSpeciesResponseToRepositoryModelMapper

    @Mock
    private lateinit var pokemonDetailsResponseToRepositoryModelMapperImp: PokemonDetailsResponseToRepositoryModelMapper


    @Mock
    private lateinit var evolutionChainResponseToRepositoryModelMapper: EvolutionChainResponseToRepositoryModelMapper

    @Before
    fun setUp() {
        cut = PokeRepositoryImp(
            pokeService,
            testDispatcher,
            pokemonSpeciesResponseToRepositoryModelMapper,
            pokemonDetailsResponseToRepositoryModelMapperImp,
            evolutionChainResponseToRepositoryModelMapper
        )
    }


    @Test
    fun `should get pokemon details on failure`() {
        runBlocking {

            doReturn(Throwable())
                .`when`(pokeService)
                .getPokemonDetails(1)

//            whenever(cut.getEvolutionChain(1)).thenReturn(Resource.Failure(true, null, null))
        }
    }
}