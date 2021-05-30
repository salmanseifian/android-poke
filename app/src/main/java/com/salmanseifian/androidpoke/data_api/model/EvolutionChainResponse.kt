package com.salmanseifian.androidpoke.data_api.model


import com.google.gson.annotations.SerializedName

data class EvolutionChainResponse(
    @SerializedName("baby_trigger_item")
    val babyTriggerItem: Any?,
    @SerializedName("chain")
    val chain: EvolvesTo?,
    @SerializedName("id")
    val id: Int?
)

data class Chain(
    @SerializedName("evolution_details")
    val evolutionDetails: List<EvolutionDetail>?,
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>?,
    @SerializedName("is_baby")
    val isBaby: Boolean?,
    @SerializedName("species")
    val species: Species?
)

data class EvolvesTo(
    @SerializedName("evolution_details")
    val evolutionDetails: List<EvolutionDetail>?,
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>?,
    @SerializedName("is_baby")
    val isBaby: Boolean?,
    @SerializedName("species")
    val species: Species?
)


data class Species(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class EvolutionDetail(
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("held_item")
    val heldItem: Any?,
    @SerializedName("item")
    val item: Any?,
    @SerializedName("known_move")
    val knownMove: Any?,
    @SerializedName("known_move_type")
    val knownMoveType: Any?,
    @SerializedName("location")
    val location: Any?,
    @SerializedName("min_affection")
    val minAffection: Any?,
    @SerializedName("min_beauty")
    val minBeauty: Any?,
    @SerializedName("min_happiness")
    val minHappiness: Any?,
    @SerializedName("min_level")
    val minLevel: Int?,
    @SerializedName("needs_overworld_rain")
    val needsOverworldRain: Boolean?,
    @SerializedName("party_species")
    val partySpecies: Any?,
    @SerializedName("party_type")
    val partyType: Any?,
    @SerializedName("relative_physical_stats")
    val relativePhysicalStats: Any?,
    @SerializedName("time_of_day")
    val timeOfDay: String?,
    @SerializedName("trade_species")
    val tradeSpecies: Any?,
    @SerializedName("trigger")
    val trigger: Trigger?,
    @SerializedName("turn_upside_down")
    val turnUpsideDown: Boolean?
)

data class Trigger(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)