package com.example.rickmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.model.Character
import com.example.rickmorty.data.model.FavoriteCharacter
import com.example.rickmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CharactersUiState(
    val characters: List<Character> = emptyList(),
    val favorites: List<FavoriteCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
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

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.getCharacters().collect { characters ->
                    _uiState.update { it.copy(characters = characters, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteCharacters().collect { favorites ->
                _uiState.update { it.copy(favorites = favorites) }
            }
        }
    }

    fun searchCharacters(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.searchCharacters(query).collect { characters ->
                    _uiState.update { it.copy(characters = characters, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun searchFavorites(query: String) {
        viewModelScope.launch {
            repository.searchFavoriteCharacters(query).collect { favorites ->
                _uiState.update { it.copy(favorites = favorites) }
            }
        }
    }

    fun addToFavorites(character: Character) {
        viewModelScope.launch {
            repository.addToFavorites(character)
        }
    }

    fun removeFromFavorites(character: FavoriteCharacter) {
        viewModelScope.launch {
            repository.removeFromFavorites(character)
        }
    }

    fun isFavorite(characterId: Int): Flow<Boolean> {
        return repository.isFavorite(characterId)
    }
} 