package com.example.miniproyecto1.repository



import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult


interface AuthRepository {
    fun login(email: String,password: String): Task<AuthResult>
    fun register(email: String, password: String): Task<AuthResult>
}
