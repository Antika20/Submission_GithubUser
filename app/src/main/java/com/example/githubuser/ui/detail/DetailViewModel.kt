package com.example.githubuser.ui.detail


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.githubuser.User
import com.example.githubuser.room.FavoriteEntity
import com.example.githubuser.toUser
import com.example.githubuser.ui.favorites.MainRepository

import kotlinx.coroutines.launch

class DetailViewModel(private val favoriteRepository: MainRepository) : ViewModel() {
    private var _user = MutableLiveData<User>()
    val users get() = _user
    val isFavorite = MutableLiveData<Boolean>()

    fun getDetailResponse(username: String) = viewModelScope.launch {
        favoriteRepository.getDetailUserFavorite(username).let {
            _user.value = it.toUser()
        }
    }

    fun checkIsFavorite(username: String) = viewModelScope.launch{
        favoriteRepository.getCheckFavorite(username).collect{
            isFavorite.value = it
        }
    }

    fun addToFavorite(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        favoriteRepository.addFavorite(favoriteEntity)

    }

    fun removeToFavorite(favoriteEntity: FavoriteEntity)  = viewModelScope.launch {
        favoriteRepository.delete(favoriteEntity)
    }
}
