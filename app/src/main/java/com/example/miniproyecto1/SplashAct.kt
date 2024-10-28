package com.example.miniproyecto1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        // Muestra la pantalla splash por 5 segundos
        Handler().postDelayed({
            // Inicia la actividad principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad splash
        }, 5000) // 5000 milisegundos = 5 segundos
    }
}
