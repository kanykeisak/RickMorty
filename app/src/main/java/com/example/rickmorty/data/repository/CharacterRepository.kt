package com.example.rickmorty.data.repository

import com.example.rickmorty.data.api.RickAndMortyApi
import com.example.rickmorty.data.local.FavoriteCharacterDao
import com.example.rickmorty.data.model.Character
import com.example.rickmorty.data.model.CharacterResponse
import com.example.rickmorty.data.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val api: RickAndMortyApi,
    private val favoriteDao: FavoriteCharacterDao
) {
    suspend fun getCharacters(page: Int = 1): CharacterResponse {
        return api.getCharacters(page)
    }

    suspend fun getCharacter(id: Int): Character {
        return api.getCharacter(id)
    }

    fun getAllFavorites(): Flow<List<FavoriteCharacter>> {
        return favoriteDao.getAllFavorites()
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

    suspend fun isFavorite(characterId: Int): Boolean {
        return favoriteDao.isFavorite(characterId)
    }
} 