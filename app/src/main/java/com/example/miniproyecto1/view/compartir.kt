package com.example.miniproyecto1.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.miniproyecto1.viewmodel.CompartirViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class compartir : AppCompatActivity() {

    private val shareViewModel: CompartirViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shareViewModel.compartirApp(this)
        finish()
    }
}