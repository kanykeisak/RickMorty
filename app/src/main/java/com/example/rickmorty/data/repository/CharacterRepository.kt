package com.example.rickmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickmorty.data.api.RickAndMortyApi
import com.example.rickmorty.data.local.FavoriteCharacterDao
import com.example.rickmorty.data.model.Character
import com.example.rickmorty.data.model.CharacterResponse
import com.example.rickmorty.data.model.FavoriteCharacter
import com.example.rickmorty.data.paging.CharacterPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val api: RickAndMortyApi,
    private val favoriteDao: FavoriteCharacterDao
) {
    suspend fun getCharacters(page: Int = 1): CharacterResponse {
        return api.getCharacters(page)
    }

    suspend fun searchCharacters(query: String, page: Int = 1): CharacterResponse {
        return api.searchCharacters(query, page)
    }

    suspend fun getCharacter(id: Int): Character {
        return api.getCharacter(id)
    }

    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null
    ): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    api = api,
                    name = name,
                    status = status,
                    species = species
                )
            }
        ).flow
    }

    fun searchCharacters(query: String): Flow<List<Character>> {
        return api.searchCharacters(query).map { it.results }
    }

    fun getFavoriteCharacters(): Flow<List<FavoriteCharacter>> {
        return favoriteDao.getAllFavorites()
    }

    fun searchFavoriteCharacters(query: String): Flow<List<FavoriteCharacter>> {
        return favoriteDao.searchFavorites("%$query%")
    }

    suspend fun addToFavorites(character: Character) {
        favoriteDao.insertFavorite(
            FavoriteCharacter(
                id = character.id,
                name = character.name,
                status = character.status,
                species = character.species,
                image = character.image
            )
        )
    }

    suspend fun removeFromFavorites(character: FavoriteCharacter) {
        favoriteDao.deleteFavorite(character)
    }

    fun isFavorite(characterId: Int): Flow<Boolean> {
        return favoriteDao.isFavorite(characterId)
    }
} 