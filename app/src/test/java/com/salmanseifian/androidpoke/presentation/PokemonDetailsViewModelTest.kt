package com.salmanseifian.androidpoke.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.salmanseifian.androidpoke.CoroutineTestRule
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
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
import org.mockito.kotlin.*


class PokemonDetailsViewModelTest {

    @get:Rule
    val rule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val pokeRepository = mock<PokeRepository>()

    private val pokemonDetailsRepositoryToUiModelMapper =
        mock<PokemonDetailsRepositoryToUiModelMapper>()

    private val evolutionChainRepositoryToUiModelMapper =
        mock<EvolutionChainRepositoryToUiModelMapper>()

    private val pokemonDetailsObserver = mock<Observer<PokemonDetailsUiModel>>()


    private val speciesUiModelObserver = mock<Observer<SpeciesUiModel>>()

    private val isLoadingObserver = mock<Observer<Boolean>>()


    private val pokemonDetailsRepositoryModel = PokemonDetailsRepositoryModel(
        "https://pokeapi.co/api/v2/evolution-chain/1/",
        "desc",
        1,
        "bulbasaur"
    )


    private val pokemonDetailsUiModel = PokemonDetailsUiModel(
        "https://pokeapi.co/api/v2/evolution-chain/1/",
        "desc",
        1,
        "bulbasaur"
    )


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

    private val evolutionChainUiModel = EvolutionChainUiModel(
        listOf(
            Pair(
                SpeciesUiModel(
                    "bulbasaur",
                    "https://pokeapi.co/api/v2/pokemon-species/1/"
                ), SpeciesUiModel(
                    "ivysaur",
                    "https://pokeapi.co/api/v2/pokemon-species/2/"
                )
            ),
            Pair(
                SpeciesUiModel(
                    "ivysaur",
                    "https://pokeapi.co/api/v2/pokemon-species/2/"
                ),
                SpeciesUiModel(
                    "venusaur",
                    "https://pokeapi.co/api/v2/pokemon-species/3/"
                )
            )
        )
    )


    private lateinit var pokemonDetailsViewModel: PokemonDetailsViewModel

    @Before
    fun setUp() {
        pokemonDetailsViewModel = PokemonDetailsViewModel(
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
    fun `should emit pokemon details on success`() = rule.dispatcher.runBlockingTest {

        val result = Result.success(pokemonDetailsRepositoryModel)

        val channel = Channel<Result<PokemonDetailsRepositoryModel>>()
        val flow = channel.consumeAsFlow()

        doReturn(flow)
            .`when`(pokeRepository)
            .getPokemonDetails(1)



        whenever(pokemonDetailsRepositoryToUiModelMapper.toUiModel(pokemonDetailsRepositoryModel)).thenReturn(
            pokemonDetailsUiModel
        )

        launch {
            channel.send(result)
        }

        pokemonDetailsViewModel.getPokemonDetails("https://pokeapi.co/api/v2/pokemon-species/1/")


        verify(pokeRepository, times(1)).getPokemonDetails(1)


        verify(pokemonDetailsObserver).onChanged(pokemonDetailsUiModel)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `should emit error on getPokemonDetails failure`() = rule.dispatcher.runBlockingTest {
        val result = Result.failure<PokemonDetailsRepositoryModel>(RuntimeException())
        val channel = Channel<Result<PokemonDetailsRepositoryModel>>()
        val flow = channel.consumeAsFlow()

        doReturn(flow)
            .whenever(pokeRepository)
            .getPokemonDetails(1)

        launch {
            channel.send(result)
        }

        pokemonDetailsViewModel.getPokemonDetails("https://pokeapi.co/api/v2/pokemon-species/1/")

        verify(isLoadingObserver).onChanged(false)
    }

    @Test
    fun `should  emit getEvolutionChain on success`() =
        rule.dispatcher.runBlockingTest {

            val result = Result.success(evolutionChainRepositoryModel)
            val channel = Channel<Result<EvolutionChainRepositoryModel>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .`when`(pokeRepository)
                .getEvolutionChain(1)

            whenever(evolutionChainRepositoryToUiModelMapper.toUiModel(evolutionChainRepositoryModel)).thenReturn(
                evolutionChainUiModel
            )


            launch {
                channel.send(result)
            }

            pokemonDetailsViewModel.getEvolutionChain(
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


    @Test
    fun `should emit error on getEvolutionChain failure`() =
        rule.dispatcher.runBlockingTest {

            val result = Result.failure<EvolutionChainRepositoryModel>(RuntimeException())
            val channel = Channel<Result<EvolutionChainRepositoryModel>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .`when`(pokeRepository)
                .getEvolutionChain(1)


            launch {
                channel.send(result)
            }

            pokemonDetailsViewModel.getEvolutionChain(
                "https://pokeapi.co/api/v2/pokemon-species/1/",
                "https://pokeapi.co/api/v2/evolution-chain/1/"
            )

            verify(isLoadingObserver).onChanged(false)

        }


}