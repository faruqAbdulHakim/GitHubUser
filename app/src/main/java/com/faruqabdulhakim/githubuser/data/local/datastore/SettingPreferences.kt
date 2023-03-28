package com.faruqabdulhakim.githubuser.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")

    fun getIsDarkMode(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_DARK_MODE] ?: false
        }
    }

    suspend fun setIsDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            if (INSTANCE == null) {
                INSTANCE = synchronized(this) {
                    SettingPreferences(dataStore)
                }
            }
            return INSTANCE as SettingPreferences
        }
    }
}