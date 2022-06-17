package com.example.githubuser

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),ListUserAdapter.OnItemClickCallback {

    private lateinit var rvAvatar: RecyclerView
    private val list = ArrayList<user>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvAvatar = findViewById(R.id.rv_Avatar)
        rvAvatar.setHasFixedSize(true)

        list.addAll(listUser)
        showRecyclerList()

    }

    private val listUser: ArrayList<user>
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataUserName = resources.getStringArray(R.array.username)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val listUser = ArrayList<user>()
            for (i in dataName.indices) {
                val user = user(
                    name = dataName[i],
                    username = dataUserName[i],
                    avatar = dataPhoto.getResourceId(i, -1),
                    followers = dataFollowers[i],
                    following = dataFollowing[i],
                    company = dataCompany[i],
                    location = dataLocation[i],
                    repository = dataRepository[i]
                )
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvAvatar.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvAvatar.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(list)
        listUserAdapter.setOnItemClickCallback (this)
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




