package com.faruqabdulhakim.githubuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faruqabdulhakim.githubuser.data.UserRepository
import com.faruqabdulhakim.githubuser.data.local.entity.Favorite
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDetailUser(username: String) = userRepository.getUserDetail(username)

    fun toggleFavorite(username: String, avatarUrl: String, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                userRepository.deleteFavorite(Favorite(username, avatarUrl))
            } else {
                userRepository.insertFavorite(Favorite(username, avatarUrl))
            }
        }
    }

}