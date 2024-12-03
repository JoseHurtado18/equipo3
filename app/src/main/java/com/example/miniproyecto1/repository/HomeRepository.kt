package com.example.miniproyecto1.repository

import com.example.miniproyecto1.model.Pokemon
import com.example.miniproyecto1.model.Reto
import com.example.miniproyecto1.webservice.ApiService
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val apiService: ApiService,
) {

    private val retosCollection = firestore.collection("retos")

    suspend fun getPokemons() : List<Pokemon>{
        return  apiService.getPokemons()
    }

    fun getRetos(onSuccess: (MutableList<Reto>) -> Unit, onFailure: (Exception) -> Unit) {
        retosCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val retosList = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Reto::class.java)
                }.toMutableList()
                onSuccess(retosList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}