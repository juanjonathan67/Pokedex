package com.dicoding.pokedex.domain.repository

import androidx.paging.PagingSource
import com.dicoding.pokedex.data.remote.response.PokemonDetailResponse
import com.dicoding.pokedex.data.remote.response.PokemonEvoChainResponse
import com.dicoding.pokedex.data.remote.response.PokemonListItem
import com.dicoding.pokedex.data.remote.response.PokemonMoveResponse
import com.dicoding.pokedex.data.remote.response.PokemonSpeciesResponse
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    fun getPokemonPagingSource(searchQuery: String): PagingSource<Int, PokemonListItem>

    suspend fun getPokemonList(
        page: Int,
    ): Flow<List<PokemonListItem?>>

    suspend fun getPokemonDetail(
        id: Long,
    ): Flow<PokemonDetailResponse>

    suspend fun getPokemonDetail(
        name: String,
    ): Flow<PokemonDetailResponse>

    suspend fun getPokemonSpecies(
        id: Long,
    ): Flow<PokemonSpeciesResponse>

    suspend fun getPokemonSpecies(
        name: String,
    ): Flow<PokemonSpeciesResponse>

    suspend fun getPokemonEvoChain(
        id: Long,
    ): Flow<PokemonEvoChainResponse>

    suspend fun getPokemonMove(
        id: Long,
    ): Flow<PokemonMoveResponse>
}