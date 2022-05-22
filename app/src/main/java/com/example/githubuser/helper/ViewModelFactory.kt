package com.example.githubuser.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.inject.Injection
import com.example.githubuser.ui.detail.DetailViewModel
import com.example.githubuser.ui.detail.FavoriteViewModel
import com.example.githubuser.ui.detail.MainViewModel
import com.example.githubuser.ui.favorites.MainRepository


class ViewModelFactory  constructor(private val MainRepository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                 return FavoriteViewModel(MainRepository) as T
        }
            else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                 return MainViewModel(MainRepository) as T
        }
            else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
                 return DetailViewModel(MainRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }


    companion object {
        @Volatile
        var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideFavorite(context))
            }.also { instance = it }
    }
}
