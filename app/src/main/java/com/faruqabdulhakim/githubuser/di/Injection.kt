package com.faruqabdulhakim.githubuser.di

import android.content.Context
import com.faruqabdulhakim.githubuser.data.UserRepository
import com.faruqabdulhakim.githubuser.data.local.room.FavoritesDatabase
import com.faruqabdulhakim.githubuser.data.local.datastore.SettingPreferences
import com.faruqabdulhakim.githubuser.data.remote.retrofit.ApiConfig
import com.faruqabdulhakim.githubuser.ui.main.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoritesDatabase.getInstance(context)
        val dao = database.favoritesDao()
        return UserRepository.getInstance(apiService, dao)
    }

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }
}
