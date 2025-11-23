package com.example.huellitas.repository

import androidx.lifecycle.LiveData
import com.example.huellitas.database.MascotaDao
import com.example.huellitas.model.Mascota

class MascotaRepository(private val mascotaDao: MascotaDao) {

    val allMascotas: LiveData<List<Mascota>> = mascotaDao.obtenerTodas()

    suspend fun insert(mascota: Mascota) {
        mascotaDao.insertar(mascota)
    }

    suspend fun update(mascota: Mascota) {
        mascotaDao.actualizar(mascota)
    }

    suspend fun delete(mascota: Mascota) {
        mascotaDao.eliminar(mascota)
    }
}