package com.dicoding.pokedex.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.pokedex.ui.theme.PlaceholderColor
import com.dicoding.pokedex.ui.theme.PokedexTheme

@Composable
fun Search (
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    StatelessSearch(
        input = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier.fillMaxWidth()
    )
}

@Composable
fun StatelessSearch(
    input: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Search Pokémon",
                color = PlaceholderColor
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "null",
            )
        },
        shape = RoundedCornerShape(OutlinedTextFieldDefaults.MinHeight / 2),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview () {
    PokedexTheme {
        Search(
            searchQuery = "",
            onSearchQueryChange = {},
        )
    }
}