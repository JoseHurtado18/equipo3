package com.example.miniproyecto1.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miniproyecto1.model.Reto
import com.example.miniproyecto1.repository.RetosRepository

class RetosViewModel : ViewModel() {

    private val retosRepository = RetosRepository()
    //val  retos = MutableLiveData<List<Reto>>()
    val retos = MutableLiveData<List<Reto>>().apply { value = emptyList() }


    fun addReto(reto: Reto){
        retosRepository.addReto(reto,
            onSuccess = {
                retos.value = retos.value?.toMutableList()?.apply { add(reto) }
        },
            onFailure = {})

    }

    fun deleteReto(reto: Reto){
        retosRepository.deleteReto(reto, onSuccess = {
            retos.value = retos.value?.filterNot { it.id == reto.id }
        }, onFailure = {})}

    fun updateReto(reto: Reto){
        retosRepository.updateReto(reto, onSuccess = {
            retos.value = retos.value?.toMutableList()?.apply {
                val index = indexOfFirst { it.id == reto.id }
                if (index != -1) {
                    this[index] = reto
                } else {
                    // Si el reto no se encuentra en la lista, puedes agregar un nuevo elemento
                    add(reto)
                }
            }
        }, onFailure = {
                error ->
            // Manejar el error, por ejemplo, mostrar un mensaje al usuario
            Log.e("RetosViewModel", "Error al actualizar el reto", error)
            // Puedes emitir un evento para notificar a la vista sobre el error
        })
    }

    fun getRetos(){
        retosRepository.getRetos(onSuccess = {
            retos.value = it
        }, onFailure = {})
    }
}