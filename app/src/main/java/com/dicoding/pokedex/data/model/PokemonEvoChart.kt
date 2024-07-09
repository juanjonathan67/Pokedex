package com.dicoding.pokedex.data.model

data class PokemonEvoChart (
    val id: Long,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val evoBy: String? = null,
)