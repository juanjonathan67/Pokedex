package com.dicoding.pokedex.data.model

data class PokemonSquad (
    val id: Long,
    val name: String,
    val favorite: Boolean,
    val imageUrl: String,
    val types: List<String>,
    val stats: PokemonStats,
)