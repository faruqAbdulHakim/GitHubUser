package com.faruqabdulhakim.githubuser.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.faruqabdulhakim.githubuser.data.UserRepository
import com.faruqabdulhakim.githubuser.data.local.datastore.SettingPreferences
import com.faruqabdulhakim.githubuser.di.Injection
import com.faruqabdulhakim.githubuser.ui.detail.DetailViewModel
import com.faruqabdulhakim.githubuser.ui.detail.FollowViewModel
import com.faruqabdulhakim.githubuser.ui.favorites.FavoritesViewModel
import com.faruqabdulhakim.githubuser.ui.main.MainViewModel
import com.faruqabdulhakim.githubuser.ui.setting.SettingViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val settingPreferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository, settingPreferences) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(settingPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                INSTANCE = synchronized(this) {
                    ViewModelFactory(
                        Injection.provideRepository(context),
                        Injection.provideSettingPreferences(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}