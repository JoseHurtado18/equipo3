package com.example.miniproyecto1.view



import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.miniproyecto1.R
import com.example.miniproyecto1.model.UserRequest
import com.example.miniproyecto1.viewmodel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
        viewModelObservers()


        try{
            setContentView(R.layout.activity_aut)

            // Email Input
            val emailEditText = findViewById<TextInputEditText>(R.id.email_edit_text)
            emailEditText.addTextChangedListener { editable ->
                loginViewModel.email.value = editable.toString()
            }

            // Password Input
            val passwordEditText = findViewById<TextInputEditText>(R.id.password_edit_text)
            passwordEditText.addTextChangedListener { editable ->
                loginViewModel.password.value = editable.toString()
            }
            val passwordTextInputLayout = findViewById<TextInputLayout>(R.id.password_input_layout)

            // Password Error Message
            val passwordErrorMessage = findViewById<TextView>(R.id.password_error_message)

            // Login Button
            val loginButton = findViewById<MaterialButton>(R.id.login_button)
            loginButton.setOnClickListener { loginUser() }

            // Register Text Button
            val registerTextButton = findViewById<TextView>(R.id.register_text_button)
            registerTextButton.setOnClickListener { registerUser() }

            // Observa los cambios en el ViewModel
            loginViewModel.isPasswordValid.observe(this) { isValid ->
                passwordTextInputLayout.boxStrokeColor = if (isValid)
                    ContextCompat.getColor(this, R.color.white)
                else
                    ContextCompat.getColor(this, R.color.red)
                passwordErrorMessage.visibility = if (isValid) View.GONE else View.VISIBLE
            }

            loginViewModel.isLoginButtonEnabled.observe(this) { isEnabled ->
                loginButton.isEnabled = isEnabled
            }

            loginViewModel.isRegisterButtonEnabled.observe(this) { isEnabled ->
                registerTextButton.isEnabled = isEnabled
            }

            loginViewModel.loginEvent.observe(this) { success ->
                if (success) {
                    // Navega a la pantalla de inicio
                    // ...
                } else {
                    // Muestra un mensaje de error
                    Toast.makeText(this, "Inicio de sesión incorrecto", Toast.LENGTH_SHORT).show()
                }
            }

            loginViewModel.registerEvent.observe(this) { success ->
                if (success) {
                    // Navega a la pantalla de inicio
                    // ...
                } else {
                    // Muestra un mensaje de error
                    Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                }
            }


            loginViewModel.error.observe(this) { errorMessage ->
                if (errorMessage != null) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    loginViewModel._error.value = null // Limpia el mensaje de error después de mostrarlo
                }
            }
        } catch (e: Exception) {
            Log.e("AuthActivity", "Error: ${e.message}", e)
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            Toast.makeText(this, "Ocurrió un error al iniciar la actividad", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewModelObservers() {
        observerIsRegister()
    }
    private fun observerIsRegister() {
        loginViewModel.isRegister.observe(this) { userResponse ->
            if (userResponse.isRegister) {
                Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putString("email",userResponse.email).apply()
                goToHome()
            } else {
                Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome(){
        val intent = Intent (this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerUser() {
        val email = findViewById<TextInputEditText>(R.id.email_edit_text).text.toString().trim()
        val pass = findViewById<TextInputEditText>(R.id.password_edit_text).text.toString().trim()
        val userRequest = UserRequest(email, pass)

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            loginViewModel.registerUser(userRequest)
        } else {
            Toast.makeText(this, "Campos Vacíos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loginUser(){
        val email = findViewById<TextInputEditText>(R.id.email_edit_text).text.toString().trim()
        val pass = findViewById<TextInputEditText>(R.id.password_edit_text).text.toString().trim()
        loginViewModel.loginUser(email,pass){ isLogin ->
            if (isLogin){
                sharedPreferences.edit().putString("email",email).apply()
                goToHome()
            }else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

}