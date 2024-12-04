package com.example.miniproyecto1.repository



import com.example.miniproyecto1.model.UserRequest
import com.example.miniproyecto1.model.UserResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject


class LoginRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {



    fun registerUser(userRequest: UserRequest, userResponse: (UserResponse) -> Unit) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(userRequest.email, userRequest.password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val email = task.result?.user?.email
                        userResponse(
                            UserResponse(
                                email = email,
                                isRegister = true,
                                message = "Registro Exitoso"
                            )
                        )
                    } else {
                        val error = task.exception
                        if (error is FirebaseAuthUserCollisionException) {
                            // Manejo específico cuando ya existe un mismo email registrado
                            userResponse(
                                UserResponse(
                                    isRegister = false,
                                    message = "El usuario ya existe"
                                )
                            )
                        } else {
                            // Manejo de otros errores
                            userResponse(
                                UserResponse(
                                    isRegister = false,
                                    message = "Error en el registro"
                                )
                            )
                        }
                    }
                }
        } catch (e: Exception) {
            // Manejo de excepciones generales
            userResponse(
                UserResponse(
                    isRegister = false,
                    message = e.message ?: "Error desconocido"
                )
            )
        }
    }
}
