package com.dicoding.pokedex.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "squad")
data class PokemonSquadEntity (
    @PrimaryKey
    val id: Long,

    val name: String,
    val favorite: Boolean,
    val imageUrl: String,
    val type1: String,
    val type2: String,
    val hp: String,
    val attack: String,
    val defense: String,
    val spAtt: String,
    val spDef: String,
    val speed: String,
)