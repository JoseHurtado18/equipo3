package com.example.miniproyecto1.viewmodel

import androidx.lifecycle.LiveData
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
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: MutableLiveData<List<Pokemon>> = _pokemons

    private val _retos = MutableLiveData<MutableList<Reto>>()
    val retos: LiveData<MutableList<Reto>> = _retos

    fun randomPokemon(): Pokemon? {
        val pokemonsList = pokemons.value
        var index = (0 until (pokemonsList?.size ?: 0)).random()
        val pokemon = pokemons.value?.get(index)
        return pokemon
    }

    fun randomReto(): Reto? {
        val retosList = retos.value
        var index = (0 until (retosList?.size ?: 0)).random()
        val reto = retos.value?.get(index)
        return reto
    }

    fun getPokemons(){
        viewModelScope.launch{
            _pokemons.value = homeRepository.getPokemons()
        }
    }

    fun getRetos(){
        homeRepository.getRetos(onSuccess = {
            _retos.value = it
        }, onFailure = {})
    }
}