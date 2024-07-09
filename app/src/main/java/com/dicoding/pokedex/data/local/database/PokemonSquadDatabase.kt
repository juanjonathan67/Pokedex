package com.dicoding.pokedex.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.pokedex.data.local.dao.PokemonSquadDao
import com.dicoding.pokedex.data.local.enitity.PokemonSquadEntity

@Database(entities = [PokemonSquadEntity::class], version = 1)
abstract class PokemonSquadDatabase : RoomDatabase() {
    abstract fun pokemonSquadDao(): PokemonSquadDao
}