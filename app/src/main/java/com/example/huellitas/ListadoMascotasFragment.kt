package com.example.huellitas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.huellitas.model.Mascota // Importamos el Core (Paso 2)
import com.google.gson.Gson // Importamos Gson (Paso 0)
import com.google.gson.reflect.TypeToken

class ListadoMascotasFragment : Fragment() {

    private lateinit var recyclerViewMascotas: RecyclerView
    private lateinit var tvListaVacia: TextView
    private lateinit var mascotasAdapter: MascotasAdapter
    private var listaMascotas: MutableList<Mascota> = mutableListOf()
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listado_mascotas, container, false)

        recyclerViewMascotas = view.findViewById(R.id.recyclerViewMascotas)
        tvListaVacia = view.findViewById(R.id.tvListaVacia)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarMascotasDesdePrefs()

        if (listaMascotas.isEmpty()) {
            recyclerViewMascotas.visibility = View.GONE
            tvListaVacia.visibility = View.VISIBLE
        } else {
            recyclerViewMascotas.visibility = View.VISIBLE
            tvListaVacia.visibility = View.GONE

            setupRecyclerView()
        }
    }

    private fun cargarMascotasDesdePrefs() {
        val prefs = requireActivity().getSharedPreferences(
            CargaMascotaFragment.PREFS_NAME_MASCOTAS,
            Context.MODE_PRIVATE
        )

        val jsonLista = prefs.getString(CargaMascotaFragment.KEY_MASCOTAS_LISTA, null)

        if (jsonLista != null) {
            val tipoLista = object : TypeToken<MutableList<Mascota>>() {}.type
            listaMascotas = gson.fromJson(jsonLista, tipoLista)
        }
    }

    private fun setupRecyclerView() {
        mascotasAdapter = MascotasAdapter(requireContext(), listaMascotas)

        recyclerViewMascotas.layoutManager = LinearLayoutManager(requireContext())

        recyclerViewMascotas.adapter = mascotasAdapter
    }
}