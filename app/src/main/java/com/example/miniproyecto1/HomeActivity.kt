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

        val btnShare: ImageView = binding.contentToolbar.btnShare
        val btnReglas: ImageView = binding.contentToolbar.btnReglas
        val btnRetos: ImageView = binding.contentToolbar.btnRetos
        val btnSound: ImageView = binding.contentToolbar.btnSound

        setTouchAnimation(btnShare)
        setTouchAnimation(btnReglas)
        setTouchAnimation(btnRetos)
        setTouchAnimation(btnSound)

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

        mediaPlayer.isLooping = true // Repetir audio
        mediaPlayer.start()

        btnSound.setOnClickListener {
            if (isSoundOn) {
                btnSound.setImageResource(R.drawable.volume_off_24px)
                mediaPlayer.stop()
                mediaPlayer.prepare()
            } else {
                btnSound.setImageResource(R.drawable.volume_up_24px)
                mediaPlayer.start()
            }
            isSoundOn = !isSoundOn // Alterna el estado
        }
    }

    private fun flashingButton(){
        val flashingButton: ImageButton = binding.flashingButton
        val animacion = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.blink)

        flashingButton.startAnimation(animacion)
    }

    private var lastAngle = 0f // Ángulo de la última posición

    private fun giroBotella() {
        val bottle: ImageView = binding.imgBotella
        val btn: ImageButton = binding.flashingButton
        val tvCounter: TextView = binding.tvCounter

        btn.setOnClickListener{
            val randomAngle = Random.nextInt(360, 721) // Rango de 360 a 720 grados para un giro completo
            val newAngle = lastAngle + randomAngle
            val animator = ObjectAnimator.ofFloat(bottle, "rotation", lastAngle, newAngle)

            lastAngle = newAngle % 360 // Normalizamos a 360 grados para evitar valores muy altos

            btn.clearAnimation()
            btn.visibility = View.INVISIBLE

            // Duración de la animación de 3 segundos
            animator.duration = 3000

            // Pausa la música de fondo
            var musicaOff = false

            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                musicaOff = true
            }

            // Configura y reproduce el sonido del giro de la botella
            mediaPlayerGiro = MediaPlayer.create(this, R.raw.sonidobotella3seg)
            mediaPlayerGiro.start()

            // Temporizador regresivo que actualiza el TextView cada segundo
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Actualiza el TextView con los segundos restantes
                    tvCounter.text = (millisUntilFinished / 1000).toString()


                }

                override fun onFinish() {
                    // Una vez terminado el temporizador, el TextView muestra "0"
                    tvCounter.text = "0"

                    // Cuando termina el giro, reanuda la música de fondo
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

            // Cuando la animación termine, se puede fijar la posición final
            animator.start()

        }


    }
}