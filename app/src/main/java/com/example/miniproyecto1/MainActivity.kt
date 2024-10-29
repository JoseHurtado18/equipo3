package com.example.miniproyecto1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.miniproyecto1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val botonControl = findViewById<ImageView>(R.id.boton_control)

        botonControl.setOnClickListener {
            val intent = Intent(this, Reglas::class.java)
            startActivity(intent)
        }

        val botonCompartir = findViewById<ImageView>(R.id.boton_compartir)

        botonCompartir.setOnClickListener {
            val tituloApp = "App pico botella"
            val eslogan = "Solo los valientes lo juegan !!"
            val urlDescarga = "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$tituloApp\n$eslogan\n$urlDescarga")
                type = "text/plain"
            }

            startActivity(Intent.createChooser(sendIntent, "Compartir con"))
        }


    }

}
