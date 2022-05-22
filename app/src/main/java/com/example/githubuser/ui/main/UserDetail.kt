package com.example.githubuser.ui.main


import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.User
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.UserDetailBinding
import com.example.githubuser.toFav
import com.example.githubuser.ui.detail.DetailViewModel
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.helper.toFavoritesEntity
import com.google.android.material.tabs.TabLayoutMediator


class UserDetail : AppCompatActivity() {
    private lateinit var binding: UserDetailBinding
    private var  isFavorite = false
    private lateinit var currentUser: User

    private fun showDataUser(user: User){
        with(binding){
            Glide.with(this@UserDetail).load(user.avatar).into(imgPhoto)
            detailName.text = user.name
            detailUsername.text = user.username
            detailRepository.text = user.repository.toString()
            detailFollowers.text = user.followers.toString()
            detailFollowing.text = user.following.toString()
            detailCompany.text = user.company
            detailLocation.text = user.location
        }
        binding.progressBar2.isVisible = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels{
            factory
        }

        intent.extras?.getParcelable<User>(EXTRA_user)?.let { user ->
            detailViewModel.checkIsFavorite(user.username)
            detailViewModel.getDetailResponse(user.username)
            detailViewModel.checkIsFavorite(user.username)
            val sectionsPagerAdapter = SectionsPagerAdapter(this,user.username )
            binding.viewPager.adapter = sectionsPagerAdapter

            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f

        }

        detailViewModel.users.observe(this){
            currentUser = it
            showDataUser(it)
        }
        binding.progressBar2.isVisible = true
        actionFloatingButton(detailViewModel)
        showFavorite(detailViewModel)
    }

    private fun showFavorite(detailViewModel:DetailViewModel){
        detailViewModel.isFavorite.observe(this){
            isFavorite = it
            binding.favoriteAdd.setImageResource(
                if (it)R.drawable.ic_love else R.drawable.ic_border
            )
        }
    }

    private fun actionFloatingButton(detailViewModel:DetailViewModel){
        binding.favoriteAdd.setOnClickListener{
            if (isFavorite)  detailViewModel.removeToFavorite(currentUser.toFav().toFavoritesEntity())
            else detailViewModel.addToFavorite(currentUser.toFav().toFavoritesEntity())
            Toast.makeText(this, if (!isFavorite)"Add to Favorite" else "Remove to Favorite", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        const val EXTRA_user = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
    }
}




