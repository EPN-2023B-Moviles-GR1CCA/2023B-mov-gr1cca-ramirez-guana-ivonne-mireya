package com.example.examen_1b.model

import java.util.Date

class Mascota (
    var id: Int,
    var nombre: String?,
    var especie: String,
    var fechaNacimiento: Date,
    var frecuenciaCardiaca: Float,
    var estaEsterilizado: Boolean,
    var nivelAdiestramiento: Int,
) {

    fun checkEstaEstelirizado(estaEsterilizado :Boolean):String{
        return if (estaEsterilizado) "Si" else "No"
    }
    override fun toString(): String {
        return "\nMascota id=$id, \nNombre/Apodo:$nombre,\nEspecie:'$especie',\nFecha de Nacimiento:$fechaNacimiento, " +
                "\nFrecuencia Cardiaca:$frecuenciaCardiaca,\nEsta Esterilizado:$estaEsterilizado,\nNivel de Adiestramiento=$nivelAdiestramiento)"
    }
}