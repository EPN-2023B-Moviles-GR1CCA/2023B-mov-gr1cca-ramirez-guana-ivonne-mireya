package com.example.examen_1b.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.examen_1b.BDD.BDD

class Mascota(
    var idMascota: Int?,
    var nombreMascota: String?,
    var frecuenciaCardiaca: String,
    var especie: String,
    var fechaNacimiento: String?,
    val context: Context?
) {

    init{
        frecuenciaCardiaca
        especie
        fechaNacimiento
        idMascota
        nombreMascota
        context
    }



    fun setidMascota(idMascota: Int) {
        this.idMascota = idMascota
    }

    fun setnombreMascota(nombreMascota: String?) {
        this.nombreMascota = nombreMascota
    }

    fun setfechaNacimiento(fechaNacimiento: String?) {
        this.fechaNacimiento = fechaNacimiento
    }

    fun setfrecuenciaCardiaca(frecuenciaCardiaca: String) {
        this.frecuenciaCardiaca = frecuenciaCardiaca
    }

    fun setespecie(especie: String) {
        this.especie = especie
    }



    fun getidMascota(): Int? {
        return idMascota
    }

    fun getnombreMascota(): String? {
        return nombreMascota
    }

    fun getfechaNacimiento(): String? {
        return fechaNacimiento
    }

    fun getfrecuenciaCardiaca(): String {
        return frecuenciaCardiaca
    }

    fun getespecie(): String {
        return especie
    }



    fun insertMascota(): Long {
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values: ContentValues = ContentValues()

        values.put("frecuenciaCardiaca", this.frecuenciaCardiaca)
        values.put("especie", this.especie)
        values.put("fechaNacimiento", this.fechaNacimiento)
        values.put("idMascota", this.idMascota)
        values.put("nombreMascota", this.nombreMascota)



        return db.insert("t_mascota", null, values)
    }



    fun showMascotas(): ArrayList<Mascota> {


        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var lista = ArrayList<Mascota>()
        var mascota: Mascota
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_mascota", null)

        if (cursor.moveToFirst()) {
            do {
                mascota = Mascota(null, "", "", "", "", this.context)

                mascota.setidMascota(cursor.getString(0).toInt())
                mascota.setnombreMascota(cursor.getString(1))
                mascota.setfechaNacimiento(cursor.getString(2))
                mascota.setfrecuenciaCardiaca(cursor.getString(3))
                mascota.setespecie(cursor.getString(4))
                lista.add(mascota)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }


    fun getMascotaById(id: Int): Mascota {
        val dbHelper = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var mascota = Mascota(null, "", "", "", "",this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_mascota WHERE idMascota = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                mascota.setidMascota(cursor.getString(0).toInt())
                mascota.setnombreMascota(cursor.getString(1))
                mascota.setfechaNacimiento(cursor.getString(2))
                mascota.setfrecuenciaCardiaca(cursor.getString(3))
                mascota.setespecie(cursor.getString(4))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return mascota
    }


    fun updateMascota(): Int {
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values: ContentValues = ContentValues()

        values.put("nombreMascota", this.nombreMascota)
        values.put("fechaNacimiento", this.fechaNacimiento)
        values.put("frecuenciaCardiaca", this.frecuenciaCardiaca)
        values.put("especie", this.especie)

        return db.update("t_mascota", values, "idMascota="+this.idMascota, null)
    }

    fun deleteMascota(id: Int): Int {
        val dbHelper: BDD = BDD(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        return db.delete("t_mascota", "idMascota="+(id+1), null)
    }



    override fun toString(): String {
        val salida =
            "Codigo: ${idMascota} \n" +
                    "Nombre: ${nombreMascota} \n"+
                    "Fecha Nacimiento: ${fechaNacimiento} \n"+
                    "frecuenciaCardiaca: ${frecuenciaCardiaca}\n"+
                    "especie: ${especie}"

        return salida
    }
}
