package com.faruqabdulhakim.githubuser.ui.favorites

import androidx.lifecycle.ViewModel
import com.faruqabdulhakim.githubuser.data.UserRepository

class FavoritesViewModel(private val favoritesRepository: UserRepository) : ViewModel() {
    fun getFavorites() = favoritesRepository.getFavorites()
}