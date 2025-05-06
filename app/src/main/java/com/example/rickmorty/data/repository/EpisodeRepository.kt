package com.example.rickmorty.data.repository

import com.example.rickmorty.data.api.RickAndMortyApi
import com.example.rickmorty.data.model.Episode
import com.example.rickmorty.data.model.EpisodeResponse

class EpisodeRepository(private val api: RickAndMortyApi) {
    suspend fun getEpisodes(page: Int = 1): EpisodeResponse {
        return api.getEpisodes(page)
    }

    suspend fun getEpisode(id: Int): Episode {
        return api.getEpisode(id)
    }
} 