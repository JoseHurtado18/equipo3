package com.example.miniproyecto1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SplashAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        val miImageView = findViewById<ImageView>(R.id.splash_icon)
        val animacion = AnimationUtils.loadAnimation(this, R.anim.girar_botella)
        miImageView.startAnimation(animacion)

        // Muestra la pantalla splash por 5 segundos
        Handler().postDelayed({
            // Inicia la actividad principal
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad splash
        }, 5000) // 5000 milisegundos = 5 segundos
    }
}
