package com.salmanseifian.androidpoke.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.salmanseifian.androidpoke.CoroutineTestRule
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.data.repository.Resource
import com.salmanseifian.androidpoke.presentation.mapper.EvolutionChainRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.mapper.PokemonDetailsRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.model.EvolutionChainUiModel
import com.salmanseifian.androidpoke.presentation.model.PokemonDetailsUiModel
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonDetailsViewModelTest {

    private lateinit var cut: PokemonDetailsViewModel

    @get:Rule
    val rule = CoroutineTestRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var pokeRepository: PokeRepository

    @Mock
    private lateinit var pokemonDetailsRepositoryToUiModelMapper: PokemonDetailsRepositoryToUiModelMapper

    @Mock
    private lateinit var evolutionChainRepositoryToUiModelMapper: EvolutionChainRepositoryToUiModelMapper


    @Mock
    private lateinit var pokemonDetailsObserver: Observer<PokemonDetailsUiModel>

    @Mock
    private lateinit var speciesUiModelObserver: Observer<SpeciesUiModel>

    @Mock
    private lateinit var isLoadingObserver: Observer<Boolean>


    private val evolutionChainRepositoryModel = EvolutionChainRepositoryModel(
        listOf(
            Pair(
                SpeciesRepositoryModel(
                    "bulbasaur",
                    "https://pokeapi.co/api/v2/pokemon-species/1/"
                ), SpeciesRepositoryModel(
                    "ivysaur",
                    "https://pokeapi.co/api/v2/pokemon-species/2/"
                )
            ),
            Pair(
                SpeciesRepositoryModel(
                    "ivysaur",
                    "https://pokeapi.co/api/v2/pokemon-species/2/"
                ),
                SpeciesRepositoryModel(
                    "venusaur",
                    "https://pokeapi.co/api/v2/pokemon-species/3/"
                )
            )
        )

    )

    @Before
    fun setUp() {
        cut = PokemonDetailsViewModel(
            pokeRepository,
            pokemonDetailsRepositoryToUiModelMapper,
            evolutionChainRepositoryToUiModelMapper
        ).apply {
            pokemonDetails.observeForever(pokemonDetailsObserver)
            evolution.observeForever(speciesUiModelObserver)
            isLoading.observeForever(isLoadingObserver)
        }
    }

    @Test
    fun `When getEvolutionChain then getEvolutionChain invoked with success result`() {
        rule.dispatcher.runBlockingTest {

            val result = Resource.Success(evolutionChainRepositoryModel)
            val channel = Channel<Resource<EvolutionChainRepositoryModel>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .`when`(pokeRepository)
                .getEvolutionChain(1)


            launch {
                channel.send(result)
            }

            cut.getEvolutionChain(
                "https://pokeapi.co/api/v2/pokemon-species/1/",
                "https://pokeapi.co/api/v2/evolution-chain/1/"
            )

            verify(speciesUiModelObserver).onChanged(
                SpeciesUiModel(
                    "ivysaur",
                    "https://pokeapi.co/api/v2/pokemon-species/2/"
                )
            )


        }
    }


    @Test
    fun `When getEvolutionChain then getEvolutionChain invoked with failure result`() {
        rule.dispatcher.runBlockingTest {
            doReturn(Resource.Failure(true, 400, null))
                .`when`(pokeRepository)
                .getEvolutionChain(1)
//
//            assertEquals(secondItem, Resource.Failure(true, 400, null))

        }
    }

    @Test
    fun `When getPokemonDetails then getPokemonDetails invoked with success result`() {
        rule.dispatcher.runBlockingTest {

            val result = Resource.Success(
                PokemonDetailsRepositoryModel(
                    "https://pokeapi.co/api/v2/evolution-chain/1/",
                    "A strange seed was\\nplanted on its\\nback at birth.\\fThe plant sprouts\\nand grows with\\nthis POKéMON.",
                    1,
                    "bulbasaur"
                )
            )

            val channel = Channel<Resource<PokemonDetailsRepositoryModel>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .`when`(pokeRepository)
                .getPokemonDetails(1)

            launch {
                channel.send(result)
            }

            cut.getPokemonDetails("https://pokeapi.co/api/v2/pokemon-species/1/")

            val expected = PokemonDetailsUiModel(
                "https://pokeapi.co/api/v2/evolution-chain/1/",
                "A strange seed was\\nplanted on its\\nback at birth.\\fThe plant sprouts\\nand grows with\\nthis POKéMON.",
                1,
                "bulbasaur"
            )

            verify(pokemonDetailsObserver).onChanged(expected)

        }
    }

    @Test
    fun `When getPokemonDetails then getPokemonDetails invoked with failure result`() {
        rule.dispatcher.runBlockingTest {

            val expected400Failure = Resource.Failure(true, 400, null)

            doReturn(
                expected400Failure
            )
                .`when`(pokeRepository)
                .getPokemonDetails(1)


//            assertEquals(secondItem, expected400Failure)

        }
    }

}