package com.example.examen_1b.Model


import android.content.ContentValues
import android.content.Context

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.examen_1b.BDD.BDD

class Consulta(
    var idConsulta: Int?,
    var nombreConsulta: String?,
    var motivo: String?,
    var peso: String?,
    var esPresencial: String?,
    var idMascota: Int,
    val context: Context?) {


    //init
    init{
        idConsulta
        nombreConsulta
        motivo
        peso
        esPresencial
        idMascota
        context
    }



    fun setidConsulta(idConsulta: Int){
        this.idConsulta = idConsulta
    }

    fun setnombreConsulta(nombreConsulta: String){
        this.nombreConsulta = nombreConsulta
    }


    fun setmotivo(motivo: String): Unit{
        this.motivo = motivo
    }

    fun setpeso(peso: String){
        this.peso = peso
    }

    fun setesPresencial(esPresencial: String){
        this.esPresencial = esPresencial
    }

    fun setidMascota(idMascota: Int){
        this.idMascota = idMascota
    }



    fun getidConsulta(): Int? {
        return  idConsulta
    }

    fun getidMascota(): Int{
        return idMascota
    }


    fun getnombreConsulta(): String? {
        return nombreConsulta
    }


    fun getpeso(): String?{
        return peso
    }

    fun getmotivo(): String? {
        return motivo
    }

    fun getesPresencial(): String?{
        return esPresencial
    }

    fun InsertarConsulta(): Long{
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()

        values.put("nombreConsulta", this.nombreConsulta)
        values.put("motivo", this.motivo)
        values.put("peso", this.peso)
        values.put("esPresencial", this.esPresencial)
        values.put("idMascota", this.idMascota)


        return db.insert("t_consulta", null,values)
    }




    fun mostrarConsulta(id: Int): ArrayList<Consulta> {
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val listaConsultas = ArrayList<Consulta>()
        var consulta: Consulta
        var cursorConsulta: Cursor? = null

        cursorConsulta = db.rawQuery("SELECT * FROM t_consulta WHERE idConsulta = ${id+1}", null)

        if (cursorConsulta.moveToFirst()) {
            do {
                consulta = Consulta(null, "", "", "", "", 0, null)

                consulta.setidConsulta(cursorConsulta.getString(0).toInt())
                consulta.setnombreConsulta(cursorConsulta.getString(1))
                consulta.setmotivo(cursorConsulta.getString(2))
                consulta.setpeso(cursorConsulta.getString(3))
                consulta.setesPresencial(cursorConsulta.getString(4))
                consulta.setidMascota(cursorConsulta.getString(5).toInt())
                listaConsultas.add(consulta)
            } while (cursorConsulta.moveToNext())
        }

        cursorConsulta.close()
        return listaConsultas
    }



    fun getConsultaById(id: Int): Consulta {
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var consulta = Consulta(null, "", "", "", "",0, this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_consulta WHERE idConsulta = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                consulta.setidConsulta(cursor.getString(0).toInt())
                consulta.setnombreConsulta(cursor.getString(1))
                consulta.setmotivo(cursor.getString(2))
                consulta.setpeso(cursor.getString(3))
                consulta.setesPresencial(cursor.getString(4))
                consulta.setidMascota(cursor.getString(5).toInt())

            } while (cursor.moveToNext())
        }

        cursor.close()
        return consulta
    }



    fun deleteConsulta(id: Int): Int {
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase


        return db.delete("t_consulta", "idConsulta=?", arrayOf((id + 1).toString()))

    }


    fun updateConsulta(): Int{
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values: ContentValues = ContentValues()

        values.put("nombreConsulta", this.nombreConsulta)
        values.put("motivo", this.motivo)
        values.put("peso", this.peso)
        values.put("esPresencial", this.esPresencial)
        values.put("idMascota", this.idMascota)
        return db.update("t_consulta", values, "idConsulta="+this.idConsulta, null)
    }




    override fun toString(): String {
        val salida=
            "id: ${idConsulta}\n" +
                    "Nombre Consulta: ${nombreConsulta}\n" +
                    "Motivo: ${motivo}\n " +
                    "Peso: ${peso}\n" +
                    "Es Presencial: ${esPresencial} \n"+
                    "id Mascota: ${idMascota}"

        return salida
    }
}