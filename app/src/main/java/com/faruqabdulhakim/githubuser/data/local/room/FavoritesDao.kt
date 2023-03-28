package com.faruqabdulhakim.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.faruqabdulhakim.githubuser.data.local.entity.Favorite

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE username = :username)")
    suspend fun isFavorite(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}