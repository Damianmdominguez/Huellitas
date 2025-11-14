package com.example.huellitas.model

import java.util.UUID


data class Usuario(
    val usuario: String,
    val clave: String,
    val email: String
)


data class Mascota(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val especie: String, // "Perro", "Gato", "Otro" (Spinner)
    val estado: String,  // "Perdido", "Encontrado", "En Adopción" (Spinner)
    val descripcion: String,
    val contacto: String,
    val zona: String,
    val castrado: Boolean
)