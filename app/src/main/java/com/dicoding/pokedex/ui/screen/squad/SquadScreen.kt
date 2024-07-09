package com.dicoding.pokedex.ui.screen.squad

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dicoding.pokedex.R
import com.dicoding.pokedex.data.model.PokemonSquad
import com.dicoding.pokedex.data.model.PokemonStats
import com.dicoding.pokedex.ui.components.SquadMember
import com.dicoding.pokedex.ui.components.StatsTable
import com.dicoding.pokedex.ui.components.SubTitle
import com.dicoding.pokedex.ui.components.TopBar
import com.dicoding.pokedex.ui.screen.loading.LoadingScreen
import com.dicoding.pokedex.ui.theme.PokedexTheme
import com.dicoding.pokedex.utils.UiState
import com.dicoding.pokedex.utils.typeAdvantages
import com.dicoding.pokedex.utils.typeDisadvantages
import com.dicoding.pokedex.utils.typeToIcon

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SquadScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    squadViewModel: SquadViewModel = hiltViewModel(),
) {
    val tabItems = listOf(
        TabItem(
            title = "Team",
            unselectedItem = R.drawable.ic_sword_white,
            selectedItem = R.drawable.ic_sword_black,
        ),
        TabItem(
            title = "Info",
            unselectedItem = R.drawable.ic_info_white,
            selectedItem = R.drawable.ic_info_black,
        )
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect (selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect (pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress)
            selectedTabIndex = pagerState.currentPage
    }

    squadViewModel.pokemonSquad.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Error -> {}
            UiState.Loading -> {
                squadViewModel.getAllPokemon()
                LoadingScreen()
            }
            is UiState.Success -> {
                SquadScreenStateless(
                    pokemonSquad = uiState.data,
                    selectedTabIndex = selectedTabIndex,
                    onSelectedTabIndexChange = {
                        selectedTabIndex = it
                    },
                    tabItems = tabItems,
                    pagerState = pagerState,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier,
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SquadScreenStateless(
    pokemonSquad: List<PokemonSquad>,
    selectedTabIndex: Int,
    onSelectedTabIndexChange: (Int) -> Unit,
    tabItems: List<TabItem>,
    pagerState: PagerState,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabItems.forEachIndexed { index, tabItem ->  
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { onSelectedTabIndexChange(index) },
                    text = {
                        Text(
                            text = tabItem.title,
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(
                                id =
                                if (index == selectedTabIndex)
                                    tabItem.selectedItem
                                else
                                    tabItem.unselectedItem),
                            contentDescription = tabItem.title
                        )
                    },
                    unselectedContentColor = Color.White,
                    selectedContentColor = Color.Black,
                    modifier = Modifier
                        .background(color = Color.Red)
                )
            }
        }
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { index: Int ->
            if (index == 0) {
                SquadMember(
                    pokemonSquad = pokemonSquad,
                    navigateToDetail = navigateToDetail,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxSize()
                )
            } else {
                Column (
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    SubTitle(subTitle = "Base Stats")

                    StatsTable(
                        stats = averageStats(pokemonSquad),
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        SubTitle(subTitle = "Strong Against")
                        Spacer(modifier = Modifier.weight(1F))
                        SubTitle(subTitle = "Weak Against")
                    }

                    val totalTypeAdvantage = totalTypeAdvantage(pokemonSquad)
                    // Extract types with positive and negative advantages
                    val positiveTypes = totalTypeAdvantage.filter { it.values.first() > 0 }.map { it.keys.first() }
                    val negativeTypes = totalTypeAdvantage.filter { it.values.first() < 0 }.map { it.keys.first() }
                    Log.d("TYPES", positiveTypes.toString())
                    Log.d("TYPES", negativeTypes.toString())

                    Row (
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        LazyVerticalGrid(
                            contentPadding = PaddingValues(start = 0.dp),
                            columns = GridCells.FixedSize(44.dp),
                            modifier = Modifier
                                .width(140.dp)
                                .fillMaxHeight()
                        ) {
                            items(positiveTypes) { type ->
                                Image(
                                    painter = painterResource(id = typeToIcon(type)),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                        .padding(2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1F))

                        LazyVerticalGrid(
                            horizontalArrangement = Arrangement.End,
                            columns = GridCells.FixedSize(44.dp),
                            modifier = Modifier
                                .width(140.dp)
                                .fillMaxHeight()
                        ) {
                            items(negativeTypes) { type ->
                                Image(
                                    painter = painterResource(id = typeToIcon(type)),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                        .padding(2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun averageStats(pokemonSquad: List<PokemonSquad>): PokemonStats {
    var totalHp = 0
    var totalAttack = 0
    var totalDefense = 0
    var totalSpAtt = 0
    var totalSpDef = 0
    var totalSpeed = 0

    pokemonSquad.forEach { pokemon ->
        totalHp += pokemon.stats.hp.toInt()
        totalAttack += pokemon.stats.attack.toInt()
        totalDefense += pokemon.stats.defense.toInt()
        totalSpAtt += pokemon.stats.spAtt.toInt()
        totalSpDef += pokemon.stats.spDef.toInt()
        totalSpeed += pokemon.stats.speed.toInt()
    }

    val count = pokemonSquad.size
    val averageHp = (totalHp / count).toString()
    val averageAttack = (totalAttack / count).toString()
    val averageDefense = (totalDefense / count).toString()
    val averageSpAtt = (totalSpAtt / count).toString()
    val averageSpDef = (totalSpDef / count).toString()
    val averageSpeed = (totalSpeed / count).toString()

    return PokemonStats(
        hp = averageHp,
        attack = averageAttack,
        defense = averageDefense,
        spAtt = averageSpAtt,
        spDef = averageSpDef,
        speed = averageSpeed
    )
}

private fun totalTypeAdvantage(pokemonSquad: List<PokemonSquad>): List<Map<String, Int>> {
    val typeAdvantageMap = mutableMapOf<String, Int>()

    pokemonSquad.forEach { pokemon ->
        val types = pokemon.types

        types.forEach { type ->
            val advantageMap = typeAdvantages[type]
            val disadvantageMap = typeDisadvantages[type]

            advantageMap?.keys?.forEach {
                typeAdvantageMap[it] = (typeAdvantageMap[it] ?: 0) + 1
            }

            disadvantageMap?.keys?.forEach {
                typeAdvantageMap[it] = (typeAdvantageMap[it] ?: 0) - 1
            }
        }
    }

    return typeAdvantageMap.toList().map { mapOf(it.first to it.second) }
}



@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun SquadScreenStatelessPreview() {
    val tabItems = listOf(
        TabItem(
            title = "Team",
            unselectedItem = R.drawable.ic_sword_white,
            selectedItem = R.drawable.ic_sword_black,
        ),
        TabItem(
            title = "Info",
            unselectedItem = R.drawable.ic_info_white,
            selectedItem = R.drawable.ic_info_black,
        )
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }


    PokedexTheme {
        Column {
            TopBar(navController = rememberNavController(), title = "TEST")
            SquadScreenStateless(
                pokemonSquad = listOf(
                    PokemonSquad(
                        id = 1,
                        name = "Bulbasaur Galarian",
                        favorite = true,
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        types = listOf("grass", "poison"),
                        stats = PokemonStats (
                            hp = "45",
                            attack = "49",
                            defense = "49",
                            spAtt = "65",
                            spDef = "65",
                            speed = "45",
                        ),
                    ),
                    PokemonSquad(
                        id = 1,
                        name = "Bulbasaur Galarian",
                        favorite = true,
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        types = listOf("grass", "poison"),
                        stats = PokemonStats (
                            hp = "45",
                            attack = "49",
                            defense = "49",
                            spAtt = "65",
                            spDef = "65",
                            speed = "45",
                        ),
                    ),
                    PokemonSquad(
                        id = 1,
                        name = "Bulbasaur Galarian",
                        favorite = true,
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        types = listOf("grass", "poison"),
                        stats = PokemonStats (
                            hp = "45",
                            attack = "49",
                            defense = "49",
                            spAtt = "65",
                            spDef = "65",
                            speed = "45",
                        ),
                    ),
                    PokemonSquad(
                        id = 1,
                        name = "Bulbasaur Galarian",
                        favorite = true,
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        types = listOf("grass", "poison"),
                        stats = PokemonStats (
                            hp = "45",
                            attack = "49",
                            defense = "49",
                            spAtt = "65",
                            spDef = "65",
                            speed = "45",
                        ),
                    ),
                    PokemonSquad(
                        id = 1,
                        name = "Bulbasaur Galarian",
                        favorite = true,
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        types = listOf("grass", "poison"),
                        stats = PokemonStats (
                            hp = "45",
                            attack = "49",
                            defense = "49",
                            spAtt = "65",
                            spDef = "65",
                            speed = "45",
                        ),
                    ),
                ),
                selectedTabIndex = selectedTabIndex,
                onSelectedTabIndexChange = {
                    selectedTabIndex = it
                },
                tabItems = tabItems,
                pagerState = rememberPagerState {
                    tabItems.size
                },
                navigateToDetail = {}
            )
        }

    }
}