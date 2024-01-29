package com.example.examen_sqlite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.examen_sqlite.BDD.BDD
import com.example.examen_sqlite.Model.Consulta
import com.google.android.material.snackbar.Snackbar

class actualizar_Consulta : AppCompatActivity() {
    //preguntar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_consulta)
        val idMascotaC = intent.extras?.getString("codigoMascota")
        val idConsultas = intent.extras?.getString("id")
        val recomendacion_consulta = intent.extras?.getString("recomendacionConsultas")


        val spinnerEsPresencial = findViewById<Spinner>(R.id.sp_esPresencial)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_esta_Esterilizado,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerEsPresencial.adapter = adaptador


        findViewById<TextView>(R.id.tv_Consultas).setText("Consultas a modificar: ${recomendacion_consulta}")

        if (idConsultas != null && idMascotaC != null) {

            val consultaEdicion = BDD.bddAplicacion!!.consultarConsultasPoridYMascota(idConsultas, idMascotaC)

            val id = findViewById<EditText>(R.id.et_id)
            val recomendacion = findViewById<EditText>(R.id.et_recomendacion)
            val fechaConsulta = findViewById<EditText>(R.id.et_fecha)
            val nHistorial = findViewById<EditText>(R.id.et_nHistorial)
            val peso = findViewById<EditText>(R.id.et_peso)

            id.setText(consultaEdicion.id)
            recomendacion.setText(consultaEdicion.recomendacion)
            fechaConsulta.setText(consultaEdicion.fechaConsulta)
            nHistorial.setText(consultaEdicion.nHistorial.toString())
            peso.setText(consultaEdicion.peso.toString())


            val esPresencialArray = resources.getStringArray(R.array.items_esta_Esterilizado)

            val esPresencialPosition = if (consultaEdicion.esPresencial) {
                esPresencialArray.indexOf("Si")
            } else {
                esPresencialArray.indexOf("No")
            }

            spinnerEsPresencial.setSelection(esPresencialPosition)


           // preguntae si borrar
        // val motivoArray = resources.getStringArray(R.array.items_tipo_cocina)

           // val motivoPosition = motivoArray.indexOf(consultaEdicion.motivo)
            //if (motivoPosition != -1) {
             //   spinnerTipoCocina.setSelection(motivoPosition)
           // }

        }


        val btnGuardarConsultas = findViewById<Button>(R.id.btn_crear_consulta)
        btnGuardarConsultas
            .setOnClickListener {
                try {
                    val id = findViewById<EditText>(R.id.et_id)
                    val recomendacion = findViewById<EditText>(R.id.et_recomendacion)
                    val fechaConsulta = findViewById<EditText>(R.id.et_fecha)
                    val esPresencial = spinnerEsPresencial.selectedItem.toString()
                    val motivo = findViewById<EditText>(R.id.et_motivo)
                    val nHistorial = findViewById<EditText>(R.id.et_nHistorial)
                    val peso = findViewById<EditText>(R.id.et_peso)


                    id.error = null
                    recomendacion.error = null
                    nHistorial.error = null
                    peso.error = null
                    fechaConsulta.error = null

                    if(validarCampos(id,nHistorial, fechaConsulta, esPresencial,
                            motivo.toString(),  peso,recomendacion)){
                        val esEsPresencial = esPresencial.equals("Si")
                        val datosActualizados = Consulta(
                            id.text.toString(),
                            nHistorial.text.toString().toInt(),
                            fechaConsulta.text.toString(),
                            esEsPresencial,
                            motivo.text.toString(),
                            peso.text.toString().toDouble(),
                            recomendacion.text.toString(),
                            idMascotaC!!
                        )

                        val respuesta = BDD
                            .bddAplicacion!!.actualizarConsultasPoridYCodigoMascota(datosActualizados)

                        if (respuesta) {

                            val data = Intent()
                            data.putExtra("codigoMascota", idMascotaC)
                            data.putExtra("message", "Los datos de la consulta se han actualizado exitosamente")
                            setResult(RESULT_OK, data)
                            finish()
                        } else {
                            mostrarSnackbar("Hubo un problema al actualizar los datos")
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
                findViewById(R.id.layout_actualizar_consulta), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun validarCampos(id: EditText, recomendacion: EditText, fecha: EditText, esPresencial: String, motivo: String, nHistorial: EditText, peso: EditText): Boolean{
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
            if (nHistorialInt < 3) {
                nHistorial.error = "La cantidad de productos debe ser un número mayor o igual a 3"
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

        if(peso.text.isBlank()){
            peso.error = "Campo requerido"
            return false
        }else{
            val pesoDouble = peso.text.toString().toDouble()
            if (pesoDouble <=0) {
                peso.error = "El peso debe ser un número mayor a 0"
                return false
            }
        }

        if(esPresencial.equals("--Seleccionar--", ignoreCase = true)){
            mostrarSnackbar("Porfavor especifique si es el plato del día o no")
            return false
        }

        if(motivo.equals("--Seleccionar--", ignoreCase = true)){
            mostrarSnackbar("Porfavor especifique el tipo de cocina")
            return false
        }
        return true
    }
}