package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SubTitle(
    subTitle: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = subTitle,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
            .padding(top = 20.dp)
    )
}