package com.example.miniproyecto1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    fun startTimer(onTimerFinished: () -> Unit) {
        viewModelScope.launch { // Inicia una corrutina en el ámbito del ViewModel
            delay(5000) // 5 segundos de retraso
            onTimerFinished() // Llama a la función onTimerFinished cuando el temporizador finalice
        }
    }
}