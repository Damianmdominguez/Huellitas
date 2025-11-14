package com.example.huellitas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment // Importamos Fragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        loadNavHeaderData()

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        if (savedInstanceState == null) {
            cargarFragmento(ListadoMascotasFragment(), getString(R.string.menu_listado))
            navigationView.setCheckedItem(R.id.nav_listado)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_listado -> {

                cargarFragmento(ListadoMascotasFragment(), getString(R.string.menu_listado))
            }

            R.id.nav_cargar -> {
                cargarFragmento(CargaMascotaFragment(), getString(R.string.menu_cargar))
            }

            R.id.nav_salir -> {
                cerrarSesion()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun cargarFragmento(fragmento: Fragment, titulo: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmento)
            .addToBackStack(null) // Permite volver atrás entre fragmentos
            .commit()
        toolbar.title = titulo // Actualizamos el título del Toolbar
    }

    private fun cerrarSesion() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun loadNavHeaderData() {
        val headerView = navigationView.getHeaderView(0)
        val tvHeaderEmail = headerView.findViewById<TextView>(R.id.tvHeaderEmail)
        val prefs = getSharedPreferences(RegistroActivity.PREFS_NAME_USUARIO, Context.MODE_PRIVATE)
        val email = prefs.getString("LOGGED_USER_EMAIL", "usuario@ejemplo.com")
        tvHeaderEmail.text = email
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
                navigationView.setCheckedItem(R.id.nav_listado)
                toolbar.title = getString(R.string.menu_listado)
            } else {
                super.onBackPressed()
                finish()
            }
        }
    }
}