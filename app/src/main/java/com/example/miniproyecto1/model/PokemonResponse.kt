package com.example.miniproyecto1.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("pokemon") val pokemon: List<Pokemon>
)
