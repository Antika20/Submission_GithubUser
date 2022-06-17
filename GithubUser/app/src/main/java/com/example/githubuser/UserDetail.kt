package com.example.githubuser



import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class UserDetail  : AppCompatActivity() {


    companion object {
        const val EXTRA_user = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail)

        val img_Photo = findViewById<ImageView>(R.id.img_Photo)
        val name = findViewById<TextView>(R.id.detail_name)
        val detail_username = findViewById<TextView>(R.id.detail_username)
        val detail_repository = findViewById<TextView>(R.id.detail_repository)
        val detail_followers = findViewById<TextView>(R.id.detail_followers)
        val detail_following = findViewById<TextView>(R.id.detail_following)
        val detail_company = findViewById<TextView>(R.id.detail_company)
        val detail_location = findViewById<TextView>(R.id.detail_location)


        intent.extras?.getParcelable<user>(EXTRA_user)?.let{
            user->
            img_Photo.setImageResource(user.avatar)
            name.text = user.name
            detail_username.text = user.username
            detail_repository.text = user.repository
            detail_followers.text = user.followers
            detail_following.text = user.following
            detail_company.text = user.company
            detail_location.text = user.location
        }
        }
    }



