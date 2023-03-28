package com.faruqabdulhakim.githubuser.data.local.entity

data class DetailUser(
    val login: String,
    val name: String?,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    var isFavorite: Boolean,
)