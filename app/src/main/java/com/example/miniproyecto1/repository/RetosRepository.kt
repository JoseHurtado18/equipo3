package com.example.miniproyecto1.repository

import com.example.miniproyecto1.model.Reto
import com.google.firebase.firestore.FirebaseFirestore

class RetosRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val retosCollection = firestore.collection("retos")

    // Agregar un reto a la colección
    fun addReto(reto: Reto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            retosCollection.add(reto)
                .addOnSuccessListener {
                    onSuccess()  // Llamada cuando el reto se ha agregado exitosamente
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
        if (reto.id != null) {
            retosCollection.document(reto.toString()).delete()
                .addOnSuccessListener {
                    onSuccess()  // Llamada cuando el reto se ha eliminado exitosamente
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)  // Llamada en caso de error
                }
        } else {
            onFailure(Exception("El reto no tiene un ID válido"))
        }
    }

    // Actualizar un reto existente por su ID
    fun updateReto(reto: Reto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (reto.id != null) {
            retosCollection.document(reto.toString()).set(reto)
                .addOnSuccessListener {
                    onSuccess()  // Llamada cuando el reto se ha actualizado exitosamente
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)  // Llamada en caso de error
                }
        } else {
            onFailure(Exception("El reto no tiene un ID válido"))
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
