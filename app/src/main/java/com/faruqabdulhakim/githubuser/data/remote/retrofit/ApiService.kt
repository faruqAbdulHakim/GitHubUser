package com.faruqabdulhakim.githubuser.data.remote.retrofit

import com.faruqabdulhakim.githubuser.data.remote.response.DetailUserResponse
import com.faruqabdulhakim.githubuser.data.remote.response.User
import com.faruqabdulhakim.githubuser.data.remote.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getUserList(@Query("q") username: String): SearchUserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowerList(@Path("username") username: String) : List<User>

    @GET("users/{username}/following")
    suspend fun getUserFollowingList(@Path("username") username: String) : List<User>
}