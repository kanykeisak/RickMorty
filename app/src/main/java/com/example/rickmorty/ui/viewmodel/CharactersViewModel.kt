package com.example.rickmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.model.Character
import com.example.rickmorty.data.model.FavoriteCharacter
import com.example.rickmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CharactersUiState(
    val characters: List<Character> = emptyList(),
    val favorites: List<FavoriteCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasNextPage: Boolean = true
)

class CharactersViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        loadCharacters()
        loadFavorites()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = repository.getCharacters(_uiState.value.currentPage)
                _uiState.update {
                    it.copy(
                        characters = it.characters + response.results,
                        currentPage = it.currentPage + 1,
                        hasNextPage = response.info.next != null,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect { favorites ->
                _uiState.update { it.copy(favorites = favorites) }
            }
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(character.id)
            if (isFavorite) {
                repository.removeFromFavorites(
                    FavoriteCharacter(
                        id = character.id,
                        name = character.name,
                        status = character.status,
                        species = character.species,
                        image = character.image
                    )
                )
            } else {
                repository.addToFavorites(character)
            }
        }
    }

    fun isFavorite(characterId: Int): Boolean {
        return _uiState.value.favorites.any { it.id == characterId }
    }
} 