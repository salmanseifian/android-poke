package com.salmanseifian.androidpoke.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.map
import com.salmanseifian.androidpoke.CoroutineTestRule
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.presentation.mapper.PokemonSpeciesRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpeciesViewModelTest {

    private lateinit var cut: SpeciesViewModel

    @get:Rule
    val rule = CoroutineTestRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var pokeRepository: PokeRepository

    @Mock
    private lateinit var pokemonSpeciesRepositoryToUiModelMapper: PokemonSpeciesRepositoryToUiModelMapper

    @Mock
    private lateinit var allSpeciesObserver: Observer<PagingData<SpeciesUiModel>>

    private val species = listOf(
        SpeciesRepositoryModel("bulbasaur", "https://pokeapi.co/api/v2/pokemon-species/1/"),
        SpeciesRepositoryModel("ivysaur", "https://pokeapi.co/api/v2/pokemon-species/2/"),
        SpeciesRepositoryModel("venusaur", "https://pokeapi.co/api/v2/pokemon-species/3/\""),
    )

    private val speciesUiModel = listOf(
        SpeciesUiModel("bulbasaur", "https://pokeapi.co/api/v2/pokemon-species/1/"),
        SpeciesUiModel("ivysaur", "https://pokeapi.co/api/v2/pokemon-species/2/"),
        SpeciesUiModel("venusaur", "https://pokeapi.co/api/v2/pokemon-species/3/\""),
    )


    @Before
    fun setUp() {
        cut = SpeciesViewModel(pokeRepository, pokemonSpeciesRepositoryToUiModelMapper).apply {
            allSpecies.observeForever(allSpeciesObserver)
        }
    }

    @Test
    fun `When launches then launchUiModelRetrieved invoked with expected result`() {
        rule.dispatcher.runBlockingTest {

            val expected = PagingData.from(species)
            val expectedToUi = expected.map {
                SpeciesUiModel(it.name, it.url)
            }

            doReturn(flowOf(expected))
                .`when`(pokeRepository)
                .getAllPokemonSpecies()

            verify(pokeRepository).getAllPokemonSpecies()
            verify(allSpeciesObserver).onChanged(expectedToUi)
            cut.allSpecies.removeObserver(allSpeciesObserver)

        }
    }

}