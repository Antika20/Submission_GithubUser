package com.example.githubuser.ui.favorites

import com.example.githubuser.retrovit.ApiService
import com.example.githubuser.SearchResponse
import com.example.githubuser.UserResponse
import com.example.githubuser.room.FavoriteDao
import com.example.githubuser.room.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class MainRepository private constructor(
    private val serviceApi: ApiService,
    private val favoriteDao: FavoriteDao
) {

    fun getAllFavorite(): Flow<List<FavoriteEntity>> {
       return favoriteDao.getAllFavorite()
    }

     suspend fun addFavorite(favoriteEntity: FavoriteEntity){
       return favoriteDao.addFavorite(favoriteEntity)
    }

     suspend fun delete(favoriteEntity: FavoriteEntity){
       return favoriteDao.delete(favoriteEntity)
    }

     fun getCheckFavorite(username: String): Flow<Boolean> {
        return favoriteDao.getCheckFavorite(username)
    }

    suspend fun serviceAPI(username:String): SearchResponse {
        return serviceApi.getUser(username)
    }
    suspend fun getFollowing (username: String):List<UserResponse>{
        return serviceApi.getFollowing(username)
    }

    suspend fun getFollowers(username: String):List<UserResponse>{
        return serviceApi.getFollowers(username)
    }

    suspend fun getDetailUserFavorite(username: String): UserResponse {
        return serviceApi.getDetailUser(username)
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null
        fun getInstance(
            serviceApi: ApiService,
            favoriteDao: FavoriteDao,
        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(serviceApi,favoriteDao)
            }.also { instance = it }
    }
}

