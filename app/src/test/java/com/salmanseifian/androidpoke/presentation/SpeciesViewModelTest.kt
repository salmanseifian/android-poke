package com.salmanseifian.androidpoke.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.map
import com.salmanseifian.androidpoke.TestCoroutineRule
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.presentation.mapper.PokemonSpeciesRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpeciesViewModelTest {

    private lateinit var cut: SpeciesViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


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

    @Test
    fun `When launches then launchUiModelRetrieved invoked with expected result`() {
        runBlockingTest {

            val expected = PagingData.from(species)
            val expectedToUi = expected.map {
                SpeciesUiModel(it.name, it.url)
            }

            doReturn(flowOf(expected))
                .`when`(pokeRepository)
                .getAllPokemonSpecies()

            val cut = SpeciesViewModel(pokeRepository, pokemonSpeciesRepositoryToUiModelMapper)

            cut.allSpecies.observeForever(allSpeciesObserver)
            verify(pokeRepository).getAllPokemonSpecies()
            cut.allSpecies.removeObserver(allSpeciesObserver)

            whenever(pokemonSpeciesRepositoryToUiModelMapper.toUiModel(species[0])).thenReturn(
                speciesUiModel[0]
            )

        }
    }

}