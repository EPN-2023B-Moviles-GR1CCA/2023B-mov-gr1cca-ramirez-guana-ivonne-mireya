package com.example.examen_sqlite.repositorio

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examen_sqlite.model.Consulta
import com.example.examen_sqlite.model.Mascota

class MascotasRepo(
    contexto: Context?, //This
): SQLiteOpenHelper(
    contexto,
    "mascota_consulta", //nombre BDD
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaMascota =
            """
                CREATE TABLE MASCOTA(
                idMascota VARCHAR(50) PRIMARY KEY ON CONFLICT ABORT,
                nombre VARCHAR(50),
                especie VARCHAR(50),
                nivelAdiestramiento INTEGER,
                fechaNacimiento TEXT,
                frecuenciaCardiaca DOUBLE,
                estaEsterilizado INTEGER
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaMascota)

        val scriptSQLCrearTablaConsultas =
            """
            CREATE TABLE CONSULTA(
                id VARCHAR(50) PRIMARY KEY ON CONFLICT ABORT,
                nHistorial INTEGER,
                fechaConsulta TEXT,
                esPresencial INTEGER,
                motivo VARCHAR(50),
                peso DOUBLE,
                recomendacion VARCHAR(50),
                idMascota VARCHAR(50),
                CONSTRAINT fk_mascotas
                    FOREIGN KEY (idMascota)
                    REFERENCES MASCOTA(idMascota)
                    ON DELETE CASCADE
            )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaConsultas)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1) {
            // Elimina las tablas si existen
            db?.execSQL("DROP TABLE IF EXISTS CONSULTA")
            db?.execSQL("DROP TABLE IF EXISTS MASCOTA")

            val scriptSQLCrearTablaMascota =
                """
                CREATE TABLE MASCOTA(
                idMascota VARCHAR(50) PRIMARY KEY ON CONFLICT ABORT,
                nombre VARCHAR(50),
                especie VARCHAR(50),
                nivelAdiestramiento INTEGER,
                fechaNacimiento TEXT,
                frecuenciaCardiaca DOUBLE,
                estaEsterilizado INTEGER
                )
            """.trimIndent()
            db?.execSQL(scriptSQLCrearTablaMascota)

            val scriptSQLCrearTablaConsultas =
                """
            CREATE TABLE CONSULTA(
                id VARCHAR(50) PRIMARY KEY ON CONFLICT ABORT,
                nHistorial INTEGER,
                fechaConsulta TEXT,
                esPresencial INTEGER,
                motivo VARCHAR(50),
                peso DOUBLE,
                recomendacion VARCHAR(50),
                idMascota VARCHAR(50),
                CONSTRAINT fk_mascotas
                    FOREIGN KEY (idMascota)
                    REFERENCES MASCOTA(idMascota)
                    ON DELETE CASCADE
            )
            """.trimIndent()
            db?.execSQL(scriptSQLCrearTablaConsultas)
        }
    }

    /* CRUD Mascotas */

    fun crearMascota(newMascota: Mascota): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("idMascota", newMascota.idMascota)
        valoresAGuardar.put("nombre", newMascota.nombre)
        valoresAGuardar.put("especie", newMascota.especie)
        valoresAGuardar.put("nivelAdiestramiento", newMascota.nivelAdiestramiento)
        valoresAGuardar.put("fechaNacimiento", newMascota.fechaNacimiento)
        valoresAGuardar.put("frecuenciaCardiaca", newMascota.frecuenciaCardiaca)
        valoresAGuardar.put("estaEsterilizado", if(newMascota.estaEsterilizado) 1 else 0)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "MASCOTA", //nombre de la tabla
                null,
                valoresAGuardar //valores
            )

        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun obtenerMascotas(): ArrayList<Mascota> {
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = """
            SELECT * FROM MASCOTA
        """.trimIndent()

        val resultadoConsulta = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        val mascotas = arrayListOf<Mascota>()

        if(resultadoConsulta != null && resultadoConsulta.moveToFirst()){

            do{
                val idMascota = resultadoConsulta.getString(0) //Indice 0
                val nombre = resultadoConsulta.getString(1)
                val especie = resultadoConsulta.getString(2)
                val nivelAdiestramiento = resultadoConsulta.getInt(3)
                val fechaNacimiento = resultadoConsulta.getString(4)
                val frecuenciaCardiaca = resultadoConsulta.getDouble(5)
                val estaEsterilizado = resultadoConsulta.getString(6)

                if(idMascota != null){
                    val usuarioEncontrado = Mascota("", "", "", 0, "", 0.0, false)
                    usuarioEncontrado.idMascota = idMascota
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.especie = especie
                    usuarioEncontrado.nivelAdiestramiento = nivelAdiestramiento
                    usuarioEncontrado.fechaNacimiento = fechaNacimiento
                    usuarioEncontrado.frecuenciaCardiaca = frecuenciaCardiaca
                    usuarioEncontrado.estaEsterilizado = estaEsterilizado.equals("1")

                    mascotas.add(usuarioEncontrado)
                }
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta?.close()
        baseDatosLectura.close()
        return mascotas //arreglo
    }

    fun consultarMascotaPorIdMascota(idMascota: String): Mascota{
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = """
            SELECT * FROM MASCOTA WHERE IDMASCOTA = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(idMascota)

        val resultadoConsulta = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura //Parametros
        )

        val existeMascota = resultadoConsulta.moveToFirst()

        val usuarioEncontrado = Mascota("", "", "", 0, "", 0.0, false)
        if(existeMascota){
            do{
                val idMascota = resultadoConsulta.getString(0) //Indice 0
                val nombre = resultadoConsulta.getString(1)
                val especie = resultadoConsulta.getString(2)
                val nivelAdiestramiento = resultadoConsulta.getInt(3)
                val fechaNacimiento = resultadoConsulta.getString(4)
                val frecuenciaCardiaca = resultadoConsulta.getDouble(5)
                val estaEsterilizado = resultadoConsulta.getString(6)

                if(idMascota != null){
                    usuarioEncontrado.idMascota = idMascota
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.especie = especie
                    usuarioEncontrado.nivelAdiestramiento = nivelAdiestramiento
                    usuarioEncontrado.fechaNacimiento = fechaNacimiento
                    usuarioEncontrado.frecuenciaCardiaca = frecuenciaCardiaca
                    usuarioEncontrado.estaEsterilizado = estaEsterilizado.equals("1")
                }
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        baseDatosLectura.close()
        return usuarioEncontrado //arreglo
    }

    fun actualizarMascota(
        datosActualizados: Mascota
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", datosActualizados.nombre)
        valoresAActualizar.put("especie", datosActualizados.especie)
        valoresAActualizar.put("nivelAdiestramiento", datosActualizados.nivelAdiestramiento)
        valoresAActualizar.put("fechaNacimiento", datosActualizados.fechaNacimiento)
        valoresAActualizar.put("frecuenciaCardiaca", datosActualizados.frecuenciaCardiaca)
        valoresAActualizar.put("estaEsterilizado", if(datosActualizados.estaEsterilizado) 1 else 0)

        val parametrosConsultaActualizar = arrayOf(datosActualizados.idMascota)
        val resultadoActualizcion = conexionEscritura
            .update(
                "MASCOTA", //tabla
                valoresAActualizar,
                "idMascota = ?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizcion == -1) false else true
    }

    fun eliminarMascotaPorIdMascota(idMascota: String): Boolean{
        val conexionEscritura = writableDatabase

        val parametrosConsultaDelete = arrayOf(idMascota)

        val resultadoEliminacion = conexionEscritura
            .delete(
                "MASCOTA", //tabla
                "idMascota = ?",
                parametrosConsultaDelete
            )

        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    fun crearConsultas(newConsulta: Consulta): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("id", newConsulta.id)
        valoresAGuardar.put("nHistorial", newConsulta.nHistorial)
        valoresAGuardar.put("fechaConsulta", newConsulta.fechaConsulta)
        valoresAGuardar.put("esPresencial", if(newConsulta.esPresencial) 1 else 0)
        valoresAGuardar.put("motivo", newConsulta.motivo)
        valoresAGuardar.put("peso", newConsulta.peso )
        valoresAGuardar.put("recomendacion", newConsulta.recomendacion)
        valoresAGuardar.put("idMascota", newConsulta.idMascota)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "CONSULTA", //nombre de la tabla
                null,
                valoresAGuardar //valores
            )

        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun obtenerConsultasPorMascota(idMascota: String): ArrayList<Consulta> {
        val consultas = arrayListOf<Consulta>()
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = """
            SELECT * FROM CONSULTA
            WHERE IDMASCOTA = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(idMascota)
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura //Parametros
        )

        if(resultadoConsultaLectura != null && resultadoConsultaLectura.moveToFirst()){

            do{
                val id = resultadoConsultaLectura.getString(0)
                val nHistorial = resultadoConsultaLectura.getInt(1)
                val fechaConsulta = resultadoConsultaLectura.getString(2)
                val esPresencial = resultadoConsultaLectura.getString(3)
                val motivo = resultadoConsultaLectura.getString(4)
                val peso = resultadoConsultaLectura.getDouble(5)
                val recomendacion = resultadoConsultaLectura.getString(6)
                val idMascotaC = resultadoConsultaLectura.getString(7)

                if(id != null){
                    val consultaEncontrada = Consulta("", 0, "", true, "", 1.0, "", "")
                    consultaEncontrada.id = id
                    consultaEncontrada.recomendacion = recomendacion
                    consultaEncontrada.fechaConsulta = fechaConsulta
                    consultaEncontrada.esPresencial = esPresencial.equals("1")
                    consultaEncontrada.motivo = motivo
                    consultaEncontrada.nHistorial = nHistorial
                    consultaEncontrada.peso = peso
                    consultaEncontrada.idMascota = idMascotaC

                    consultas.add(consultaEncontrada)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura?.close()
        baseDatosLectura.close()

        return consultas //arreglo
    }

    fun consultarConsultasPoridYMascota(idConsulta: String, idMascota: String): Consulta{
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = """
            SELECT * FROM CONSULTA WHERE ID = ? AND IDMASCOTA = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(idConsulta, idMascota)

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura //Parametros
        )

        val existeConsultas = resultadoConsultaLectura.moveToFirst()

        val consultaEncontrada =Consulta("", 1, "", true, "", 1.0, "", "")
        if(existeConsultas){
            do{
                val id = resultadoConsultaLectura.getString(0)
                val nHistorial = resultadoConsultaLectura.getInt(1)
                val fechaConsulta = resultadoConsultaLectura.getString(2)
                val esPresencial = resultadoConsultaLectura.getString(3)
                val motivo = resultadoConsultaLectura.getString(4)
                val peso = resultadoConsultaLectura.getDouble(5)
                val recomendacion = resultadoConsultaLectura.getString(6)
                val idMascotaC = resultadoConsultaLectura.getString(7)

                if(id != null){
                    consultaEncontrada.id = id
                    consultaEncontrada.recomendacion = recomendacion
                    consultaEncontrada.fechaConsulta = fechaConsulta
                    consultaEncontrada.esPresencial = esPresencial.equals("1")
                    consultaEncontrada.motivo = motivo
                    consultaEncontrada.nHistorial = nHistorial
                    consultaEncontrada.peso = peso
                    consultaEncontrada.idMascota = idMascotaC
                }
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return consultaEncontrada //arreglo
    }

    fun actualizarConsultasPoridYIdMascota(
        datosActualizados: Consulta
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("recomendacion", datosActualizados.recomendacion)
        valoresAActualizar.put("fechaConsulta", datosActualizados.fechaConsulta)
        valoresAActualizar.put("esPresencial", if(datosActualizados.esPresencial) 1 else 0)
        valoresAActualizar.put("motivo", datosActualizados.motivo)
        valoresAActualizar.put("nHistorial", datosActualizados.nHistorial)
        valoresAActualizar.put("peso", datosActualizados.peso)
        //where id = ?
        val parametrosConsultaActualizar = arrayOf(datosActualizados.id, datosActualizados.idMascota)
        val resultadoActualizcion = conexionEscritura
            .update(
                "CONSULTA", //tabla
                valoresAActualizar,
                "id = ? and idMascota = ?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizcion == -1) false else true
    }

    fun eliminarConsultasPoridYIdMascota(idConsulta: String, idMascota: String): Boolean{
        val conexionEscritura = writableDatabase

        val parametrosConsultaDelete = arrayOf( idConsulta, idMascota)

        val resultadoEliminacion = conexionEscritura
            .delete(
                "CONSULTA", //tabla
                "id = ? and idMascota = ?",
                parametrosConsultaDelete
            )

        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }
}