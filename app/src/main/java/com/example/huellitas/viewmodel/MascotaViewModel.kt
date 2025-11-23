package com.example.huellitas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.huellitas.database.HuellitasDatabase
import com.example.huellitas.model.Mascota
import com.example.huellitas.repository.MascotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MascotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MascotaRepository

    val allMascotas: LiveData<List<Mascota>>

    init {
        val mascotaDao = HuellitasDatabase.getDatabase(application).mascotaDao()
        repository = MascotaRepository(mascotaDao)
        allMascotas = repository.allMascotas
    }

    fun insert(mascota: Mascota) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(mascota)
    }

    fun update(mascota: Mascota) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(mascota)
    }

    fun delete(mascota: Mascota) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(mascota)
    }
}