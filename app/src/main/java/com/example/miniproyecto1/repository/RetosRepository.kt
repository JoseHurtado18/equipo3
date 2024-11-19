package com.example.miniproyecto1.repository

import android.util.Log
import com.example.miniproyecto1.model.Reto
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class RetosRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val retosCollection = firestore.collection("retos")

    // Agregar un reto a la colección
    fun addReto(reto: Reto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        //Const id = Firebase.firestore().collection("menu").doc().id
        val retoId = UUID.randomUUID().toString()
        reto.id = retoId
        try {
            retosCollection.document(retoId).set(reto)
                .addOnSuccessListener {
                    Log.d("RetosRepository", "Reto agregado con exito: ${reto.descripcion} ID: ${reto.id}")
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)  // Llamada en caso de error
                }
        } catch (e: Exception) {
            onFailure(e)  // Llamada si ocurre un error al agregar el reto
        }
    }

    // Eliminar un reto por su ID
    fun deleteReto(reto: Reto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
            retosCollection.document(reto.id).delete()
                .addOnSuccessListener {
                    onSuccess()  // Llamada cuando el reto se ha eliminado exitosamente
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)  // Llamada en caso de error
                }
        }


    // Actualizar un reto existente por su ID
    fun updateReto(reto: Reto, newDes: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {

            val reto = retosCollection.document(reto.id)
            reto.update("descripcion", newDes)
                .addOnSuccessListener {
                    Log.d("RetosRepository", "Reto actualizado con exito: ${newDes}")
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }

    }

    // Obtener todos los retos de la colección
    fun getRetos(onSuccess: (List<Reto>) -> Unit, onFailure: (Exception) -> Unit) {
        retosCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val retosList = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Reto::class.java)  // Convierte cada documento a un objeto Reto
                }
                onSuccess(retosList)  // Llamada cuando los retos se han obtenido exitosamente
            }
            .addOnFailureListener { exception ->
                onFailure(exception)  // Llamada en caso de error
            }
    }
}

