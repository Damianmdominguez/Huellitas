package com.example.huellitas.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.huellitas.model.Mascota

@Dao
interface MascotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(mascota: Mascota)

    @Update
    suspend fun actualizar(mascota: Mascota)

    @Delete
    suspend fun eliminar(mascota: Mascota)

    @Query("SELECT * FROM tabla_mascotas ORDER BY id DESC")
    fun obtenerTodas(): LiveData<List<Mascota>>
}