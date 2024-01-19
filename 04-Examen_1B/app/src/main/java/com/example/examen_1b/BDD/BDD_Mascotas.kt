package com.example.examen_1b.BDD

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examen_1b.model.Mascota
import java.util.Date

class BDD_Mascotas(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "BDD_Mascotas",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaMascotas =
            """
                CREATE TABLE MASCOTA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                     nombre VARCHAR(50),
                     especie VARCHAR(50),
                     fechaNacimiento TEXT,
                     frecuenciaCardiaca REAL,
                     estaEsterilizado INTEGER,/* SI-1 O NO-0*/
                     nivelAdiestramiento INTEGER
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaMascotas)
    }

    override fun onUpgrade(
        p0: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
    }

    fun crearMascota(
        nuevaMascota: Mascota
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("Id", nuevaMascota.id)
        valoresAGuardar.put("Nombre", nuevaMascota.nombre)
        valoresAGuardar.put("Especie", nuevaMascota.especie)
        valoresAGuardar.put("Fecha de Nacimiento", nuevaMascota.fechaNacimiento.toString())
        valoresAGuardar.put("Frecuencia Cardiaca", nuevaMascota.frecuenciaCardiaca)
        valoresAGuardar.put("Esta Esterilizado", if (nuevaMascota.estaEsterilizado) 1 else 0)
        valoresAGuardar.put("Nivel Adiestramiento", nuevaMascota.nivelAdiestramiento)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "MASCOTA",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun obtenerMascota(): ArrayList<Mascota> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM MASCOTA
                """.trimIndent()
        val resultadoConsulta = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val mascotas = arrayListOf<Mascota>()

        if (resultadoConsulta != null && resultadoConsulta.moveToNext()) {
            do {
                val id = resultadoConsulta.getInt(0)
                var nombre = resultadoConsulta.getString(1)
                var especie = resultadoConsulta.getString(2)
                var fechaNacimiento = resultadoConsulta.getString(3)
                var frecuenciaCardiaca = resultadoConsulta.getFloat(4)
                var estaEsterilizado = resultadoConsulta.getString(5)
                var nivelAdiestramiento = resultadoConsulta.getInt(6)


                if (id != null) {
                    val mascotaEncontrada = Mascota(1, "chichilo", "gato", Date(), 15.5f, false, 1)
                    mascotaEncontrada.id = id
                    mascotaEncontrada.nombre = nombre
                    mascotaEncontrada.especie = especie
                    mascotaEncontrada.frecuenciaCardiaca = frecuenciaCardiaca
                    mascotaEncontrada.estaEsterilizado = estaEsterilizado.equals("1")
                    mascotaEncontrada.nivelAdiestramiento = nivelAdiestramiento
                    mascotaEncontrada.fechaNacimiento = Date(fechaNacimiento)

                    mascotas.add((mascotaEncontrada))
                }
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta?.close()
        baseDatosLectura.close()
        return mascotas
    }

    fun consultarMascotaID(id: String): Mascota{
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = """
            SELECT * FROM COCINERO WHERE CODIGOUNICO = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(id)

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura //Parametros
        )

        val existeMascota = resultadoConsultaLectura.moveToFirst()

        val mascotaEncontrada = Mascota(1, "chichilo", "gato", Date(), 15.5f, false, 1)
        if(existeMascota){
            do {
                val id = resultadoConsultaLectura.getInt(0)
                var nombre = resultadoConsultaLectura.getString(1)
                var especie = resultadoConsultaLectura.getString(2)
                var fechaNacimiento = resultadoConsultaLectura.getString(3)
                var frecuenciaCardiaca = resultadoConsultaLectura.getDouble(4)
                var estaEsterilizado = resultadoConsultaLectura.getString(5)
                var nivelAdiestramiento = resultadoConsultaLectura.getInt(6)

                if (id != null) {
                    val mascotaEncontrada = Mascota(1, "chichilo", "gato", Date(), 15.5f, false, 1)
                    mascotaEncontrada.id = id
                    mascotaEncontrada.nombre = nombre
                    mascotaEncontrada.especie = especie
                    mascotaEncontrada.frecuenciaCardiaca = frecuenciaCardiaca.toFloat()
                    mascotaEncontrada.estaEsterilizado = estaEsterilizado.equals(1)
                    mascotaEncontrada.nivelAdiestramiento = nivelAdiestramiento
                }
            } while (resultadoConsultaLectura.moveToNext())
                      
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return mascotaEncontrada //arreglo
    }

    fun actualizarMascotaID(
        datosActualizados: Mascota
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("Nombre/Apodo", datosActualizados.nombre)
        valoresActualizar.put("Especie", datosActualizados.especie)
        valoresActualizar.put("Frecuencia Cardiaca", datosActualizados.frecuenciaCardiaca)
        valoresActualizar.put("Esta Esterilizado", if(datosActualizados.estaEsterilizado) 1 else 0)
        valoresActualizar.put("Nivel Adiestramiento", datosActualizados.nivelAdiestramiento)
        //where id = ?
        val parametrosConsultaActualizar = arrayOf(datosActualizados.id.toString())
        val resultadoActualizar = conexionEscritura
            .update(
                "MASCOTA", //tabla
                valoresActualizar,
                "id = ?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizar == -1) false else true
    }

    fun eliminarMascotaID(id: String): Boolean{
        val conexionEscritura = writableDatabase

        val parametrosConsultaDelete = arrayOf( id)

        val resultadoEliminacion = conexionEscritura
            .delete(
                "MASCOTA", //tabla
                "id = ?",
                parametrosConsultaDelete
            )

        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }















}

