package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.pokedex.data.model.PokemonMoves
import com.dicoding.pokedex.ui.theme.BorderColor
import com.dicoding.pokedex.ui.theme.BorderHeaderColor
import com.dicoding.pokedex.ui.theme.PokedexTheme
import com.dicoding.pokedex.utils.typeToIcon

@Composable
fun MovesTable(
    moves: List<PokemonMoves>,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
    ) {
        MovesTableHeader()
        for (move in moves) {
            key (move.name) {
                MovesRow(
                    level = move.level,
                    name = move.name,
                    type = move.type,
                    power = move.power
                )
            }
        }
    }
}

@Composable
fun MovesTableHeader(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(color = BorderHeaderColor)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Text(
                text = "Lv.",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .weight(2F)
                .fillMaxHeight()
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Text(
                text = "Move",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(0.75F)
                .fillMaxHeight()
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Text(
                text = "Type",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(0.75F)
                .fillMaxHeight()
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Text(
                text = "Power",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun MovesRow(
    modifier: Modifier = Modifier,
    level: String,
    name: String,
    type: String,
    power: String? = null,
) {
    Row (
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Text(
                text = level,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .weight(2F)
                .fillMaxHeight()
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(0.75F)
                .fillMaxHeight()
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Image(
                painter = painterResource(id = typeToIcon(type)),
                contentDescription = "move_type",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .weight(0.75F)
                .fillMaxHeight()
                .border(BorderStroke(1.dp, BorderColor))
        ) {
            Text(
                text = power ?: "-",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovesTablePreview() {
    PokedexTheme {
        MovesTable(
            moves = listOf(
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
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovesTableHeaderPreview() {
    PokedexTheme {
        MovesTableHeader()
    }
}

@Preview(showBackground = true)
@Composable
fun MovesRowPreview() {
    PokedexTheme {
        MovesRow(
            level = "1",
            name = "Growl",
            type = "normal",
        )
    }
}