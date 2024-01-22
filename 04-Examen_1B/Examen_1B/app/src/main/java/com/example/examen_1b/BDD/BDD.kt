package com.example.examen_1b.BDD

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context

class BDD(context: Context?): SQLiteOpenHelper(
    context, "Examen1b", null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Crear base de datos

        val scriptSQLCrearTablaMascota =
            "CREATE TABLE t_mascota(" +
                    "idMascota INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombreMascota TEXT NOT NULL," +
                    "fechaNacimiento TEXT NOT NULL," +
                    "frecuenciaCardiaca TEXT NOT NULL," +
                    "especie TEXT NOT NULL);"



        val scriptSQLCrearTablaConsulta =
            "CREATE TABLE t_consulta(" +
                    "idConsulta INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombreConsulta TEXT NOT NULL," +
                    "motivo TEXT NOT NULL," +
                    "peso TEXT NOT NULL," +
                    "esPresencial TEXT NOT NULL, " +
                    "idMascota INTEGER NOT NULL," +
                    "FOREIGN KEY(idMascota) REFERENCES t_mascota(idMascota ));"

        db?.execSQL(scriptSQLCrearTablaMascota)
        db?.execSQL(scriptSQLCrearTablaConsulta)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Ejemplo: Eliminar la tabla existente y volver a crearla
        db?.execSQL("DROP TABLE IF EXISTS t_mascota")
        db?.execSQL("DROP TABLE IF EXISTS t_consulta")
        onCreate(db)
    }



}