package com.example.miniproyecto1.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("num")
    val num: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("type")
    val type: List<String>,
    @SerializedName("height")
    val height: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("candy")
    val candy: String,
    @SerializedName("candy_count")
    val candyCount: Int? = null,
    @SerializedName("egg")
    val egg: String? = null,
    @SerializedName("spawn_chance")
    val spawnChance: Double? = null,
    @SerializedName("avg_spawns")
    val avgSpawns: Double? = null,
    @SerializedName("spawn_time")
    val spawnTime: String? = null,
    @SerializedName("multipliers")
    val multipliers: List<Double>? = null,
    @SerializedName("weaknesses")
    val weaknesses: List<String>? = null,
    @SerializedName("prev_evolution")
    val prevEvolution: List<Evolution>? = null,
    @SerializedName("next_evolution")
    val nextEvolution: List<Evolution>? = null
)

data class Evolution(
    @SerializedName("num")
    val num: String,
    @SerializedName("name")
    val name: String
)