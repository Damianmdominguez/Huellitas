package com.example.huellitas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.huellitas.model.Mascota // Importamos nuestro Objeto Core (Paso 2)
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson // Importamos Gson (Paso 0)
import com.google.gson.reflect.TypeToken
import java.util.UUID // Para generar un ID único

class CargaMascotaFragment : Fragment() {

    companion object {
        const val PREFS_NAME_MASCOTAS = "MascotasPrefs"
        const val KEY_MASCOTAS_LISTA = "mascotas_lista"
    }

    private lateinit var etNombreMascota: TextInputEditText
    private lateinit var spinnerEspecie: Spinner
    private lateinit var spinnerEstado: Spinner
    private lateinit var etDescripcion: TextInputEditText
    private lateinit var etZona: TextInputEditText
    private lateinit var etContacto: TextInputEditText
    private lateinit var chkCastrado: CheckBox
    private lateinit var btnGuardar: Button

    private val gson = Gson()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carga_mascota, container, false)

        etNombreMascota = view.findViewById(R.id.etNombreMascota)
        spinnerEspecie = view.findViewById(R.id.spinnerEspecie)
        spinnerEstado = view.findViewById(R.id.spinnerEstado)
        etDescripcion = view.findViewById(R.id.etDescripcion)
        etZona = view.findViewById(R.id.etZona)
        etContacto = view.findViewById(R.id.etContacto)
        chkCastrado = view.findViewById(R.id.chkCastrado)
        btnGuardar = view.findViewById(R.id.btnGuardar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnGuardar.setOnClickListener {
            guardarMascota()
        }
    }

    private fun guardarMascota() {
        val nombre = etNombreMascota.text.toString().trim()
        val descripcion = etDescripcion.text.toString().trim()
        val zona = etZona.text.toString().trim()
        val contacto = etContacto.text.toString().trim()

        if (descripcion.isEmpty() || zona.isEmpty() || contacto.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.msg_error_campos_vacios), Toast.LENGTH_SHORT).show()
            return
        }

        val nuevaMascota = Mascota(
            id = UUID.randomUUID().toString(),
            nombre = nombre.ifEmpty { "Sin nombre" },
            especie = spinnerEspecie.selectedItem.toString(),
            estado = spinnerEstado.selectedItem.toString(),
            descripcion = descripcion,
            contacto = contacto,
            zona = zona,
            castrado = chkCastrado.isChecked //
        )


        persistirMascota(nuevaMascota)

        Toast.makeText(requireContext(), getString(R.string.msg_mascota_guardada), Toast.LENGTH_LONG).show()

        etNombreMascota.text?.clear()
        etDescripcion.text?.clear()
        etZona.text?.clear()
        etContacto.text?.clear()
        chkCastrado.isChecked = false
        spinnerEspecie.setSelection(0)
        spinnerEstado.setSelection(0)
    }


    private fun persistirMascota(mascota: Mascota) {
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME_MASCOTAS, Context.MODE_PRIVATE)

        val jsonListaActual = prefs.getString(KEY_MASCOTAS_LISTA, null)

        val listaMascotas: MutableList<Mascota>

        if (jsonListaActual == null) {
            listaMascotas = mutableListOf()
        } else {
            val tipoLista = object : TypeToken<MutableList<Mascota>>() {}.type
            listaMascotas = gson.fromJson(jsonListaActual, tipoLista)
        }

        listaMascotas.add(0, mascota)

        val jsonListaNueva = gson.toJson(listaMascotas)

        prefs.edit().putString(KEY_MASCOTAS_LISTA, jsonListaNueva).apply()
    }
}