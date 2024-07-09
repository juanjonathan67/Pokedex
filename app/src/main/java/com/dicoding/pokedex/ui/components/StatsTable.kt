package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.pokedex.data.model.PokemonStats
import com.dicoding.pokedex.ui.theme.Bug
import com.dicoding.pokedex.ui.theme.Fire
import com.dicoding.pokedex.ui.theme.Grass
import com.dicoding.pokedex.ui.theme.PokedexTheme

@Composable
fun StatsTable(
    stats: PokemonStats,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
    ) {
        val hp = stats.hp.toInt()
        val attack = stats.attack.toInt()
        val defense = stats.defense.toInt()
        val spAtt = stats.spAtt.toInt()
        val spDef = stats.spDef.toInt()
        val speed = stats.speed.toInt()

        val total = hp + attack + defense + spAtt + spDef + speed

        StatsDivider()
        StatsRow(name = "HP", stat = hp)
        StatsDivider()
        StatsRow(name = "Attack", stat = attack)
        StatsDivider()
        StatsRow(name = "Defense", stat = defense)
        StatsDivider()
        StatsRow(name = "Sp. Att", stat = spAtt)
        StatsDivider()
        StatsRow(name = "Sp. Def", stat = spDef)
        StatsDivider()
        StatsRow(name = "Speed", stat = speed)
        StatsDivider()
        StatsRow(name = "Total", stat = total)
        StatsDivider()
    }
}

@Composable
fun StatsRow(
    name: String,
    stat: Int,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF434A54),
            modifier = Modifier
                .padding(
                    start = 4.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 20.dp,
                )
                .width(80.dp)
        )

        if (name != "Total") {
            Box(
                modifier = Modifier
                    .width(if (stat > 200) 200.dp else stat.dp)
                    .height(16.dp)
                    .background(
                        color = if (stat in 0..49) {
                            Color.Red
                        } else if (stat in 50..99) {
                            Fire
                        } else if (stat in 100..149) {
                            Bug
                        } else if (stat > 149) {
                            Grass
                        } else {
                            Color.Black
                        },
                        shape = RoundedCornerShape(4.dp)
                    )
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = stat.toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 4.dp
                )
        )
    }
}

@Composable
fun StatsDivider() {
    HorizontalDivider(
        color = Color(0xFFE6E9ED)
    )
}

@Preview(showBackground = true)
@Composable
fun StatsTablePreview() {
    PokedexTheme {
        StatsTable(
            stats = PokemonStats (
                hp = "45",
                attack = "49",
                defense = "49",
                spAtt = "65",
                spDef = "65",
                speed = "45",
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatsRowPreview() {
    PokedexTheme {
        StatsRow(
            name = "HP",
            stat = 45,
        )
    }
}

@Preview(showBackground = true, heightDp = 10)
@Composable
fun StatsDividerPreview() {
    PokedexTheme {
        StatsDivider()
    }
}