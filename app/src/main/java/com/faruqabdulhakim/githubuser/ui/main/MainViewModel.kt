package com.faruqabdulhakim.githubuser.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.faruqabdulhakim.githubuser.data.UserRepository
import com.faruqabdulhakim.githubuser.data.local.datastore.SettingPreferences

class MainViewModel(
    private val userRepository: UserRepository,
    private val settingPreferences: SettingPreferences,
    ) : ViewModel() {

    fun searchUser(query: String) = userRepository.getUserList(query)

    fun getIsDarkMode() = settingPreferences.getIsDarkMode().asLiveData()
}