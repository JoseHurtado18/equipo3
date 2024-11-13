package com.example.miniproyecto1.model

data class Reto(
    val id: String? = null,
    var descripcion: String,
){
    constructor() : this("", "" )
}
