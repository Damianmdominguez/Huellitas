package com.example.huellitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.huellitas.model.Mascota
import com.example.huellitas.viewmodel.MascotaViewModel
import com.google.android.material.textfield.TextInputEditText

class CargaMascotaFragment : Fragment() {

    private lateinit var etNombre: TextInputEditText
    private lateinit var spinnerEspecie: Spinner
    private lateinit var spinnerEstado: Spinner
    private lateinit var etDescripcion: TextInputEditText
    private lateinit var etZona: AutoCompleteTextView
    private lateinit var etContacto: TextInputEditText
    private lateinit var chkCastrado: CheckBox
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button

    private lateinit var mascotaViewModel: MascotaViewModel

    private var mascotaAEditar: Mascota? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carga_mascota, container, false)

        etNombre = view.findViewById(R.id.etNombreMascota)
        spinnerEspecie = view.findViewById(R.id.spinnerEspecie)
        spinnerEstado = view.findViewById(R.id.spinnerEstado)
        etDescripcion = view.findViewById(R.id.etDescripcion)
        etZona = view.findViewById(R.id.etZona)
        etContacto = view.findViewById(R.id.etContacto)
        chkCastrado = view.findViewById(R.id.chkCastrado)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnEliminar = view.findViewById(R.id.btnEliminar)

        mascotaViewModel = ViewModelProvider(this).get(MascotaViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBarriosDropdown()

        arguments?.let { bundle ->

            mascotaAEditar = bundle.getSerializable("mascota") as Mascota?
        }

        if (mascotaAEditar != null) {
            configurarModoEdicion(mascotaAEditar!!)
        } else {
            btnGuardar.text = "Guardar Reporte"
            btnEliminar.visibility = View.GONE
        }

        btnGuardar.setOnClickListener {
            guardarOActualizarMascota()
        }

        btnEliminar.setOnClickListener {
            eliminarMascota()
        }
    }

    private fun configurarModoEdicion(mascota: Mascota) {
        btnGuardar.text = "Actualizar Mascota"
        btnEliminar.visibility = View.VISIBLE

        etNombre.setText(mascota.nombre)
        etDescripcion.setText(mascota.descripcion)
        etZona.setText(mascota.zona, false)
        etContacto.setText(mascota.contacto)
        chkCastrado.isChecked = mascota.castrado

        setSpinnerValue(spinnerEspecie, mascota.especie)
        setSpinnerValue(spinnerEstado, mascota.estado)
    }

    private fun setSpinnerValue(spinner: Spinner, value: String) {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == value) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun setupBarriosDropdown() {
        val barriosAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.barrios_caba,
            android.R.layout.simple_dropdown_item_1line
        )
        etZona.setAdapter(barriosAdapter)
    }

    private fun guardarOActualizarMascota() {
        val nombre = etNombre.text.toString().trim()
        val descripcion = etDescripcion.text.toString().trim()
        val zona = etZona.text.toString().trim()
        val contacto = etContacto.text.toString().trim()

        if (descripcion.isEmpty() || zona.isEmpty() || contacto.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.msg_error_campos_vacios), Toast.LENGTH_SHORT).show()
            return
        }

        if (mascotaAEditar == null) {
            val nuevaMascota = Mascota(
                nombre = nombre.ifEmpty { "Sin nombre" },
                especie = spinnerEspecie.selectedItem.toString(),
                estado = spinnerEstado.selectedItem.toString(),
                descripcion = descripcion,
                contacto = contacto,
                zona = zona,
                castrado = chkCastrado.isChecked
            )
            mascotaViewModel.insert(nuevaMascota)
            Toast.makeText(requireContext(), "Mascota creada con éxito", Toast.LENGTH_SHORT).show()

        } else {
            val mascotaEditada = Mascota(
                id = mascotaAEditar!!.id,
                nombre = nombre.ifEmpty { "Sin nombre" },
                especie = spinnerEspecie.selectedItem.toString(),
                estado = spinnerEstado.selectedItem.toString(),
                descripcion = descripcion,
                contacto = contacto,
                zona = zona,
                castrado = chkCastrado.isChecked
            )
            mascotaViewModel.update(mascotaEditada)
            Toast.makeText(requireContext(), "Mascota actualizada", Toast.LENGTH_SHORT).show()
        }

        findNavController().popBackStack()
    }

    private fun eliminarMascota() {
        if (mascotaAEditar != null) {
            mascotaViewModel.delete(mascotaAEditar!!)
            Toast.makeText(requireContext(), "Mascota eliminada", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}