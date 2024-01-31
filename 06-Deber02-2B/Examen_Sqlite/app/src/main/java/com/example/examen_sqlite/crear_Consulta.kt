package com.example.examen_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.examen_sqlite.model.Consulta
import com.google.android.material.snackbar.Snackbar
import com.example.examen_sqlite.bdd.BDD

class crear_Consulta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_consulta)

        val idMascotaC = intent.extras?.getString("idMascota")


        val spinnerEsPresencial = findViewById<Spinner>(R.id.sp_esPresencial_crear)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_esta_Esterilizado,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerEsPresencial.adapter = adaptador


        val btnGuardarConsulta = findViewById<Button>(R.id.btn_guardar_consulta)
        btnGuardarConsulta
            .setOnClickListener {
                try {
                    val id = findViewById<EditText>(R.id.et_id_crear)
                    val nHistorial = findViewById<EditText>(R.id.et_nHistorial_crear)
                    val fechaConsulta = findViewById<EditText>(R.id.et_fecha_crear)
                    val esPresencial = spinnerEsPresencial.selectedItem.toString()
                    val motivo = findViewById<EditText>(R.id.et_motivo_crear)
                    val peso = findViewById<EditText>(R.id.et_peso_crear)
                    val recomendacion = findViewById<EditText>(R.id.et_recomendacion_crear)

                    id.error = null
                    recomendacion.error = null
                    nHistorial.error = null
                    peso.error = null
                    fechaConsulta.error = null
                    motivo.error = null

                    if (validarCampos( id, nHistorial, fechaConsulta, esPresencial, motivo, peso, recomendacion)
                    ) {
                        val esEsPresencial = esPresencial.equals("Si")
                        val newConsulta = Consulta(
                            id.text.toString(),
                            nHistorial.text.toString().toInt(),
                            fechaConsulta.text.toString(),
                            esEsPresencial,
                            motivo.text.toString(),
                            peso.text.toString().toDouble(),
                            recomendacion.text.toString(),
                            idMascotaC!!
                        )

                        val respuesta = BDD.bddAplicacion!!.crearConsultas(newConsulta)

                        if (respuesta) {
                            val data = Intent()
                            data.putExtra("idMascota", idMascotaC)
                            data.putExtra("message", "La consulta se ha creado exitosamente")
                            setResult(RESULT_OK, data)
                            finish()
                        } else {
                            mostrarSnackbar("Hubo un problema al crear la consulta")
                        }
                    }

                } catch (e: Exception) {
                    Log.e("Error", "Error en la aplicación", e)
                }
            }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.layout_crear_consulta), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun validarCampos(
        id: EditText,
        nHistorial: EditText,
        fecha: EditText,
        esPresencial: String,
        motivo: EditText,
        peso: EditText,
        recomendacion: EditText
    ): Boolean {
        if (id.text.isBlank()) {
            id.error = "Campo requerido"
            return false
        }

        if (recomendacion.text.isBlank()) {
            recomendacion.error = "Campo requerido"
            return false
        }

        if (nHistorial.text.isBlank()) {
            nHistorial.error = "Campo requerido"
            return false
        } else {
            val nHistorialInt = nHistorial.text.toString().toInt()
            if (nHistorialInt < 0) {
                nHistorial.error = "El número de historial debe ser mayor a 0"
                return false
            }
        }

        if (fecha.text.isBlank()) {
            fecha.error = "Campo requerido"
            return false
        } else {
            val fechaRegex = Regex("""^\d{4}-\d{2}-\d{2}$""")
            if (!fechaRegex.matches(fecha.text.toString())) {
                fecha.error = "Formato de fecha inválido (debe ser yyyy-MM-dd)"
                return false
            }
        }

        if (peso.text.isBlank()) {
            peso.error = "Campo requerido"
            return false
        } else {
            val pesoDouble = peso.text.toString().toDouble()
            if (pesoDouble <= 0) {
                peso.error = "El peso debe ser un número mayor a 0"
                return false
            }
        }

        if (esPresencial.equals("--Seleccionar--", ignoreCase = true)) {
            mostrarSnackbar("Porfavor especifique si es consulta presencial o no")
            return false
        }

        if (motivo.text.isBlank()) {
            motivo.error = "Campo requerido"
            return false
        }
        return true
    }
}