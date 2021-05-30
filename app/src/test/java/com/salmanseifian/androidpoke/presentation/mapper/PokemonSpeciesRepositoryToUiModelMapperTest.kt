package com.salmanseifian.androidpoke.presentation.mapper

import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PokemonSpeciesRepositoryToUiModelMapperTest {

    private lateinit var cut: PokemonSpeciesRepositoryToUiModelMapper

    private val givenSpecies = SpeciesRepositoryModel(
        "bulbasaur",
        "https://pokeapi.co/api/v2/pokemon-species/1/"
    )

    private val expected = SpeciesUiModel(
        "bulbasaur",
        "https://pokeapi.co/api/v2/pokemon-species/1/"
    )


    @Before
    fun setUp() {
        cut = PokemonSpeciesRepositoryToUiModelMapperImp()
    }


    @Test
    fun `Given pokemonSpeciesRepositoryModel when toUiModel then returns expected result`() {
        val actualValue = cut.toUiModel(givenSpecies)

        // Then
        assertEquals(expected, actualValue)
    }

}