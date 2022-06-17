package com.example.githubuser

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ListUserAdapter.OnItemClickCallback {


    private lateinit var rvAvatar: RecyclerView
    private lateinit var btnSearch: ImageView
    private lateinit var edtQuery: TextInputEditText
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvAvatar = findViewById(R.id.rv_Avatar)
        rvAvatar.setHasFixedSize(true)

        btnSearch = findViewById(R.id.btn_search)
        btnSearch.setOnClickListener {
            searchUser()
        }
        progressBar = findViewById(R.id.progressBar)
        progressBar.isVisible = false
        edtQuery = findViewById(R.id.edt_query)
        edtQuery.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                searchUser()
            }
            true

        }
    }

    private fun searchUser() {
        progressBar.isVisible = true
        getResponse(edtQuery.text.toString())
    }

    private fun getResponse(username: String) = lifecycleScope.launch {
        ApiConfig.getApiService().getUser(username).let {
            it.items?.toListUser()?.let {listUser->
                showRecyclerList(listUser)
            }
        }

    }

    private fun showRecyclerList(listUser: List<user>) {
        progressBar.isVisible = false
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvAvatar.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvAvatar.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(listUser)
        listUserAdapter.setOnItemClickCallback(this)
        rvAvatar.adapter = listUserAdapter
    }


    override fun onItemClicked(data: user) {
        startActivity(
            Intent(this@MainActivity, UserDetail::class.java).apply {
                putExtra(UserDetail.EXTRA_user, data)
            }
        )
    }
}







