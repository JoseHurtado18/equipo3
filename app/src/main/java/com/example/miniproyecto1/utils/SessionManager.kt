package com.example.miniproyecto1.utils

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveSession(isLoggedIn: Boolean, userId: String) {
        val editor = prefs.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.putString("userId", userId)
        editor.apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun getUserId(): String? = prefs.getString("userId", null)
}
