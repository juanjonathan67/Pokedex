package com.dicoding.pokedex

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.pokedex.ui.components.TopBar
import com.dicoding.pokedex.ui.navigation.Screen
import com.dicoding.pokedex.ui.screen.about.AboutScreen
import com.dicoding.pokedex.ui.screen.detail.DetailScreen
import com.dicoding.pokedex.ui.screen.home.HomeScreen
import com.dicoding.pokedex.ui.screen.loading.LoadingScreen
import com.dicoding.pokedex.ui.screen.squad.SquadScreen
import com.dicoding.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokedexApp : Application()

@Composable
fun PokedexAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var title by remember {
        mutableStateOf("Pokédex")
    }

    Scaffold(
        topBar = {
            if (currentRoute != Screen.Detail.route) {
                TopBar(navController = navController, title = title)
            }
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                title = "Pokédex"
                HomeScreen(
                    navigateToDetail = { pokemonId ->
                        navController.navigate(Screen.Detail.createRoute(pokemonId))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("pokemonId") {type = NavType.LongType})
            ) {
                val id = it.arguments?.getLong("pokemonId") ?: 1L

                DetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToEvo = { pokemonId ->
                        navController.navigate(Screen.Detail.createRoute(pokemonId))
                    }
                )
            }
            composable(Screen.About.route) {
                title = "About Me"
                AboutScreen()
            }
            composable(Screen.Squad.route) {
                title = "My Squad"
                SquadScreen(
                    navigateToDetail = { pokemonId ->
                        navController.navigate(Screen.Detail.createRoute(pokemonId))
                    }
                )
            }
            composable(Screen.Loading.route) {
                LoadingScreen()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PokedexAppPreview() {
    PokedexTheme {
        PokedexAppContent()
    }
}