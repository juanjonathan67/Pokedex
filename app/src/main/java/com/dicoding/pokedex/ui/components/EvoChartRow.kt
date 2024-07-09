package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.pokedex.R
import com.dicoding.pokedex.data.model.PokemonEvoChart
import com.dicoding.pokedex.ui.theme.PokedexTheme
import com.dicoding.pokedex.utils.typeToColor

@Composable
fun EvoChartRow(
    pokemonEvoChart: List<PokemonEvoChart>,
    navigateToEvo: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .horizontalScroll(rememberScrollState())
    ) {
        for (evoChart in pokemonEvoChart) {
            key (evoChart.id) {
                EvoChartItem(
                    id = evoChart.id,
                    name = evoChart.name,
                    imageUrl = evoChart.imageUrl,
                    types = evoChart.types,
                    navigateToEvo = navigateToEvo,
                )

                if (evoChart.evoBy != null) {
                    EvoChartConnector(
                        evoBy = evoChart.evoBy,
                        modifier = Modifier
                            .padding(
                                top = 52.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                    )
                }
            }
        }

    }
}

@Composable
fun EvoChartItem(
    id: Long,
    name: String,
    imageUrl: String,
    types: List<String>,
    navigateToEvo: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .clickable { navigateToEvo(id) }
    ) {
        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = "pokemon_image",
            modifier = Modifier
                .size(106.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
        )

        Row (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = types[0].capitalize(LocaleList()),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                color = typeToColor(types[0]),
            )

            if (types.size > 1) {
                Text(
                    text = types[1].capitalize(LocaleList()),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = typeToColor(types[1]),
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun EvoChartConnector(
    evoBy: String,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = "evo_by",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "($evoBy)",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EvoChartRowPreview() {
    PokedexTheme {
        EvoChartRow(
            pokemonEvoChart = listOf(
                PokemonEvoChart(
                    id = 1,
                    name = "Bulbasaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                    types = listOf("grass", "poison"),
                    evoBy = "Level 16",
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
            ),
            navigateToEvo = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EvoChartItemPreview() {
    PokedexTheme {
        EvoChartItem(
            id = 1,
            name = "Bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            types = listOf("grass", "poison"),
            navigateToEvo = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EvoChartConnectorPreview() {
    PokedexTheme {
        EvoChartConnector(evoBy = "Level 16")
    }
}