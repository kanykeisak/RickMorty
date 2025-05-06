package com.example.rickmorty.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.api.RickMortyApi
import com.example.rickmorty.data.model.Character
import com.example.rickmorty.di.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val api = NetworkModule.api

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1
    private var hasNextPage = true

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        if (!hasNextPage || _isLoading.value) return

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val response = api.getCharacters(currentPage)
                _characters.value = _characters.value + response.results
                hasNextPage = response.info.next != null
                currentPage++
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
} 