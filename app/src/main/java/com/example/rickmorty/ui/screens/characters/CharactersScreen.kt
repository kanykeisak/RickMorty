package com.example.rickmorty.ui.screens.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.rickmorty.ui.components.CharacterItem
import com.example.rickmorty.ui.components.FilterDialog
import com.example.rickmorty.ui.viewmodel.CharactersViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharactersViewModel = koinViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val characters = viewModel.characters.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters") },
                actions = {
                    IconButton(onClick = { showFilters = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filters")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { 
                    searchQuery = it
                    viewModel.updateFilters(name = it)
                },
                onSearch = { },
                active = false,
                onActiveChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search characters...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
            ) { }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = characters.itemCount,
                    key = characters.itemKey { it.id }
                ) { index ->
                    characters[index]?.let { character ->
                        CharacterItem(
                            character = character,
                            onClick = { onCharacterClick(character.id) },
                            onFavoriteClick = { viewModel.addToFavorites(character) }
                        )
                    }
                }
            }
        }

        if (showFilters) {
            FilterDialog(
                currentStatus = uiState.statusFilter,
                currentSpecies = uiState.speciesFilter,
                onDismiss = { showFilters = false },
                onApply = { status, species ->
                    viewModel.updateFilters(
                        name = searchQuery,
                        status = status,
                        species = species
                    )
                    showFilters = false
                }
            )
        }
    }
} 