package com.example.githubuser.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.User
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.thema.LocalTheme
import com.example.githubuser.ui.detail.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ListUserAdapter.OnItemClickCallback {

    private lateinit var binding: ActivityMainBinding

    private var isCurrentThemeDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentTheme ()

         val factory = ViewModelFactory.getInstance(this)
         val mainViewModel: MainViewModel by viewModels{
             factory
         }

        binding.progressBar.isVisible =  false
        mainViewModel.getUsername("username")
        mainViewModel.user.observe(this) {
            showRecyclerList(it)
        }

        binding.rvAvatar.setHasFixedSize(true)

        binding.btnSearch.setOnClickListener {
            searchUser(mainViewModel)
        }

        binding.progressBar.isVisible =  true
        binding.edtQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                searchUser(mainViewModel)
            }
            true
        }
    }

    private fun searchUser(mainViewModel:MainViewModel) {
        binding.progressBar.isVisible = true
        mainViewModel.getResponse(binding.edtQuery.text.toString())
    }

    private fun showRecyclerList(listUser: List<User>) {
        binding.progressBar.isVisible = false
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvAvatar.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvAvatar.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(listUser)
        listUserAdapter.setOnItemClickCallback(this)
        binding.rvAvatar.adapter = listUserAdapter
    }

    private fun setAppTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO
            else AppCompatDelegate.MODE_NIGHT_YES
        )
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_theme -> {
                isCurrentThemeDarkMode = !isCurrentThemeDarkMode
                putDataTheme(isCurrentThemeDarkMode)

            }
            R.id.action_favorite -> {
                startActivity(
                    Intent(this@MainActivity, FavoritesPages::class.java)
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(data: User) {
        startActivity(
            Intent(this@MainActivity, UserDetail::class.java).apply {
                putExtra(UserDetail.EXTRA_user, data)
            }
        )
    }



    private fun putDataTheme(isDarkMode: Boolean) {
        lifecycleScope.launch {
            LocalTheme.updateTheme(this@MainActivity, isDarkMode)
        }
    }

    private fun getCurrentTheme() {
        lifecycleScope.launchWhenCreated {
            LocalTheme.getTheme(this@MainActivity).collect {
                isCurrentThemeDarkMode = it
                setAppTheme(it)
            }
        }
    }
}









