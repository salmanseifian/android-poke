package com.salmanseifian.androidpoke.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.salmanseifian.androidpoke.TestCoroutineRule
import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonRepositoryModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.data.repository.Resource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonDetailsViewModelTest {

    private lateinit var cut: PokemonDetailsViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var pokeRepository: PokeRepository

    private val coroutineDispatcher = TestCoroutineDispatcher()


    private val evolutionChainRepositoryModel = EvolutionChainRepositoryModel(
        PokemonRepositoryModel(
            "bulbasaur",
            "https://pokeapi.co/api/v2/pokemon-species/1/"
        )
    )


    @Before
    fun setUp() {
        cut = PokemonDetailsViewModel(pokeRepository)
    }

    @ExperimentalTime
    @Test
    fun `When getEvolutionChain then getEvolutionChain invoked with success result`() {
        coroutineDispatcher.runBlockingTest {
            doReturn(Resource.Success(evolutionChainRepositoryModel))
                .`when`(pokeRepository)
                .getEvolutionChain(1)


            val firstItem =
                cut.getEvolutionChain("https://pokeapi.co/api/v2/evolution-chain/1/").first()

            assertEquals(firstItem, Resource.Loading)

            val secondItem =
                cut.getEvolutionChain("https://pokeapi.co/api/v2/evolution-chain/1/").drop(1)
                    .first()

            assertEquals(secondItem, Resource.Success(evolutionChainRepositoryModel))

//            cut.getEvolutionChain("https://pokeapi.co/api/v2/evolution-chain/1/").test {
//                assertEquals(Resource.Loading, expectItem())
//                assertEquals(Resource.Success(evolutionChainRepositoryModel), expectItem())
//                expectComplete()
//            }

        }
    }


    @Test
    fun `When getEvolutionChain then getEvolutionChain invoked with failure result`() {
        coroutineDispatcher.runBlockingTest {
            doReturn(Resource.Failure(true, 400, null))
                .`when`(pokeRepository)
                .getEvolutionChain(1)


            val firstItem =
                cut.getEvolutionChain("https://pokeapi.co/api/v2/evolution-chain/1/").first()

            assertEquals(firstItem, Resource.Loading)

            val secondItem =
                cut.getEvolutionChain("https://pokeapi.co/api/v2/evolution-chain/1/").drop(1)
                    .first()

            assertEquals(secondItem, Resource.Failure(true, 400, null))

        }
    }

}