package com.example.huellitas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsuarioLogin: TextInputEditText
    private lateinit var etClaveLogin: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var tvIrARegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsuarioLogin = findViewById(R.id.etUsuarioLogin)
        etClaveLogin = findViewById(R.id.etClaveLogin)
        btnLogin = findViewById(R.id.btnLogin)
        tvIrARegistro = findViewById(R.id.tvIrARegistro)

        btnLogin.setOnClickListener {
            validarLogin()
        }

        tvIrARegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validarLogin() {
        val usuarioStr = etUsuarioLogin.text.toString().trim()
        val claveStr = etClaveLogin.text.toString().trim()

        if (usuarioStr.isEmpty() || claveStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_error_campos_vacios), Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = getSharedPreferences(RegistroActivity.PREFS_NAME_USUARIO, Context.MODE_PRIVATE)
        val usuarioGuardado = prefs.getString(RegistroActivity.KEY_USUARIO, null)
        val claveGuardada = prefs.getString(RegistroActivity.KEY_CLAVE, null)
        val emailGuardado = prefs.getString(RegistroActivity.KEY_EMAIL, "usuario@ejemplo.com")

        if (usuarioGuardado == null) {
            Toast.makeText(this, getString(R.string.msg_error_usuario_no_existe), Toast.LENGTH_SHORT).show()
            return
        }

        if (usuarioStr == usuarioGuardado && claveStr == claveGuardada) {

            prefs.edit().putString("LOGGED_USER_EMAIL", emailGuardado).apply()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.msg_error_credenciales), Toast.LENGTH_SHORT).show()
        }
    }
}