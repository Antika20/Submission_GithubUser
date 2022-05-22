package com.example.githubuser.inject

import android.content.Context
import com.example.githubuser.ui.favorites.MainRepository
import com.example.githubuser.retrovit.ApiConfig
import com.example.githubuser.room.FavoriteDB

object Injection {
    fun provideFavorite(context: Context): MainRepository {
        val serviceAPI = ApiConfig.getApiService()
        val database = FavoriteDB.getInstance(context)
        val dao = database.favoriteDao()
        return MainRepository.getInstance(serviceAPI,dao)
    }
}