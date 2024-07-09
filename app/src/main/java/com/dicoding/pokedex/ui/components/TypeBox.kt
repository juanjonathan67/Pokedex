package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.pokedex.ui.theme.PokedexTheme
import com.dicoding.pokedex.utils.typeToColor

@Composable
fun TypeBox (
    type: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = typeToColor(type),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        AutoResizedText(
            text = type.capitalize(Locale.current),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 0.dp, vertical = 3.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TypeBoxPreview() {
    PokedexTheme {
        TypeBox(
            type = "psychic",
            modifier = Modifier
                .height(40.dp)
                .width(80.dp),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TypeBoxPreviewSmall() {
    PokedexTheme {
        TypeBox(
            type = "psychic",
            modifier = Modifier
                .height(35.dp)
                .width(70.dp),
        )
    }
}