package com.dicoding.pokedex.data.model

import androidx.compose.ui.graphics.Color

data class PokemonGrid(
    val id: String,
    val name: String,
    val bgColor: Color,
    val imageUrl: String,
    val favorite: Boolean,
)
