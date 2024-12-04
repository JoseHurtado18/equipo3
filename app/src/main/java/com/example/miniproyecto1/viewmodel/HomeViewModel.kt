package com.example.miniproyecto1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproyecto1.model.Pokemon
import com.example.miniproyecto1.model.Reto
import com.example.miniproyecto1.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: MutableLiveData<List<Pokemon>> = _pokemons

    private val _retos = MutableLiveData<MutableList<Reto>>()
    val retos: LiveData<MutableList<Reto>> = _retos

    fun randomPokemon(): Pokemon? {
        val pokemonsList = pokemons.value as? List<Pokemon>
        if (pokemonsList.isNullOrEmpty()) {
            Log.d("randomPokemon", "La lista de pokemons está vacía o es nula.")
            return null
        }
        val index = (0 until (pokemonsList.size ?: 0)).random()
        val selectedPokemon = pokemonsList[index]
        Log.d("randomPokemon", "Pokémon seleccionado: $selectedPokemon")
        return selectedPokemon
    }

    fun randomReto(): Reto? {
        val retosList = retos.value
        if (retosList.isNullOrEmpty()) {
            return null
        }
        var index = (0 until (retosList?.size ?: 0)).random()
        val reto = retos.value?.get(index)
        return reto
    }

    fun getPokemons() {
        viewModelScope.launch {
            _pokemons.value = homeRepository.getPokemons()
        }
    }

    fun getRetos() {
        homeRepository.getRetos(onSuccess = {
            _retos.value = it
        }, onFailure = {})
    }

}