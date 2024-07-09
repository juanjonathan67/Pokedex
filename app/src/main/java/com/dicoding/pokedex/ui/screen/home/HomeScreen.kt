package com.dicoding.pokedex.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dicoding.pokedex.data.model.PokemonGrid
import com.dicoding.pokedex.ui.components.GridItem
import com.dicoding.pokedex.ui.components.Search
import com.dicoding.pokedex.ui.screen.loading.LoadingScreen
import com.dicoding.pokedex.ui.screen.squad.SquadViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    squadViewModel: SquadViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit,
) {
    val lazyPokemonItems = homeViewModel.pokemonPagingFlow.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    Column (
        modifier = modifier
            .fillMaxSize()
    ) {
        // Needs to be here, otherwise it will disappear everytime the search query changes if put in HomeContent
        Search(
            searchQuery = searchQuery,
            onSearchQueryChange = {
                searchQuery = it
                homeViewModel.setSearchQuery(it)
            },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        when (lazyPokemonItems.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingScreen()
            }
            is LoadState.Error -> {
                lazyPokemonItems.loadState.refresh as LoadState.Error
            }
            else -> {
                if (lazyPokemonItems.itemSnapshotList.size == 0) {
                    LoadingScreen()
                }
                HomeContent(
                    pokemons = lazyPokemonItems,
                    pokemonCount = {
                        runBlocking {
                            squadViewModel.getPokemonCount().first()
                        }
                    },
                    navigateToDetail = navigateToDetail,
                    favoritePokemon = { pokemonId ->
                        scope.launch {
                            squadViewModel.toggleFavoriteStatus(pokemonId)
                        }
                    },
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    pokemons: LazyPagingItems<PokemonGrid>,
    pokemonCount: () -> Int,
    navigateToDetail: (Long) -> Unit,
    favoritePokemon: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 156.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(
            count = pokemons.itemCount,
            key = { index -> pokemons[index]?.id ?: index },
        ) { index ->
            val item = pokemons[index] ?: return@items

            GridItem(
                id = item.id,
                name = item.name,
                bgColor = item.bgColor,
                imageUrl = item.imageUrl,
                favorite = item.favorite,
                pokemonCount = pokemonCount,
                navigateToDetail = navigateToDetail,
                favoritePokemon = favoritePokemon,
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
            )

        }
    }
}
