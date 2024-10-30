package com.example.miniproyecto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class compartir: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        compartirApp() // Llama al método para compartir inmediatamente
        finish() // Cierra la actividad después de compartir
    }

    private fun compartirApp() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "App pico botella\nSolo los valientes lo juegan!!\nhttps://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}