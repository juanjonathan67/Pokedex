package com.dicoding.pokedex.ui.screen.about

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.pokedex.R
import com.dicoding.pokedex.ui.theme.PokedexTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp),
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .border(BorderStroke(2.dp, Color.Black), shape = CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dp),
                contentDescription = "profile_picture",
            )
        }

        Text(
            text = "Juan Jonathan Nursamsu",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(top = 32.dp)
        )

        Text(
            text = "juanjonathan67@gmail.com",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    PokedexTheme {
        AboutScreen()
    }
}