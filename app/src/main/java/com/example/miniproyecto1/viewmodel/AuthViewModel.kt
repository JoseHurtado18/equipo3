package com.example.miniproyecto1.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.miniproyecto1.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val isPasswordValid = password.map { (it?.length ?: 0) in 6..10 }


    val isLoginButtonEnabled = MediatorLiveData<Boolean>().apply {
        fun updateEnabled() {
            value = email.value?.isNotEmpty() == true &&
                    password.value?.isNotEmpty() == true &&
                    isPasswordValid.value == true
        }

        addSource(email) { updateEnabled() }
        addSource(password) { updateEnabled() }
        addSource(isPasswordValid) { updateEnabled() }
    }

    val isRegisterButtonEnabled = MediatorLiveData<Boolean>().apply {
        fun updateEnabled() {
            value = email.value?.isNotEmpty() == true &&
                    password.value?.isNotEmpty() == true &&
                    isPasswordValid.value == true}

        addSource(email) { updateEnabled() }
        addSource(password) { updateEnabled() }
        addSource(isPasswordValid) { updateEnabled() }
    }

    // LiveData para eventos de inicio de sesión y registro
    val loginEvent = MutableLiveData<Boolean>()
    val registerEvent = MutableLiveData<Boolean>()
    val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun login() {
        val emailValue = email.value
        val passwordValue = password.value

        if (emailValue != null && emailValue.isNotEmpty() && passwordValue != null && passwordValue.isNotEmpty()) {
            authRepository.login(emailValue, passwordValue).addOnSuccessListener {
                loginEvent.value = true // Inicio de sesión exitoso
            }
                .addOnFailureListener { exception: Exception->
                    loginEvent.value = false // Error al iniciar sesión
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            // El usuario no existe o está deshabilitado
                            _error.value = "El usuario no existe o está deshabilitado."
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            // Contraseña incorrecta
                            _error.value = "Contraseña incorrecta."
                        }
                        else ->{
                            // Otro error de autenticación
                            _error.value = "Error de autenticación: ${exception.message}"
                        }
                    }
                }
        } else {
            // Manejo de errores si el correo electrónico o la contraseña están vacíos
            _error.value = "El correo electrónico y la contraseña son obligatorios."
        }
    }

    fun register() {
        val emailValue = email.value
        val passwordValue = password.value

        if (emailValue != null && emailValue.isNotEmpty() && passwordValue != null && passwordValue.isNotEmpty()) {
            authRepository.register(emailValue, passwordValue)
                .addOnSuccessListener {
                    registerEvent.value = true // Registro exitoso
                }
                .addOnFailureListener { exception: Exception ->
                    registerEvent.value = false // Error al registrarse
                    when (exception) {
                        is FirebaseAuthWeakPasswordException -> {
                            // La contraseña es demasiado débil
                            _error.value = "La contraseña es demasiado débil. Debe tener al menos 6 caracteres."
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            // El correo electrónico no es válido
                            _error.value = "El correo electrónicono es válido."
                        }
                        is FirebaseAuthUserCollisionException -> {
                            // Ya existe una cuenta con este correo electrónico
                            _error.value = "Ya existe una cuenta con este correo electrónico."
                        }
                        else -> {
                            // Otro error de autenticación
                            _error.value = "Error de autenticación: ${exception.message}"
                        }
                    }
                }
        } else {
            // Manejo de errores si el correo electrónico o la contraseña están vacíos
            _error.value = "El correo electrónico y la contraseña son obligatorios."
        }
    }
}