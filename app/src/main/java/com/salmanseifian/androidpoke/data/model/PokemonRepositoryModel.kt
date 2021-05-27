package com.salmanseifian.androidpoke.data.model

data class PokemonSpeciesRepositoryModel(
    val pokemons: List<PokemonRepositoryModel>
)

data class PokemonRepositoryModel(
    val name: String,
    val url: String
)
