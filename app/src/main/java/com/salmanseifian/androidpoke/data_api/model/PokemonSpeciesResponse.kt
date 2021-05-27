package com.salmanseifian.androidpoke.data.model

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val pokemonSpecies: List<PokemonSpecies>
)

data class PokemonSpecies(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)