package com.example.examen_sqlite.model

class Consulta (
    var id: String,
    var nHistorial: Int,
    var fechaConsulta: String,
    var esPresencial: Boolean,
    var motivo: String,
    var peso: Double,
    var recomendacion: String,
    var idMascota: String
){

    fun spinnerEsPresencial(esPresencial: Boolean): String{
        return if(esPresencial) "Si" else "No"
    }

    override fun toString(): String {
        return "\nid: $id" +
                "\nN°Historial $nHistorial" +
                "\nFecha Consulta $fechaConsulta " +
                "\nEs presencial: ${spinnerEsPresencial(esPresencial)}" +
                "\nMotivo: $motivo " +
                "\nPeso:$peso"+
                "\nRecomendación: $recomendacion "     }


}