package com.example.miniproyecto1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miniproyecto1.model.Reto
import com.example.miniproyecto1.repository.RetosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RetosViewModel @Inject constructor(
    private val retosRepository: RetosRepository,
) : ViewModel() {


    //val  retos = MutableLiveData<List<Reto>>()
    private val _retos = MutableLiveData<MutableList<Reto>>()
    val retos: LiveData<MutableList<Reto>> = _retos

    val retoString = MutableLiveData<String>().apply {
        value = ""
    }

    var isGuardarButtonEnabled = MediatorLiveData<Boolean>().apply {
        fun updateEnabled() {
            if (retoString.value.isNullOrEmpty()) {
                val isButtonEnabled = true
            }
        }
        addSource(retoString) { updateEnabled() }

    }


    fun addReto(reto: Reto){
        retosRepository.addReto(reto,
            onSuccess = {
                _retos.value = retos.value?.toMutableList()?.apply { add(reto) }
        },
            onFailure = {})

    }

    fun deleteReto(reto: Reto){
        retosRepository.deleteReto(reto, onSuccess = {
            _retos.value = retos.value?.toMutableList()?.apply {
                remove(reto)
            }
        }, onFailure = {
            Log.e("DeleteReto", "Error al eliminar el reto: ${reto.descripcion}")
        })
    }

    fun updateReto(reto: Reto, descripcion: String){
        val updatedReto = reto.copy(descripcion = descripcion)
        retosRepository.updateReto(reto, descripcion, onSuccess = {
            _retos.value = retos.value?.toMutableList()?.apply {

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
            _retos.value = it
        }, onFailure = {})
    }
}