package com.dicoding.pokedex.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.pokedex.R
import com.dicoding.pokedex.ui.theme.Grass
import com.dicoding.pokedex.ui.theme.PokedexTheme

@Composable
fun GridItem(
    id: String,
    name: String,
    bgColor: Color,
    imageUrl: String,
    favorite: Boolean,
    pokemonCount: () -> Int,
    navigateToDetail: (Long) -> Unit,
    favoritePokemon: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var favoriteState by remember { mutableStateOf(favorite) }
    val context = LocalContext.current

    GridItemStateless(
        id = id,
        name = name,
        bgColor = bgColor,
        imageUrl = imageUrl,
        favoriteState = favoriteState,
        navigateToDetail = navigateToDetail,
        favoritePokemon = { pokemonId ->
            if (pokemonCount() < 6 || favoriteState) {
                favoriteState = !favoriteState
                favoritePokemon(pokemonId)
            } else {
                Toast.makeText(context, "You already have 6 pokemons", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = modifier
    )
}

@Composable
fun GridItemStateless(
    id: String,
    name: String,
    bgColor: Color,
    imageUrl: String,
    favoriteState: Boolean,
    navigateToDetail: (Long) -> Unit,
    favoritePokemon: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(156.dp)
            .background(color = bgColor, shape = RoundedCornerShape(16.dp))
            .clickable { navigateToDetail(id.toLong()) }
    ) {
        IconButton(
            onClick = { favoritePokemon(id.toLong()) },
        ) {
            Image(
                painter = if (favoriteState) {
                    painterResource(id = R.drawable.ic_filled_heart)
                } else {
                    painterResource(id = R.drawable.ic_hollow_heart)
                },
                contentDescription = "favorite_button",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 5.dp, top = 5.03.dp)
            )
        }

        Text(
            text = "#$id",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 6.dp, end = 6.dp)
        )

        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = "pokemon_image",
            modifier = Modifier
                .align(Alignment.Center)
                .size(90.dp)
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp, start = 4.dp, end = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GridItemPreview() {
    PokedexTheme {
        GridItem(
            id = "1",
            name = "Darmanitan Standard",
            bgColor = Grass,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            favorite = false,
            pokemonCount = {
                5
            },
            navigateToDetail = {

            },
            favoritePokemon = {

            }
        )
    }
}