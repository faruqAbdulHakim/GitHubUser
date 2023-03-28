package com.faruqabdulhakim.githubuser.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.faruqabdulhakim.githubuser.data.local.datastore.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val settingPreferences: SettingPreferences) : ViewModel() {

    fun getIsDarkMode() = settingPreferences.getIsDarkMode().asLiveData()

    fun setIsDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingPreferences.setIsDarkMode(isDarkMode)
        }
    }
}