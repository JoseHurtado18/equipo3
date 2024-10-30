package com.example.miniproyecto1

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.miniproyecto1.databinding.HomeBinding
import kotlin.random.Random


class HomeActivity : AppCompatActivity() {
    lateinit var binding: HomeBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayerGiro: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home)

        setupToolbar()
        flashingButton()
        controllerSound()
        giroBotella()


    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mediaPlayerGiro.release()
    }

    private fun setupToolbar() {
        val toolbar = binding.contentToolbar.toolbar
        binding.contentToolbar.toolbar.title = ""
        setSupportActionBar(toolbar)

        rateApp()
        shareApp()

        val btnShare: ImageView = binding.contentToolbar.btnShare
        val btnReglas: ImageView = binding.contentToolbar.btnReglas
        val btnRetos: ImageView = binding.contentToolbar.btnRetos


        setTouchAnimation(btnShare)
        //setTouchAnimation(btnReglas)
        setTouchAnimation(btnRetos)



        btnReglas.setOnClickListener {
            setTouchAnimation(btnReglas)

            btnReglas.postDelayed({
                val intent = Intent(this, Reglas::class.java)
                startActivity(intent)
            }, 200)
        }


    }

   private fun setTouchAnimation(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.animate()
                        .scaleX(0.9f)
                        .scaleY(0.9f)
                        .setDuration(100)
                        .start()

                    v.performClick()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }
            }
            false
        }
    }

    private fun shareApp(){
        val botonCompartir = binding.contentToolbar.btnShare

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

    private fun rateApp() {
        val btnRate: ImageView = binding.contentToolbar.btnRate
        btnRate.setOnClickListener {
            setTouchAnimation(btnRate)
            btnRate.postDelayed({
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es")
                    )
                )
            }, 200)
        }
    }

    private fun controllerSound(){
        val btnSound : ImageView = binding.contentToolbar.btnSound
        var isSoundOn = true

        mediaPlayer = MediaPlayer.create(this, R.raw.supermariobros)

        mediaPlayer.isLooping = true
        mediaPlayer.start()

        btnSound.setOnClickListener {
            //setTouchAnimation(btnSound)
            if (isSoundOn) {
                btnSound.setImageResource(R.drawable.volume_off_24px)
                mediaPlayer.pause()
            } else {
                btnSound.setImageResource(R.drawable.volume_up_24px)
                mediaPlayer.start()
            }
            isSoundOn = !isSoundOn
        }
    }

    private fun flashingButton(){
        val flashingButton: ImageButton = binding.flashingButton
        val animacion = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.blink)

        flashingButton.startAnimation(animacion)
    }

    private var lastAngle = 0f

    private fun giroBotella() {
        val bottle: ImageView = binding.imgBotella
        val btn: ImageButton = binding.flashingButton
        val tvCounter: TextView = binding.tvCounter

        btn.setOnClickListener{
            val randomAngle = Random.nextInt(360, 721) // Rango de 360 a 720 grados para un giro completo
            val newAngle = lastAngle + randomAngle
            val animator = ObjectAnimator.ofFloat(bottle, "rotation", lastAngle, newAngle)

            lastAngle = newAngle % 360

            btn.clearAnimation()
            btn.visibility = View.INVISIBLE

            animator.duration = 3000

            var musicaOff = false
            // Pausa la música de fondo
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                musicaOff = true
            }

            mediaPlayerGiro = MediaPlayer.create(this, R.raw.sonidobotella3seg)
            mediaPlayerGiro.start()

            // Temporizador regresivo que actualiza el TextView cada segundo
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    tvCounter.text = (millisUntilFinished / 1000).toString()

                }

                override fun onFinish() {

                    tvCounter.text = "0"

                    mediaPlayerGiro.stop()
                    mediaPlayerGiro.release()
                    if (musicaOff === true){
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                    }


                    btn.visibility = View.VISIBLE
                    flashingButton()

                }
            }.start()

            // posición final
            animator.start()

        }


    }
}