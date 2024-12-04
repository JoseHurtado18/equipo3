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
        viewModelScope.launch {
            delay(5000) // 5 segundos de retraso
            onTimerFinished()
        }
    }
}