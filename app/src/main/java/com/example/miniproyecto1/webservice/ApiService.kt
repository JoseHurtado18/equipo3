package com.example.miniproyecto1.webservice

import com.example.miniproyecto1.model.Pokemon
import retrofit2.http.GET

interface ApiService {
    @GET("pokedex.json")
    suspend fun getPokemons(): MutableList<Pokemon>
}