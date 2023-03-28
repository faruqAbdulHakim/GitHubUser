package com.faruqabdulhakim.githubuser.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.faruqabdulhakim.githubuser.databinding.ActivitySplashBinding
import com.faruqabdulhakim.githubuser.ui.main.MainActivity
import com.google.android.material.elevation.SurfaceColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val color = SurfaceColors.SURFACE_5.getColor(this)
        window.statusBarColor = color

        lifecycleScope.launch {
            delay(2000L)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}