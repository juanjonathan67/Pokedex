package com.dicoding.pokedex.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.pokedex.data.local.enitity.PokemonSquadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonSquadDao {

    @Query("SELECT * FROM squad")
    fun getAll(): Flow<List<PokemonSquadEntity>>

    @Query("SELECT * FROM squad WHERE id = :id")
    fun findById(id: Long): Flow<PokemonSquadEntity>

    @Query("UPDATE squad SET favorite = :favorite WHERE id = :id")
    suspend fun updateFavoriteById(id: Long, favorite: Boolean): Int

    @Insert
    suspend fun insert(vararg pokemonSquadEntity: PokemonSquadEntity): List<Long>

    @Delete
    suspend fun delete(pokemonSquadEntity: PokemonSquadEntity): Int

    @Query("SELECT count(*) FROM squad")
    suspend fun count(): Int
}