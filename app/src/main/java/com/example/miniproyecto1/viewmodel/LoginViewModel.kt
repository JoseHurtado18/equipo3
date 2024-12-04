package com.example.miniproyecto1.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.miniproyecto1.model.UserRequest
import com.example.miniproyecto1.model.UserResponse
import com.example.miniproyecto1.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
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

    private val _isRegister = MutableLiveData<UserResponse>()
    val isRegister: LiveData<UserResponse> = _isRegister


    fun registerUser(userRequest: UserRequest) {
        loginRepository.registerUser(userRequest) { userResponse ->
            _isRegister.value = userResponse
        }
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
        } else {
            isLogin(false)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}