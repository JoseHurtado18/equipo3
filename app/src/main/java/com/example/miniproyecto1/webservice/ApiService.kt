package com.example.miniproyecto1.webservice

import com.example.miniproyecto1.model.Pokemon
import com.example.miniproyecto1.utils.Constans
import retrofit2.http.GET

interface ApiService {
    @GET(Constans.END_POINT)
    suspend fun getPokemons(): MutableList<Pokemon>
}