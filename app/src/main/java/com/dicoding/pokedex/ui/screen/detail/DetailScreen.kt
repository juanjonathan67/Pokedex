package com.dicoding.pokedex.ui.screen.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dicoding.pokedex.R
import com.dicoding.pokedex.data.model.PokemonAbilities
import com.dicoding.pokedex.data.model.PokemonEvoChart
import com.dicoding.pokedex.data.model.PokemonMoves
import com.dicoding.pokedex.data.model.PokemonStats
import com.dicoding.pokedex.ui.components.EvoChartRow
import com.dicoding.pokedex.ui.components.MovesTable
import com.dicoding.pokedex.ui.components.StatsTable
import com.dicoding.pokedex.ui.components.SubTitle
import com.dicoding.pokedex.ui.components.TypeBox
import com.dicoding.pokedex.ui.screen.loading.LoadingScreen
import com.dicoding.pokedex.ui.theme.PokedexTheme
import com.dicoding.pokedex.utils.UiState
import com.dicoding.pokedex.utils.typeToColor

@Composable
fun DetailScreen (
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel(),
    id: Long,
    navigateBack: () -> Unit,
    navigateToEvo: (Long) -> Unit
) {

    detailViewModel.pokemonDetail.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Error -> {
                Log.e("HomeViewModel", uiState.error)
            }
            UiState.Loading -> {
                LoadingScreen()
                detailViewModel.getPokemonDetail(id)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    id = data.id,
                    name = data.name,
                    imageUrl = data.imageUrl,
                    genus = data.genus,
                    types = data.types,
                    description = data.description,
                    abilities = data.abilities,
                    stats = data.stats,
                    evoChart = data.evoChart,
                    moves = data.moves,
                    navigateBack = navigateBack,
                    navigateToEvo = navigateToEvo,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    id: Long,
    name: String,
    imageUrl: String,
    genus: String,
    types: List<String>,
    description: String,
    abilities: PokemonAbilities,
    stats: PokemonStats,
    evoChart: List<PokemonEvoChart>,
    moves: List<PokemonMoves>,
    navigateBack: () -> Unit,
    navigateToEvo: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp)
                .background(
                    color = typeToColor(types[0]),
                    shape = RoundedCornerShape(
                        bottomStart = 32.dp,
                        bottomEnd = 32.dp,
                    )
                )
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navigateBack() },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "arrow_back",
                    )
                }

                Spacer(modifier = Modifier.weight(1F))

                Text(
                    text = "#$id",
                    modifier = Modifier
                        .padding(top = 12.dp, end = 12.dp)
                )
            }

            AsyncImage(
                model = imageUrl,
                placeholder = painterResource(id = R.drawable.ic_pokeball),
                contentDescription = "pokemonImage",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(320.dp)
                    .padding(top = 16.dp)
            )

            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )

            Text(
                text = genus,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Row (
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp)
            ) {
                TypeBox(
                    type = types[0],
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp)
                )

                if (types.size > 1) {
                    TypeBox(
                        type = types[1],
                        modifier = Modifier
                            .height(40.dp)
                            .width(80.dp)
                    )
                }
            }

            Text(
                text = description,
                modifier = Modifier
                    .padding(top = 20.dp)
            )

            SubTitle(subTitle = "Abilities")

            Text(
                text = abilities.ability1.capitalize(Locale.current),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 5.dp)
            )

            if (abilities.ability2 != null) {
                Text(
                    text = abilities.ability2.capitalize(Locale.current),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
            }

            Text(
                text = "${abilities.abilityHidden.capitalize(Locale.current)} (hidden)",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 5.dp)
            )

            SubTitle(subTitle = "Base Stats")

            StatsTable(
                stats = stats,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            SubTitle(subTitle = "Evolution Chart")

            EvoChartRow(
                pokemonEvoChart = evoChart,
                navigateToEvo = navigateToEvo,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            SubTitle(subTitle = "Moves Learnt By Level Up")

            MovesTable(
                moves = moves,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, heightDp = 2000)
fun DetailScreenPreview() {
    val pokemonAbilities = PokemonAbilities (
        ability1 = "overgrow",
        abilityHidden = "chlorophyll",
    )

    val pokemonStats = PokemonStats (
        hp = "45",
        attack = "49",
        defense = "49",
        spAtt = "65",
        spDef = "65",
        speed = "45",
    )

    val pokemonEvoChart = listOf(
        PokemonEvoChart(
            id = 1,
            name = "Bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            types = listOf("grass", "poison"),
            evoBy = "Level 16"
        ),
        PokemonEvoChart(
            id = 2,
            name = "Ivysaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            types = listOf("grass", "poison"),
            evoBy = "Level 32"
        ),
        PokemonEvoChart(
            id = 3,
            name = "Venusaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            types = listOf("grass", "poison"),
        ),
    )

    val pokemonMoves = listOf(
        PokemonMoves(
            level = "1",
            name = "Growl",
            type = "normal",
        ),
        PokemonMoves(
            level = "1",
            name = "Tackle",
            type = "normal",
            power = "40"
        ),
        PokemonMoves(
            level = "3",
            name = "Vine Whip",
            type = "grass",
            power = "45"
        ),
        PokemonMoves(
            level = "6",
            name = "Growth",
            type = "normal",
        ),
        PokemonMoves(
            level = "9",
            name = "Leech Seed",
            type = "grass",
        ),
        PokemonMoves(
            level = "12",
            name = "Razor Leaf",
            type = "grass",
            power = "55"
        ),
        PokemonMoves(
            level = "15",
            name = "Poison Powder",
            type = "poison",
        ),
    )

    PokedexTheme {
        DetailContent(
            id = 1,
            name = "Bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            genus = "Seed Pokémon",
            types = listOf("grass", "poison"),
            description = "A strange seed was planted on its back at birth. The plant sprouts and grows with this POKéMON.",
            abilities = pokemonAbilities,
            stats = pokemonStats,
            evoChart = pokemonEvoChart,
            moves = pokemonMoves,
            navigateBack = {

            },
            navigateToEvo = {

            },
        )
    }
}