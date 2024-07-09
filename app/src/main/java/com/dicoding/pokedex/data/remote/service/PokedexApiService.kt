package com.dicoding.pokedex.data.remote.service

import com.dicoding.pokedex.data.remote.response.PokemonDetailResponse
import com.dicoding.pokedex.data.remote.response.PokemonEvoChainResponse
import com.dicoding.pokedex.data.remote.response.PokemonListResponse
import com.dicoding.pokedex.data.remote.response.PokemonMoveResponse
import com.dicoding.pokedex.data.remote.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Long,
    ): PokemonDetailResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String,
    ): PokemonDetailResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: Long,
    ): PokemonSpeciesResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path("name") name: String,
    ): PokemonSpeciesResponse

    @GET("evolution-chain/{id}")
    suspend fun getPokemonEvoChain(
        @Path("id") id: Long,
    ): PokemonEvoChainResponse

    @GET("move/{id}")
    suspend fun getPokemonMove(
        @Path("id") id: Long,
    ): PokemonMoveResponse
}