package com.dicoding.pokedex.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.dicoding.pokedex.R
import com.dicoding.pokedex.ui.theme.Bug
import com.dicoding.pokedex.ui.theme.Dark
import com.dicoding.pokedex.ui.theme.Dragon
import com.dicoding.pokedex.ui.theme.Electric
import com.dicoding.pokedex.ui.theme.Fairy
import com.dicoding.pokedex.ui.theme.Fighting
import com.dicoding.pokedex.ui.theme.Fire
import com.dicoding.pokedex.ui.theme.Flying
import com.dicoding.pokedex.ui.theme.Ghost
import com.dicoding.pokedex.ui.theme.Grass
import com.dicoding.pokedex.ui.theme.Ground
import com.dicoding.pokedex.ui.theme.Ice
import com.dicoding.pokedex.ui.theme.Normal
import com.dicoding.pokedex.ui.theme.Poison
import com.dicoding.pokedex.ui.theme.Psychic
import com.dicoding.pokedex.ui.theme.Rock
import com.dicoding.pokedex.ui.theme.Steel
import com.dicoding.pokedex.ui.theme.Water

fun typeToColor(type: String) : Color {
    when(type) {
        "normal" -> { return Normal }
        "fire" -> { return Fire }
        "water" -> { return Water }
        "electric" -> { return Electric }
        "grass" -> { return Grass }
        "ice" -> { return Ice }
        "ghost" -> { return Ghost }
        "dark" -> { return Dark }
        "fairy" -> { return Fairy }
        "fighting" -> { return Fighting }
        "psychic" -> { return Psychic }
        "poison" -> { return Poison }
        "ground" -> { return Ground }
        "flying" -> { return Flying }
        "bug" -> { return Bug }
        "rock" -> { return Rock }
        "dragon" -> { return Dragon }
        "steel" -> { return Steel }
        else -> { return Color.Black }
    }
}

fun typeToIcon(type: String) : Int {
    when(type) {
        "normal" -> { return R.drawable.ic_normal }
        "fire" -> { return R.drawable.ic_fire }
        "water" -> { return R.drawable.ic_water }
        "electric" -> { return R.drawable.ic_electric }
        "grass" -> { return R.drawable.ic_grass }
        "ice" -> { return R.drawable.ic_ice }
        "ghost" -> { return R.drawable.ic_ghost }
        "dark" -> { return R.drawable.ic_dark }
        "fairy" -> { return R.drawable.ic_fairy }
        "fighting" -> { return R.drawable.ic_fighting }
        "psychic" -> { return R.drawable.ic_psychic }
        "poison" -> { return R.drawable.ic_poison }
        "ground" -> { return R.drawable.ic_ground }
        "flying" -> { return R.drawable.ic_flying }
        "bug" -> { return R.drawable.ic_bug }
        "rock" -> { return R.drawable.ic_rock }
        "dragon" -> { return R.drawable.ic_dragon }
        "steel" -> { return R.drawable.ic_steel }
        else -> { return R.drawable.ic_pokeball }
    }
}

fun Modifier.mirrorX(): Modifier {
    return this.scale(scaleX = -1F, scaleY = 1F)
}

fun Modifier.mirrorY(): Modifier {
    return this.scale(scaleX = 1F, scaleY = -1F)
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

fun String?.toTitleCaseOrNull(): String {
    return this?.split("-", " ")
        ?.joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
        ?.replace("-", " ")
        ?: "null"
}

fun String?.capitalizeSentencesOrNull(): String {
    return this?.let { str ->
        str.split("\n").joinToString(" ") { it.trim() }
    } ?: "null"
}

val typeAdvantages = mapOf(
    "normal" to mapOf(),
    "fire" to mapOf(
        "grass" to 1,
        "ice" to 1,
        "bug" to 1,
        "steel" to 1
    ),
    "water" to mapOf(
        "fire" to 1,
        "ground" to 1,
        "rock" to 1
    ),
    "electric" to mapOf(
        "water" to 1,
        "flying" to 1
    ),
    "grass" to mapOf(
        "water" to 1,
        "ground" to 1,
        "rock" to 1
    ),
    "ice" to mapOf(
        "grass" to 1,
        "ground" to 1,
        "flying" to 1,
        "dragon" to 1
    ),
    "fighting" to mapOf(
        "normal" to 1,
        "ice" to 1,
        "rock" to 1,
        "dark" to 1,
        "steel" to 1
    ),
    "poison" to mapOf(
        "grass" to 1,
        "fairy" to 1
    ),
    "ground" to mapOf(
        "fire" to 1,
        "electric" to 1,
        "poison" to 1,
        "rock" to 1,
        "steel" to 1
    ),
    "flying" to mapOf(
        "grass" to 1,
        "fighting" to 1,
        "bug" to 1
    ),
    "psychic" to mapOf(
        "fighting" to 1,
        "poison" to 1
    ),
    "bug" to mapOf(
        "grass" to 1,
        "psychic" to 1,
        "dark" to 1
    ),
    "rock" to mapOf(
        "fire" to 1,
        "ice" to 1,
        "flying" to 1,
        "bug" to 1
    ),
    "ghost" to mapOf(
        "psychic" to 1,
        "ghost" to 1
    ),
    "dragon" to mapOf(
        "dragon" to 1
    ),
    "dark" to mapOf(
        "psychic" to 1,
        "ghost" to 1
    ),
    "steel" to mapOf(
        "ice" to 1,
        "rock" to 1,
        "fairy" to 1
    ),
    "fairy" to mapOf(
        "fighting" to 1,
        "dragon" to 1,
        "dark" to 1
    )
)

val typeDisadvantages = mapOf(
    "normal" to mapOf(
        "fighting" to -1
    ),
    "fire" to mapOf(
        "water" to -1,
        "rock" to -1,
        "ground" to -1
    ),
    "water" to mapOf(
        "electric" to -1,
        "grass" to -1
    ),
    "electric" to mapOf(
        "ground" to -1
    ),
    "grass" to mapOf(
        "fire" to -1,
        "poison" to -1,
        "flying" to -1,
        "bug" to -1,
        "ice" to -1
    ),
    "ice" to mapOf(
        "fire" to -1,
        "fighting" to -1,
        "rock" to -1,
        "steel" to -1
    ),
    "fighting" to mapOf(
        "flying" to -1,
        "psychic" to -1,
        "fairy" to -1
    ),
    "poison" to mapOf(
        "ground" to -1,
        "psychic" to -1
    ),
    "ground" to mapOf(
        "water" to -1,
        "grass" to -1,
        "ice" to -1
    ),
    "flying" to mapOf(
        "electric" to -1,
        "rock" to -1,
        "ice" to -1
    ),
    "psychic" to mapOf(
        "bug" to -1,
        "ghost" to -1,
        "dark" to -1
    ),
    "bug" to mapOf(
        "fire" to -1,
        "flying" to -1,
        "rock" to -1
    ),
    "rock" to mapOf(
        "water" to -1,
        "grass" to -1,
        "fighting" to -1,
        "ground" to -1,
        "steel" to -1
    ),
    "ghost" to mapOf(
        "psychic" to -1,
        "ghost" to -1
    ),
    "dragon" to mapOf(
        "ice" to -1,
        "dragon" to -1,
        "fairy" to -1
    ),
    "dark" to mapOf(
        "fighting" to -1,
        "bug" to -1,
        "fairy" to -1
    ),
    "steel" to mapOf(
        "fire" to -1,
        "fighting" to -1,
        "ground" to -1
    ),
    "fairy" to mapOf(
        "poison" to -1,
        "steel" to -1
    )
)