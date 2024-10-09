package com.example.unieventos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarTop(
    onSearchTextChanged: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    val searchBarColors = androidx.compose.material3.SearchBarDefaults.colors(
        containerColor = Color(0xFF161616), // Set the background color of the search bar
    )

    androidx.compose.material3.SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 20.dp, top = 1.dp),
           // .height(60.dp),
        colors = searchBarColors,
        query = searchText,
        onQueryChange = {
            searchText = it
            onSearchTextChanged(it)
        },
        onSearch = { isActive = false },
        active = isActive,
        onActiveChange = { isActive = it },
        placeholder = { Text("Search", color = Color.White) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (isActive) {
                IconButton(onClick = {
                    if (searchText.isNotEmpty()) {
                        searchText = ""
                        onSearchTextChanged("")
                    } else {
                        isActive = false
                    }
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Close Icon")
                }
            }
        },

        ) {
         Text(text = "Sufrimiento")
    }
}
