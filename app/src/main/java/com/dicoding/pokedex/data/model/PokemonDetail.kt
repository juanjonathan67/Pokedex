package com.dicoding.pokedex.data.model

data class PokemonDetail (
    val id: Long,
    val name: String,
    val favorite: Boolean,
    val imageUrl: String,
    val genus: String,
    val types: List<String>,
    val description: String,
    val abilities: PokemonAbilities,
    val stats: PokemonStats,
    val evoChart: List<PokemonEvoChart>,
    val moves: List<PokemonMoves>,
)