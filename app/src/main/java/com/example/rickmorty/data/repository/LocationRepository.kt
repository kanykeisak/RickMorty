package com.example.rickmorty.data.repository

import com.example.rickmorty.data.api.RickAndMortyApi
import com.example.rickmorty.data.model.Location
import com.example.rickmorty.data.model.LocationResponse

class LocationRepository(private val api: RickAndMortyApi) {
    suspend fun getLocations(page: Int = 1): LocationResponse {
        return api.getLocations(page)
    }

    suspend fun getLocation(id: Int): Location {
        return api.getLocation(id)
    }
} 