package com.example.miniproyecto1.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository { // Implementa la interfaz AuthRepository

    override fun login(email: String, password: String): Task<AuthResult> {
        return authRemoteDataSource.login(email, password)
    }

    override fun register(email: String, password: String): Task<AuthResult> {
        return authRemoteDataSource.register(email, password)
    }
}