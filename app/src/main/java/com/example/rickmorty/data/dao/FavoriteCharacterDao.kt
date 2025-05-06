package com.example.rickmorty.data.dao

import androidx.room.*
import com.example.rickmorty.data.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Query("SELECT * FROM favorite_characters")
    fun getAllFavorites(): Flow<List<FavoriteCharacter>>

    @Query("SELECT * FROM favorite_characters WHERE name LIKE :query")
    fun searchFavorites(query: String): Flow<List<FavoriteCharacter>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :characterId)")
    fun isFavorite(characterId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(character: FavoriteCharacter)

    @Delete
    suspend fun deleteFavorite(character: FavoriteCharacter)
} 