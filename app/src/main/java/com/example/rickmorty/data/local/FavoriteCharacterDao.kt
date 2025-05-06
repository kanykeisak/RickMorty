package com.example.rickmorty.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.data.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Query("SELECT * FROM favorite_characters")
    fun getAllFavorites(): Flow<List<FavoriteCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(character: FavoriteCharacter)

    @Delete
    suspend fun deleteFavorite(character: FavoriteCharacter)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :characterId)")
    suspend fun isFavorite(characterId: Int): Boolean
} 