package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dicoding.pokedex.R
import com.dicoding.pokedex.ui.navigation.Screen
import com.dicoding.pokedex.ui.theme.PokedexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pokeball),
                    contentDescription = "Return to home page",
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(Screen.Squad.route)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_filled_white),
                    contentDescription = "favorites_page",
                    modifier = Modifier
                        .height(30.dp)
                )
            }
            IconButton(
                onClick = {
                    navController.navigate(Screen.About.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "about_page",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Red,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
        ),
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun TopBarPreview() {
    PokedexTheme {
        TopBar(navController = rememberNavController(), title = "Pokédex")
    }
}