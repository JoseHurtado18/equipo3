package com.example.miniproyecto1

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Reglas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reglas)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val imageView = findViewById<ImageView>(R.id.mi_imagen)
        val animation = AnimationUtils.loadAnimation(this, R.anim.trans_animation)
        imageView.startAnimation(animation)

       // (this.application as? act_reglas)?.pauseBackgroundAudio()




        toolbar.setNavigationOnClickListener {
            // Regresar al home principal
            // Restablecer audio de fondo
            // ...
            finish()
        }
    }
}