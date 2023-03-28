package com.faruqabdulhakim.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

    @field:SerializedName("items")
    val items: List<User>
)

data class User(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)
