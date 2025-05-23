package com.example.rickmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmorty.data.model.Character
import com.example.rickmorty.data.model.FavoriteCharacter
import com.example.rickmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CharactersUiState(
    val characters: PagingData<Character> = PagingData.empty(),
    val favorites: List<FavoriteCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val nameFilter: String = "",
    val statusFilter: String? = null,
    val speciesFilter: String? = null
)

class CharactersViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private val _characters = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val characters: StateFlow<PagingData<Character>> = _characters.asStateFlow()

    init {
        loadCharacters()
        loadFavorites()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            repository.getCharacters(
                name = _uiState.value.nameFilter.takeIf { it.isNotEmpty() },
                status = _uiState.value.statusFilter,
                species = _uiState.value.speciesFilter
            ).cachedIn(viewModelScope).collect { pagingData ->
                _characters.value = pagingData
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

    fun updateFilters(
        name: String? = null,
        status: String? = null,
        species: String? = null
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                nameFilter = name ?: currentState.nameFilter,
                statusFilter = status,
                speciesFilter = species
            )
        }
        loadCharacters()
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