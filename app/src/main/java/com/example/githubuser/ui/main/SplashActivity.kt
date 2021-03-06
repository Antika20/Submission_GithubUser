package com.example.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import com.example.githubuser.R


class SplashActivity  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
    }
}
