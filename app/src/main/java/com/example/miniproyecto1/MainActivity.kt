package com.example.miniproyecto1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.miniproyecto1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      //  binding.botonReglas.setOnClickListener {
         //   val intent = Intent(this, Reglas::class.java)
           // startActivity(intent)
        //}


       // var mediaPlayer: MediaPlayer? = null

        //fun pauseBackgroundAudio() {mediaPlayer?.pause()


    }
    fun compartirApp(view: View) {
        val intent = Intent(this, compartir::class.java)
        startActivity(intent)
    }
}
