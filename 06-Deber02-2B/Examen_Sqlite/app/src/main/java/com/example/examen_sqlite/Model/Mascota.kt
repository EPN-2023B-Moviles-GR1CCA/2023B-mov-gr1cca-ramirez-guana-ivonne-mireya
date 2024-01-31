package com.example.examen_sqlite.model

class Mascota (
    var idMascota: String,
    var nombre: String,
    var especie: String,
    var nivelAdiestramiento: Int,
    var fechaNacimiento: String,
    var frecuenciaCardiaca: Double,
    var estaEsterilizado: Boolean
) {

    fun spinnerEstaEsterilizado(estaEsterilizado: Boolean): String{
        return if(estaEsterilizado) "Si" else "No"
    }

    override fun toString(): String {
        return "\nidMascota: $idMascota " +
                "\nNombre/Apodo Mascota $nombre - $especie. " +
                "\nNivel Adiestramiento: $nivelAdiestramiento" +
                "\nFecha Nacimiento: $fechaNacimiento " +
                "\nFrecuencia Cardiaca: $frecuenciaCardiaca" +
                "\nEsta Esterilizado: ${spinnerEstaEsterilizado(estaEsterilizado)}\n"
    }
}