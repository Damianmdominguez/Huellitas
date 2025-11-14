package com.example.huellitas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
// Importamos nuestra entidad de datos (del Paso 2).
import com.example.huellitas.model.Usuario
import com.google.android.material.textfield.TextInputEditText

class RegistroActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME_USUARIO = "UsuarioPrefs" // Nombre del "archivo" de preferencias
        const val KEY_USUARIO = "user_name"
        const val KEY_CLAVE = "user_pass"
        const val KEY_EMAIL = "user_email"
    }

    private lateinit var etUsuario: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etClave: TextInputEditText
    private lateinit var btnRegistrar: Button
    private lateinit var tvIrALogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        etUsuario = findViewById(R.id.etUsuario)
        etEmail = findViewById(R.id.etEmail)
        etClave = findViewById(R.id.etClave)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        tvIrALogin = findViewById(R.id.tvIrALogin)

        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }

        tvIrALogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registrarUsuario() {
        val usuarioStr = etUsuario.text.toString().trim()
        val emailStr = etEmail.text.toString().trim()
        val claveStr = etClave.text.toString().trim()

        if (usuarioStr.isEmpty() || emailStr.isEmpty() || claveStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_error_campos_vacios), Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoUsuario = Usuario(usuarioStr, claveStr, emailStr)

        val prefs = getSharedPreferences(PREFS_NAME_USUARIO, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putString(KEY_USUARIO, nuevoUsuario.usuario)
        editor.putString(KEY_CLAVE, nuevoUsuario.clave)
        editor.putString(KEY_EMAIL, nuevoUsuario.email)

        editor.apply()

        Toast.makeText(this, getString(R.string.msg_registro_exitoso), Toast.LENGTH_LONG).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}