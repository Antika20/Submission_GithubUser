package com.example.githubuser.ui.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.User
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.databinding.ActivityFavoritesPagesBinding
import com.example.githubuser.ui.detail.FavoriteViewModel
import com.example.githubuser.helper.ViewModelFactory


class FavoritesPages : AppCompatActivity(), ListUserAdapter.OnItemClickCallback{
    private lateinit var binding:ActivityFavoritesPagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesPagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val favoriteViewModel:FavoriteViewModel by viewModels{
            factory
        }
        binding.progressBarFavorite.isVisible = true
        favoriteViewModel.getAllFavorite()
        favoriteViewModel.favorites.observe(this){
            showRecyclerList(it)
        }
    }

    private fun showRecyclerList(listUser: List<User>) {
        binding.progressBarFavorite.isVisible = false
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
           binding.rvFavorite.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(listUser)
        listUserAdapter.setOnItemClickCallback(this)
        binding.rvFavorite.adapter = listUserAdapter
    }

    override fun onItemClicked(data: User) {
        startActivity(
            Intent(this, UserDetail::class.java).apply {
                putExtra(UserDetail.EXTRA_user, data)
            }
        )
    }
}