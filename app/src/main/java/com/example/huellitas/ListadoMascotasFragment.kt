package com.example.huellitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.huellitas.viewmodel.MascotaViewModel

class ListadoMascotasFragment : Fragment() {

    private lateinit var mascotaViewModel: MascotaViewModel
    private lateinit var recyclerViewMascotas: RecyclerView
    private lateinit var tvListaVacia: TextView
    private lateinit var mascotasAdapter: MascotasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listado_mascotas, container, false)

        recyclerViewMascotas = view.findViewById(R.id.recyclerViewMascotas)
        tvListaVacia = view.findViewById(R.id.tvListaVacia)

        mascotasAdapter = MascotasAdapter(requireContext()) { mascotaSeleccionada ->

            val bundle = bundleOf("mascota" to mascotaSeleccionada)

            findNavController().navigate(R.id.nav_cargar, bundle)
        }

        recyclerViewMascotas.adapter = mascotasAdapter
        recyclerViewMascotas.layoutManager = LinearLayoutManager(requireContext())

        mascotaViewModel = ViewModelProvider(this).get(MascotaViewModel::class.java)

        mascotaViewModel.allMascotas.observe(viewLifecycleOwner) { mascotas ->
            mascotasAdapter.setMascotas(mascotas)

            if (mascotas.isEmpty()) {
                recyclerViewMascotas.visibility = View.GONE
                tvListaVacia.visibility = View.VISIBLE
            } else {
                recyclerViewMascotas.visibility = View.VISIBLE
                tvListaVacia.visibility = View.GONE
            }
        }

        return view
    }
}