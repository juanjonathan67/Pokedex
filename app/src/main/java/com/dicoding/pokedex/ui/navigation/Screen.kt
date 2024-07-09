package com.dicoding.pokedex.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("home/{pokemonId}") {
        fun createRoute(pokemonId: Long) = "home/$pokemonId"
    }
    object Squad : Screen("squad")
    object About : Screen("about")
    object Loading : Screen("loading")
}