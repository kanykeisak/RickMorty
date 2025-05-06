package com.example.rickmorty.data.api

import com.example.rickmorty.data.model.Character
import com.example.rickmorty.data.model.CharacterResponse
import com.example.rickmorty.data.model.Episode
import com.example.rickmorty.data.model.EpisodeResponse
import com.example.rickmorty.data.model.Location
import com.example.rickmorty.data.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int = 1): CharacterResponse

    @GET("character")
    suspend fun searchCharacters(
        @Query("name") query: String,
        @Query("page") page: Int = 1
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int = 1): EpisodeResponse

    @GET("episode")
    suspend fun searchEpisodes(
        @Query("name") query: String,
        @Query("page") page: Int = 1
    ): EpisodeResponse

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Episode

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int = 1): LocationResponse

    @GET("location")
    suspend fun searchLocations(
        @Query("name") query: String,
        @Query("page") page: Int = 1
    ): LocationResponse

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): Location
} 