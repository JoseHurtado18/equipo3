package com.example.miniproyecto1.view

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.miniproyecto1.R
import com.example.miniproyecto1.viewmodel.ReglasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Reglas : AppCompatActivity() {

    private val reglasViewModel: ReglasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reglas)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageView = findViewById<ImageView>(R.id.mi_imagen)
        val animation = AnimationUtils.loadAnimation(this, R.anim.trans_animation)
        imageView.startAnimation(animation)

        toolbar.setNavigationOnClickListener {
            finish() // Regresar al home principal
        }
    }
}