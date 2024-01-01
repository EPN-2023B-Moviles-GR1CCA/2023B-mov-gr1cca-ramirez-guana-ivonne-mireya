import com.google.gson.Gson
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n************   Bienvenido al Administrador de CONSULTA-MASCOTA   ************")
        println("\n\nEscoja una opción:")
        println("1. Administrar Consultas")
        println("2. Administrar Mascotas")
        println("3. Exit")
        
        when (scanner.nextInt()) {
            1 -> {
                println("\n************  Administrar Consultas:  ************")
                println("\n\n1. Ingresar Consulta")
                println("2. Visualizar Consultas")
                println("3. Actualizar Consulta")
                println("4. Borrar Consulta")

                when (scanner.nextInt()) {
                    1 -> {
                        println("* Ingresar los detalles de la Consulta:")

                        println("* #Historial:")
                        val nHistorial = scanner.nextInt()

                        println("* Fecha de Consulta (yyyy-MM-dd):")
                        val fechaConsultaString = readLine()
                        val formato = SimpleDateFormat("yyyy-MM-dd")
                        val fechaConsulta = formato.parse(fechaConsultaString)

                        println("* Consulta Presencial (true/false):")
                        val esPresencial = scanner.nextBoolean()

                        println("* Motivo:")
                        val motivo = readLine()

                        println("* Peso:")
                        val peso = scanner.nextFloat()

                        println("* Observaciones (separación con comás):")
                        val observaciones = scanner.next().split(",").toTypedArray()
                        
                        println("* Recomendaciones:")
                        val recomendaciones = scanner.next()

                        val consulta = Consulta(
                            Consulta.idDisponible(),
                            nHistorial,
                            fechaConsulta,
                            esPresencial,
                            motivo,
                            peso,
                            observaciones,
                            recomendaciones
                        )

                        Consulta.crearConsulta(consulta)
                        println("\n* Consulta ingresada exitosamente !!!!!")
                    }
                    2 -> {
                        val consultas = Consulta.desplegarConsultas()
                        if (consultas.isNotEmpty()) {
                            println("\n* Consultas:")
                            consultas.forEach { println(it) }
                        } else {
                            println("\n* Consultas no encontradas.")
                        }
                    }
                    3 -> {
                        println("\n* Consultas existentes:")
                        val consultasExistentes = Consulta.desplegarConsultas()
                        consultasExistentes.forEach { println(it) }

                        println("\n* Ingresar el ID de la consulta a actualizar:")
                        val id = readLine()!!.toInt()
                        val consultaToUpdate = consultasExistentes.find { it.id == id }

                        if (consultaToUpdate != null) {
                            println("\n* Ingresar el atributo a actualizar (#Historial, Fecha, Consulta Presencial,Motivo, Peso, Observaciones, Recomendaciones):")
                            val atributo = readLine()

                            when (atributo) {

                                "#Historial" -> {
                                    println("* Actualizar #Historial:")
                                    consultaToUpdate.nHistorial = readLine()!!.toInt()
                                }
                                "Fecha" -> {
                                    println("* Actualizar Fecha de la Consulta (yyyy-MM-dd):")
                                    val fechaSF = readLine()!!
                                    val formato = SimpleDateFormat("yyyy-MM-dd")
                                    consultaToUpdate.fecha = formato.parse(fechaSF)
                                }
                                "Consulta Presencial" -> {
                                    println("* Actualizar si la Consulta es Precencial(true/false):")
                                    consultaToUpdate.esPresencial = readLine()!!.toBoolean()
                                }
                                "Motivo" -> {
                                    println("* Actualizar Motivo:")
                                    consultaToUpdate.motivo = readLine()!!
                                }
                                "Peso" -> {
                                    println("* Actualizar Peso:")
                                    consultaToUpdate.peso = readLine()!!.toFloat()
                                }
                                "Observaciones" -> {
                                    println("* Número de Observaciones:")
                                    val numObservaciones = readLine()!!.toInt()
                                    val observaciones = mutableListOf<String>()
                                    for (i in 1..numObservaciones) {
                                        println("Observaciones $i:")
                                        val ingrediente = readLine()!!
                                        observaciones.add(ingrediente)
                                    }
                                    consultaToUpdate.observaciones = observaciones.toTypedArray()
                                }
                                "Recomendaciones" -> {
                                    println("* Actualizar Recomendación:")
                                    consultaToUpdate.recomendaciones = readLine()!!
                                }
                                else -> {
                                    println("* Atributo no válido.")
                                    return
                                }
                            }

                            Consulta.actualizarConsulta(consultaToUpdate)
                            println("\n\n* Consulta actualizada exitosamente.")
                        } else {
                            println("\n\n* Consulta con el ID $id no encontrada.")
                        }

                    }
                    4 -> {
                        println("\n* Consultas existentes:")
                        val consultasExistentes = Consulta.desplegarConsultas()
                        consultasExistentes.forEach { println("\n"+it) }

                        println("\n* Ingresar el ID de la consulta a borrar:")
                        val id = scanner.nextInt()
                        Consulta.borrarConsulta(id)
                        println("\n* Consulta borrada exitosamente.")
                    }
                    else -> {
                        println("\n\n* Opción invalida.")
                    }
                }
            }
            2 -> {
                println("\n\n\n************  Administrar Mascotas:  ************")
                println("\n\n1. Ingresar Mascota")
                println("2. Visualizar Mascotas")
                println("3. Actulizar Mascota")
                println("4. Borrar Mascota")

                when (scanner.nextInt()) {
                    1 -> {
                        println("\n* Ingresar los detalles de la Mascota")
                        println("* Nombre/Apodo:")
                        val nombre = readLine()

                        println("* Especie:")
                        val especie = scanner.next()

                        println("* Fecha de Nacimiento (yyyy-MM-dd):")
                        val fechaConsultaString = readLine()
                        val formato = SimpleDateFormat("yyyy-MM-dd")
                        val fechaNacimiento = formato.parse(fechaConsultaString)

                        println("* Frecuencia Cardiaca:")
                        val frecuenciaCardiaca = scanner.nextFloat()

                        println("* Esta esterilizado (true/false):")
                        val estaEsterilizado = scanner.nextBoolean()
                        val consultas = mutableListOf<Consulta>()

                        println("* Nivel de Adiestramiento(1-5):")
                        val nivelAdiestramiento = scanner.nextInt()
                        
                        while (true) {
                            println("\n* Quisieras revisar alguna Consulta? (y/n)")
                            val choice = scanner.next()
                            if (choice.equals("y", ignoreCase = true)) {
                                val consultasExistentes = Consulta.desplegarConsultas()
                                consultasExistentes.forEach { println(it) }
                                println("* Ingresar el Id de la consulta :")
                                val consultaId = scanner.nextInt()
                                val consultaToAdd = Consulta.desplegarConsultas().find { it.id == consultaId }
                                if (consultaToAdd != null) {
                                    consultas.add(consultaToAdd)
                                    println("* Consulta revisada.")
                                } else {
                                    println("\n* La consulta con el ID $consultaId no encontrada.")
                                }
                            } else {
                                break
                            }
                        }

                        val mascota = Mascota(
                            Mascota.idDisponible(),
                            nombre,
                            especie,
                            fechaNacimiento,
                            frecuenciaCardiaca,
                            estaEsterilizado,
                            nivelAdiestramiento,
                            consultas.toTypedArray()
                        )

                        Mascota.crearMascota(mascota)
                        println("\n* Mascota guardado.")

                    }
                    2 -> {
                        val mascotas = Mascota.desplegarMascota()
                        if (mascotas.isNotEmpty()) {
                            println("* Mascotas:")
                            mascotas.forEach { println(it) }
                        } else {
                            println("\n* Mascota no encontrado.")
                        }
                    }
                    3 -> {
                        println("\n\n* Mascotas existentes:")
                        val mascotasExistentes = Mascota.desplegarMascota()
                        mascotasExistentes.forEach { println(it) }

                        println("\n* Ingresar el ID del mascota para actualizar:")
                        val id = readLine()!!.toInt()
                        val mascotaToUpdate = mascotasExistentes.find { it.id == id }

                        if (mascotaToUpdate != null) {
                            println("* Ingresar el atributo a actualizar (Nombre, Especie, Fecha Nacimiento, Frecuencia Cardiaca," +
                                    "Esta Esterilizado, Nivel de Adiestramiento, Consultas):")
                            val atributo = readLine()

                            when (atributo) {
                                "Nombre" -> {
                                    println("Actualizar Nombre/Apodo:")
                                    mascotaToUpdate.nombre = readLine()!!
                                }
                                "Especie" -> {
                                    println("* Actualizar Especie:")
                                    mascotaToUpdate.especie = readLine()!!
                                }
                                "Fecha de Nacimiento" -> {
                                    println("Actualizar Fecha de Nacimiento (yyyy-MM-dd):")
                                    val integracionSF = readLine()!!
                                    val formato = SimpleDateFormat("yyyy-MM-dd")
                                    mascotaToUpdate.fechaNacimiento = formato.parse(integracionSF)
                                }
                                "Frecuencia Cardiaca" -> {
                                    println("Actualizar Frecuencia Cardiaca:")
                                    mascotaToUpdate.frecuenciaCardiaca = readLine()!!.toFloat()
                                }                                
                                "Esta Esterilizado" -> {
                                    println("Actualizar si esta Esterilizado (true/false):")
                                    mascotaToUpdate.estaEsterilizado = readLine()!!.toBoolean()
                                }
                                "Nivel de Adiestramiento" -> {
                                    println("Actualizar Nivel de Adiestramiento (1-5):")
                                    mascotaToUpdate.nivelAdiestramiento = readLine()!!.toInt()
                                }
                                "Consultas" -> {
                                    println("* Número de consultas:")
                                    val numConsultas = readLine()!!.toInt()
                                    val consultas = mutableListOf<Consulta>()
                                    for (i in 1..numConsultas) {
                                        println("* Consulta $i:")
                                        val consultaId = readLine()!!.toInt()
                                        val consulta = Consulta.desplegarConsultas().find { it.id == consultaId }
                                        if (consulta != null) {
                                            consultas.add(consulta)
                                        }
                                    }
                                    mascotaToUpdate.consultas = consultas.toTypedArray()
                                }
                                else -> {
                                    println("\n* Atributo no válido.")
                                    return
                                }
                            }

                            Mascota.actualizarMascota(mascotaToUpdate)
                            println("\n* Mascota actualizado.")
                        } else {
                            println("\n* La mascota con el ID $id indicada no existe.")
                        }

                    }
                    4 -> {
                        println("\n* Mascotas existentes:")
                        val mascotasExistentes = Mascota.desplegarMascota()
                        mascotasExistentes.forEach { println(it) }

                        println("\n* Ingresar el Id de la mascota a borrar:")
                        val id = scanner.nextInt()
                        Mascota.borrarMascota(id)
                        println("\n* Mascota borrada exitosamente")
                    }
                    else -> {
                        println("\n* Opción invalida.")
                    }
                }
            }
            3 -> {
                println("\n* Saliendo...")
                return
            }
            else -> {
                println("\n* Opción invalida.")
            }
        }
    }
}



data class Consulta(
    val id: Int,
    var nHistorial: Int,
    var fecha: Date,
    var esPresencial: Boolean,
    var motivo: String?,
    var peso: Float,
    var observaciones: Array<String>,
    var recomendaciones: String
) {
    companion object {
        private const val archivo_consultas = "C:\\Users\\LENOVO\\Desktop\\A.Moviles.R\\2023B-mov-gr1cca-ramirez-guana-ivonne-mireya\\03-Deber01-IB\\Deber01\\src\\main\\kotlin\\Consultas.txt"

        fun desplegarConsultas(): List<Consulta> {
            val archivoConsultas = File(archivo_consultas)
            val gson = Gson()
            val lineas = archivoConsultas.readLines()
            val consultas = mutableListOf<Consulta>()

            for (linea in lineas) {
                val consulta = gson.fromJson(linea, Consulta::class.java)
                consultas.add(consulta)
            }

            return consultas
        }

        fun crearConsulta(consulta: Consulta) {
            val consultas = desplegarConsultas().toMutableList()
            consultas.add(consulta)
            guardarConsulta(consultas)
        }

        fun idDisponible(): Int {
            val consultas = desplegarConsultas()
            return consultas.map { it.id }.maxOrNull()?.plus(1) ?: 1
        }

        fun borrarConsulta(id: Int) {
            val consultas = desplegarConsultas().toMutableList()
            val consultaBorrar = consultas.find { it.id == id }
            if (consultaBorrar != null) {
                consultas.remove(consultaBorrar)
                guardarConsulta(consultas)
            }
        }

        fun guardarConsulta(consultas: List<Consulta>) {
            val archivoConsultas = File(archivo_consultas)
            val gson = Gson()
            val lineas = consultas.map { gson.toJson(it) }
            archivoConsultas.writeText(lineas.joinToString("\n"))
        }
        fun actualizarConsulta(consulta: Consulta) {
            val consultas = desplegarConsultas().toMutableList()
            val index = consultas.indexOfFirst { it.id == consulta.id }
            if (index != -1) {
                consultas[index] = consulta
                guardarConsulta(consultas)
            }
        }
    }

    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fecha = formato.format(fecha)
        return """
            * Consulta #$id 
            * #Historial: $nHistorial
            * Fecha de la Consulta: $fecha 
            * Consulta Presencial: $esPresencial
            * Motivo: $motivo
            * Peso: $peso
            * Observaciones: ${observaciones.joinToString(", ")}
            * Recomendaciones: $recomendaciones
        """.trimIndent()
    }
}


data class Mascota(
    val id: Int,
    var nombre: String?,
    var especie: String,
    var fechaNacimiento: Date,
    var frecuenciaCardiaca: Float,
    var estaEsterilizado: Boolean,
    var nivelAdiestramiento: Int,
    var consultas: Array<Consulta>
) {
    companion object {
        private const val archivo_mascotas = "C:\\Users\\LENOVO\\Desktop\\A.Moviles.R\\2023B-mov-gr1cca-ramirez-guana-ivonne-mireya\\03-Deber01-IB\\Deber01\\src\\main\\kotlin\\Mascotas.txt"

        fun desplegarMascota(): List<Mascota> {
            val archivoMascotas = File(archivo_mascotas)
            val gson = Gson()
            val lineas = archivoMascotas.readLines()
            val mascotas = mutableListOf<Mascota>()

            for (linea in lineas) {
                val mascota = gson.fromJson(linea, Mascota::class.java)
                mascotas.add(mascota)
            }

            return mascotas
        }

        fun crearMascota(mascota: Mascota) {
            val mascotas = desplegarMascota().toMutableList()
            mascotas.add(mascota)
            guardarMascota(mascotas)
        }

        fun idDisponible(): Int {
            val mascotas = desplegarMascota()
            return mascotas.map { it.id }.maxOrNull()?.plus(1) ?: 1
        }

        fun borrarMascota(id: Int) {
            val mascotas = desplegarMascota().toMutableList()
            val mascotaBorrar = mascotas.find { it.id == id }
            if (mascotaBorrar != null) {
                mascotas.remove(mascotaBorrar)
                guardarMascota(mascotas)
            }
        }

        private fun guardarMascota(mascotas: List<Mascota>) {
            val archivoMascotas = File(archivo_mascotas)
            val gson = Gson()
            val lineas = mascotas.map { gson.toJson(it) }
            archivoMascotas.writeText(lineas.joinToString("\n"))
        }

        fun actualizarMascota(mascota: Mascota) {
            val mascotas = desplegarMascota().toMutableList()
            val index = mascotas.indexOfFirst { it.id == mascota.id }
            if (index != -1) {
                mascotas[index] = mascota
                guardarMascota(mascotas)
            }
        }
    }

    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fechaN = formato.format(fechaNacimiento)
        return "* Mascota #$id \n* Nombre/Apodo: $nombre \n* Nivel de Adiestramiento(1-5): $nivelAdiestramiento \n* Frecuencia Cardiaca: $frecuenciaCardiaca\n* Fecha de Nacimiento: $fechaN\n* Esta esterilizado: $estaEsterilizado\n* Consultas: ${consultas.contentToString()}\n"
    }
}