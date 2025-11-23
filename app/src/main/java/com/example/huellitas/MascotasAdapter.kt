package com.example.huellitas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huellitas.model.Mascota

class MascotasAdapter(
    private val context: Context
) : RecyclerView.Adapter<MascotasAdapter.MascotaViewHolder>() {

    private var listaMascotas = emptyList<Mascota>()

    inner class MascotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreMascota)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvZona: TextView = view.findViewById(R.id.tvZona)
        val tvContacto: TextView = view.findViewById(R.id.tvContacto)
        val tvCastrado: TextView = view.findViewById(R.id.tvCastrado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val mascota = listaMascotas[position]

        holder.tvNombre.text = mascota.nombre
        holder.tvEstado.text = mascota.estado
        holder.tvDescripcion.text = mascota.descripcion

        holder.tvZona.text = context.getString(R.string.label_zona_prefijo, mascota.zona)
        holder.tvContacto.text = context.getString(R.string.label_contacto_prefijo, mascota.contacto)

        if (mascota.castrado) {
            holder.tvCastrado.visibility = View.VISIBLE
        } else {
            holder.tvCastrado.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listaMascotas.size
    }

    fun setMascotas(mascotas: List<Mascota>) {
        this.listaMascotas = mascotas
        notifyDataSetChanged()
    }
}