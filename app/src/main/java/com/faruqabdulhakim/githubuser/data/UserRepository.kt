package com.faruqabdulhakim.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.faruqabdulhakim.githubuser.data.local.room.FavoritesDao
import com.faruqabdulhakim.githubuser.data.local.entity.*
import com.faruqabdulhakim.githubuser.data.remote.response.User
import com.faruqabdulhakim.githubuser.data.remote.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoritesDao: FavoritesDao
) {

    fun getUserList(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserList(username)
            emit(Result.Success(response.items))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserDetail(username: String): LiveData<Result<DetailUser>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserDetail(username)
            val isFavorite = favoritesDao.isFavorite(username)
            val detailUser = DetailUser(
                response.login,
                response.name,
                response.avatarUrl,
                response.followers,
                response.following,
                isFavorite
            )
            emit(Result.Success(detailUser))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowerList(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowerList(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowingList(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowingList(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavorites() = favoritesDao.getAllFavorites()

    suspend fun insertFavorite(favorite: Favorite) = favoritesDao.insertFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = favoritesDao.deleteFavorite(favorite)

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        @JvmStatic
        fun getInstance(apiService: ApiService, favoritesDao: FavoritesDao): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = synchronized(this) {
                    UserRepository(apiService, favoritesDao)
                }
            }

            return INSTANCE as UserRepository
        }
    }
}