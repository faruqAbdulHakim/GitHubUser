package com.faruqabdulhakim.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faruqabdulhakim.githubuser.data.local.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FavoritesDatabase {
            if (INSTANCE == null) {
                INSTANCE = synchronized(this) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        FavoritesDatabase::class.java,
                        "Favorites.db"
                    ).build()
                }
            }
            return INSTANCE as FavoritesDatabase
        }
    }
}