package com.example.githubuser.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.retrovit.ApiConfig
import com.example.githubuser.User
import com.example.githubuser.toListUser
import com.example.githubuser.ui.favorites.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val MainRepository: MainRepository) : ViewModel() {
    private var _user = MutableLiveData<List<User>>()
    val user get() = _user


    fun getResponse(username: String) = viewModelScope.launch {
        ApiConfig.getApiService().getUser(username).let {
            _user.value = it.items?.toListUser()
        }

    }

    fun getUsername(username: String) = viewModelScope.launch {
        MainRepository.serviceAPI(username).let {
            _user.value = it.items?.toListUser()
        }
    }

    fun getFollowers(username: String) = viewModelScope.launch {
        MainRepository.getFollowers(username).let {
            _user.value = it.toListUser()
        }
    }

    fun getFollowing(username: String) = viewModelScope.launch {
        MainRepository.getFollowing(username).let {
            _user.value = it.toListUser()
        }
    }
}


