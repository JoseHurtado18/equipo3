package com.example.miniproyecto1.model

data class Pokemon (
    val id: Int,
    val num: String,
    val name:String,
    val img:String,
    val type:List<String>,
    val height:String,
    val wight:String,
    val candy:String,
    val candyCount: Int? = null,
    val egg: String? = null,
    val spawnChance: Double? = null,
    val avg_spawns: Double? = null,
    val spawnTime: String? = null,
    val multipliers: List<Double>? = null,
    val weaknesses: List<String>? = null,
    val prevEvolution: List<Evolution>? = null,
    val nextEvolution: List<Evolution>? = null


)

data class Evolution(
    val num: String,
    val name: String
)