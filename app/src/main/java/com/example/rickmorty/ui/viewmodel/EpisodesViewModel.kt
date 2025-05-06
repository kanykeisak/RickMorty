package com.example.rickmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.model.Episode
import com.example.rickmorty.data.repository.EpisodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EpisodesUiState(
    val episodes: List<Episode> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasNextPage: Boolean = true
)

class EpisodesViewModel(
    private val repository: EpisodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EpisodesUiState())
    val uiState: StateFlow<EpisodesUiState> = _uiState.asStateFlow()

    init {
        loadEpisodes()
    }

    fun loadEpisodes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = repository.getEpisodes(_uiState.value.currentPage)
                _uiState.update {
                    it.copy(
                        episodes = it.episodes + response.results,
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
} 