package com.example.githubuser.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.User
import com.example.githubuser.helper.toListUser
import com.example.githubuser.room.toListFavorites
import com.example.githubuser.ui.favorites.MainRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: MainRepository) : ViewModel() {

    private var _favorites = MutableLiveData<List<User>>()
    val favorites get() = _favorites

    fun getAllFavorite(){
        viewModelScope.launch {
            favoriteRepository.getAllFavorite().collect{
                _favorites.value = it.toListFavorites().toListUser()
            }
        }
    }
}




