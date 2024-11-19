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
            retos.value = retos.value?.toMutableList()?.apply {
                remove(reto)
            }
        }, onFailure = {
            Log.e("DeleteReto", "Error al eliminar el reto: ${reto.descripcion}")
        })
    }

    fun updateReto(reto: Reto, descripcion: String){
        val updatedReto = reto.copy(descripcion = descripcion)
        retosRepository.updateReto(reto, descripcion, onSuccess = {
            retos.value = retos.value?.toMutableList()?.apply {

                val index = indexOfFirst { it.id == updatedReto.id }
                if (index != -1) {
                    this[index] = updatedReto

                }

            }
        }, onFailure = {
                error ->

            Log.e("RetosViewModel", "Error al actualizar el reto", error)

        })
    }

    fun getRetos(){
        retosRepository.getRetos(onSuccess = {
            retos.value = it
        }, onFailure = {})
    }
}