package com.example.rickmorty.ui.screens.character

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickmorty.data.model.Character
import com.example.rickmorty.ui.viewmodel.CharactersViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onBackClick: () -> Unit,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val character = uiState.characters.firstOrNull()

    LaunchedEffect(Unit) {
        // Load character details if needed
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character?.name ?: "Character Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    character?.let {
                        IconButton(
                            onClick = { viewModel.toggleFavorite(it) }
                        ) {
                            Icon(
                                imageVector = if (viewModel.isFavorite(it.id)) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (viewModel.isFavorite(it.id)) "Remove from favorites" else "Add to favorites",
                                tint = if (viewModel.isFavorite(it.id)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        character?.let { char ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = char.image,
                    contentDescription = char.name,
                    modifier = Modifier
                        .size(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = char.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                DetailItem("Status", char.status)
                DetailItem("Species", char.species)
                DetailItem("Type", char.type.ifEmpty { "Unknown" })
                DetailItem("Gender", char.gender)
                DetailItem("Origin", char.origin.name)
                DetailItem("Location", char.location.name)
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
} 