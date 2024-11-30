package com.example.miniproyecto1.view



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
import com.example.miniproyecto1.viewmodel.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            setContentView(R.layout.activity_aut)

            // Email Input
            val emailEditText = findViewById<TextInputEditText>(R.id.email_edit_text)
            emailEditText.addTextChangedListener { editable ->
                viewModel.email.value = editable.toString()
            }

            // Password Input
            val passwordEditText = findViewById<TextInputEditText>(R.id.password_edit_text)
            passwordEditText.addTextChangedListener { editable ->
                viewModel.password.value = editable.toString()
            }
            val passwordTextInputLayout = findViewById<TextInputLayout>(R.id.password_input_layout)

            // Password Error Message
            val passwordErrorMessage = findViewById<TextView>(R.id.password_error_message)

            // Login Button
            val loginButton = findViewById<MaterialButton>(R.id.login_button)
            loginButton.setOnClickListener { viewModel.login() }

            // Register Text Button
            val registerTextButton = findViewById<TextView>(R.id.register_text_button)
            registerTextButton.setOnClickListener { viewModel.register() }

            // Observa los cambios en el ViewModel
            viewModel.isPasswordValid.observe(this) { isValid ->
                passwordTextInputLayout.boxStrokeColor = if (isValid)
                    ContextCompat.getColor(this, R.color.white)
                else
                    ContextCompat.getColor(this, R.color.red)
                passwordErrorMessage.visibility = if (isValid) View.GONE else View.VISIBLE
            }

            viewModel.isLoginButtonEnabled.observe(this) { isEnabled ->
                loginButton.isEnabled = isEnabled
            }

            viewModel.isRegisterButtonEnabled.observe(this) { isEnabled ->
                registerTextButton.isEnabled = isEnabled
            }

            viewModel.loginEvent.observe(this) { success ->
                if (success) {
                    // Navega a la pantalla de inicio
                    // ...
                } else {
                    // Muestra un mensaje de error
                    Toast.makeText(this, "Inicio de sesión incorrecto", Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.registerEvent.observe(this) { success ->
                if (success) {
                    // Navega a la pantalla de inicio
                    // ...
                } else {
                    // Muestra un mensaje de error
                    Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                }
            }


            viewModel.error.observe(this) { errorMessage ->
                if (errorMessage != null) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    viewModel._error.value = null // Limpia el mensaje de error después de mostrarlo
                }
            }
        } catch (e: Exception) {
            Log.e("AuthActivity", "Error: ${e.message}", e)
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            Toast.makeText(this, "Ocurrió un error al iniciar la actividad", Toast.LENGTH_SHORT).show()
        }
    }
}