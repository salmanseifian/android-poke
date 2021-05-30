package com.salmanseifian.androidpoke.data.repository

import com.salmanseifian.androidpoke.data_api.PokeService
import com.salmanseifian.androidpoke.data_api.mapper.EvolutionChainResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonDetailsResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.mapper.PokemonSpeciesResponseToRepositoryModelMapper
import com.salmanseifian.androidpoke.data_api.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be true`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class PokeRepositoryImpTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var pokemonSpeciesResponseToRepositoryModelMapper: PokemonSpeciesResponseToRepositoryModelMapper

    @Mock
    private lateinit var pokemonDetailsResponseToRepositoryModelMapperImp: PokemonDetailsResponseToRepositoryModelMapper


    @Mock
    private lateinit var evolutionChainResponseToRepositoryModelMapper: EvolutionChainResponseToRepositoryModelMapper


    @ExperimentalCoroutinesApi
    @Test
    fun `should get pokemon details on success`() =
        testDispatcher.runBlockingTest {

            val pokemonDetails = PokemonDetailsResponse(
                EvolutionChain("https://pokeapi.co/api/v2/evolution-chain/1/"),
                listOf(
                    FlavorTextEntry(
                        "A strange seed was\\nplanted on its\\nback at birth.\\fThe plant sprouts\\nand grows with\\nthis POKéMON.",
                        Language("en", "https://pokeapi.co/api/v2/language/9/"),
                        Version("red", "https://pokeapi.co/api/v2/version/1/")
                    )
                ),
                1,
                "bulbasaur"
            )

            val pokeService = mock<PokeService> {
                onBlocking { getPokemonDetails(1) } doReturn pokemonDetails
            }

            val cut = PokeRepositoryImp(
                pokeService, testDispatcher,
                pokemonSpeciesResponseToRepositoryModelMapper,
                pokemonDetailsResponseToRepositoryModelMapperImp,
                evolutionChainResponseToRepositoryModelMapper
            )

            val flow = cut.getPokemonDetails(1)

            flow.collect {
                it.isSuccess.`should be true`()
            }

        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should throw error for pokemon details if any exception is thrown`() =
        testDispatcher.runBlockingTest {

            val pokeService = mock<PokeService> {
                onBlocking { getPokemonDetails(1) } doAnswer { throw IOException() }
            }

            val cut = PokeRepositoryImp(
                pokeService, testDispatcher,
                pokemonSpeciesResponseToRepositoryModelMapper,
                pokemonDetailsResponseToRepositoryModelMapperImp,
                evolutionChainResponseToRepositoryModelMapper
            )

            val flow = cut.getPokemonDetails(1)

            flow.collect {
                it.isFailure.`should be true`()
            }

        }
}