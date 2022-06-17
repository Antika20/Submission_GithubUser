package com.example.githubuser


import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class UserDetail : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar


    companion object {
        const val EXTRA_user = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
    }

    private fun getResponse(username: String) = lifecycleScope.launch {
        ApiConfig.getApiService().getDetailUser(username).let {
            showDataUser(it.toUser())
            progressBar.isVisible = false
        }
    }

    private fun showDataUser(user: user) {
        progressBar.isVisible = true
        val img_Photo = findViewById<ImageView>(R.id.img_Photo)
        val name = findViewById<TextView>(R.id.detail_name)
        val detail_username = findViewById<TextView>(R.id.detail_username)
        val detail_repository = findViewById<TextView>(R.id.detail_repository)
        val detail_followers = findViewById<TextView>(R.id.detail_followers)
        val detail_following = findViewById<TextView>(R.id.detail_following)
        val detail_company = findViewById<TextView>(R.id.detail_company)
        val detail_location = findViewById<TextView>(R.id.detail_location)

        Glide.with(this).load(user.avatar).circleCrop().into(img_Photo)
        name.text = user.name
        detail_username.text = user.username
        detail_company.text = user.company
        detail_location.text = user.location
        detail_repository.text = user.repository.toString()
        detail_followers.text= user.followers.toString()
        detail_following.text=user.following.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail)
        progressBar = findViewById(R.id.progressBar2)

        intent.extras?.getParcelable<user>(EXTRA_user)?.let { user ->
            getResponse(user.username)
            val sectionsPagerAdapter = SectionsPagerAdapter(this,user.username )
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f

        }
    }
}




