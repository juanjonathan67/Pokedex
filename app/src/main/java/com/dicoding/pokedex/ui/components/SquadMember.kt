package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.pokedex.R
import com.dicoding.pokedex.data.model.PokemonSquad
import com.dicoding.pokedex.data.model.PokemonStats
import com.dicoding.pokedex.ui.theme.PokedexTheme
import com.dicoding.pokedex.utils.conditional
import com.dicoding.pokedex.utils.mirrorX
import com.dicoding.pokedex.utils.typeToColor


@Composable
fun SquadMember(
    pokemonSquad: List<PokemonSquad>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(((-30).dp)),
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (i in pokemonSquad.indices) {
            SquadCard(
                pokemonSquad = pokemonSquad[i],
                left = i % 2 == 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .clickable {
                        navigateToDetail(pokemonSquad[i].id)
                    }
            )
        }

        if (pokemonSquad.size < 6) {
            for (j in pokemonSquad.size ..5) {
                EmptySquadCard(
                    left = j % 2 == 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun SquadCard(
    modifier: Modifier = Modifier,
    pokemonSquad: PokemonSquad,
    left: Boolean = true,
) {
    Box(
        modifier = modifier
            .width(225.dp)
            .height(115.dp),
    ) {
        TrapezoidCard(
            width = 225.dp,
            height = 50.dp,
            leftBase = 0.dp,
            rightBase = 175.dp,
            color = typeToColor(pokemonSquad.types[0]),
            modifier = Modifier
                .width(225.dp)
                .height(50.dp)
                .align(if (left) Alignment.BottomStart else Alignment.BottomEnd)
                .conditional(!left) {
                    mirrorX()
                }
        )

        AsyncImage(
            model = pokemonSquad.imageUrl,
            placeholder = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = "pokemon_image",
            modifier = Modifier
                .size(115.dp)
                .align(if (left) Alignment.BottomStart else Alignment.BottomEnd)
                .conditional(!left) {
                    mirrorX()
                }
        )

        Text(
            text = pokemonSquad.name,
            style = MaterialTheme.typography.titleSmall,
            textAlign = if(left) TextAlign.Left else TextAlign.Right,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 65.dp)
                .conditional(left) {
                    padding(start = 115.dp)
                }
                .conditional(!left) {
                    padding(start = 165.dp)
                }
                .width(110.dp)
        )
    }
}

@Composable
fun EmptySquadCard(
    modifier: Modifier = Modifier,
    left: Boolean = true,
) {
    Box(
        modifier = modifier
            .width(225.dp)
            .height(115.dp),
    ) {
        TrapezoidCard(
            width = 225.dp,
            height = 50.dp,
            leftBase = 0.dp,
            rightBase = 175.dp,
            color = Color.Black,
            modifier = Modifier
                .width(225.dp)
                .height(50.dp)
                .align(if (left) Alignment.BottomStart else Alignment.BottomEnd)
                .conditional(!left) {
                    mirrorX()
                }
        )

        Text(
            text = "???",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 36.sp,
            color = Color.White,
            modifier = Modifier
                .align(if (left) Alignment.BottomStart else Alignment.BottomEnd)
        )
    }
}

@Composable
fun TrapezoidCard(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    leftBase: Dp,
    rightBase: Dp,
    color: Color
) {
    Canvas(
        modifier = modifier
    ) {
        drawIntoCanvas {
            val path = Path().apply {
                moveTo(0f, height.toPx())
                lineTo(leftBase.toPx(), 0f)
                lineTo(width.toPx(), 0f)
                lineTo(rightBase.toPx(), height.toPx())
                close()
            }
            drawPath(path, color)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SquadMemberPreview() {
    PokedexTheme {
        SquadMember(
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
            navigateToDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SquadCardPreview() {
    PokedexTheme {
        SquadCard(
            pokemonSquad = PokemonSquad(
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
            left = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrapezoidPreview() {
    PokedexTheme {
        TrapezoidCard(
            width = 225.dp,
            height = 50.dp,
            leftBase = 0.dp,
            rightBase = 175.dp,
            color = typeToColor("grass"),
            modifier = Modifier
                .width(225.dp)
                .height(50.dp)
                .mirrorX()

        )
    }
}