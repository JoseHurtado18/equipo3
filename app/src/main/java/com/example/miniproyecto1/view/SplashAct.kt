package com.example.miniproyecto1.view

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.miniproyecto1.R
import com.example.miniproyecto1.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashAct : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)


        val miImageView = findViewById<ImageView>(R.id.splash_icon)
        val animacion = AnimationUtils.loadAnimation(this, R.anim.girar_botella)
        miImageView.startAnimation(animacion)

        splashViewModel.startTimer {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}