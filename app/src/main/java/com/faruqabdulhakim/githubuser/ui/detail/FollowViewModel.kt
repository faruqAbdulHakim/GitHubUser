package com.faruqabdulhakim.githubuser.ui.detail

import androidx.lifecycle.ViewModel
import com.faruqabdulhakim.githubuser.data.UserRepository

class FollowViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFollowerList(username: String) = userRepository.getFollowerList(username)

    fun getFollowingList(username: String) = userRepository.getFollowingList(username)
}