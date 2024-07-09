package com.dicoding.pokedex.data.repository

import androidx.paging.PagingSource
import com.dicoding.pokedex.data.paging.PokemonPagingSource
import com.dicoding.pokedex.data.remote.response.PokemonDetailResponse
import com.dicoding.pokedex.data.remote.response.PokemonEvoChainResponse
import com.dicoding.pokedex.data.remote.response.PokemonListItem
import com.dicoding.pokedex.data.remote.response.PokemonMoveResponse
import com.dicoding.pokedex.data.remote.response.PokemonSpeciesResponse
import com.dicoding.pokedex.data.remote.service.PokedexApiService
import com.dicoding.pokedex.domain.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokedexRespositoyImpl @Inject constructor(
    private val pokedexApiService: PokedexApiService,
): PokedexRepository {

    override fun getPokemonPagingSource(searchQuery: String): PagingSource<Int, PokemonListItem> {
        return PokemonPagingSource(pokedexApiService, searchQuery)
    }

    override suspend fun getPokemonList(page: Int): Flow<List<PokemonListItem?>> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonListResponse = pokedexApiService.getPokemonList(offset = page * 20)
                val pokemonList = pokemonListResponse.results ?: emptyList()
                return@withContext flowOf(pokemonList)
            } catch (e: Exception) {
                throw Throwable(message = e.message)
            }
        }
    }


    override suspend fun getPokemonDetail(id: Long): Flow<PokemonDetailResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonDetailResponse = pokedexApiService.getPokemonDetail(id = id)
                return@withContext flowOf(pokemonDetailResponse)
            } catch (e: Exception) {
                throw Throwable(message = e.message)
            }
        }
    }

    override suspend fun getPokemonDetail(name: String): Flow<PokemonDetailResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonDetailResponse = pokedexApiService.getPokemonDetail(name = name)
                return@withContext flowOf(pokemonDetailResponse)
            } catch (e: Exception) {
                throw Throwable(message = e.message)
            }
        }
    }

    override suspend fun getPokemonSpecies(id: Long): Flow<PokemonSpeciesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonSpeciesResponse = pokedexApiService.getPokemonSpecies(id = id)
                return@withContext flowOf(pokemonSpeciesResponse)
            } catch (e: Exception) {
                throw Throwable(message = e.message)
            }
        }
    }

    override suspend fun getPokemonSpecies(name: String): Flow<PokemonSpeciesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonSpeciesResponse = pokedexApiService.getPokemonSpecies(name = name)
                return@withContext flowOf(pokemonSpeciesResponse)
            } catch (e: Exception) {
                throw Throwable(message = e.message)
            }
        }
    }

    override suspend fun getPokemonEvoChain(id: Long): Flow<PokemonEvoChainResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonEvoChainResponse = pokedexApiService.getPokemonEvoChain(id = id)
                return@withContext flowOf(pokemonEvoChainResponse)
            } catch (e: Exception) {
                throw Throwable(message = e.message)
            }
        }
    }

    override suspend fun getPokemonMove(id: Long): Flow<PokemonMoveResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonMoveResponse = pokedexApiService.getPokemonMove(id = id)
                return@withContext flowOf(pokemonMoveResponse)
            } catch (e: Exception) {
                throw Throwable(message = e.message)
            }
        }
    }
}