package com.dicoding.pokedex.domain.repository

import com.dicoding.pokedex.data.model.PokemonSquad

interface SquadRepository {
    suspend fun getAllPokemon(): List<PokemonSquad>

    suspend fun findPokemonById(id: Long): PokemonSquad

    suspend fun insertPokemon(pokemonSquad: List<PokemonSquad>): Int

    suspend fun deletePokemon(id: Long): Int

    suspend fun toggleFavoritePokemon(id: Long): Boolean

    suspend fun isFavorite(id: Long): Boolean

    suspend fun getPokemonCount(): Int
}