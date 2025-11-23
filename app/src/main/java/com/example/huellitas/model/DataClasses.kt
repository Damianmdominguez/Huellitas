package com.example.huellitas.model

import java.util.UUID
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class Usuario(
    val usuario: String,
    val clave: String,
    val email: String
)

@Entity(tableName = "tabla_mascotas")
data class Mascota(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "especie")
    val especie: String,

    @ColumnInfo(name = "estado")
    val estado: String,

    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "contacto")
    val contacto: String,

    @ColumnInfo(name = "zona")
    val zona: String,

    @ColumnInfo(name = "castrado")
    val castrado: Boolean,

    @ColumnInfo(name = "imagenUrl")
    val imagenUrl: String? = null
) : Serializable
