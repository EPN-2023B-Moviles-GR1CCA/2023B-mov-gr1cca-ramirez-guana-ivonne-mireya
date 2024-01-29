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
import com.example.examen_sqlite.BDD.BDD
import com.example.examen_sqlite.Model.Consulta
import com.google.android.material.snackbar.Snackbar

class crear_Consulta : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_consulta)
        val idMascotaC = intent.extras?.getString("idMascota")


        val spinnerEsPresencial = findViewById<Spinner>(R.id.sp_esPresencial)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_esta_Esterilizado,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerEsPresencial.adapter = adaptador
        

        val btnGuardarConsulta = findViewById<Button>(R.id.btn_crear_consulta)
        btnGuardarConsulta
            .setOnClickListener {
                try {
                    val id = findViewById<EditText>(R.id.et_id)
                    val nHistorial = findViewById<EditText>(R.id.et_nHistorial)
                    val fechaConsulta = findViewById<EditText>(R.id.et_fecha)
                    val esPresencial = spinnerEsPresencial.selectedItem.toString()
                    val motivo = findViewById<EditText>(R.id.et_motivo)
                    val peso = findViewById<EditText>(R.id.et_peso)
                    val recomendacion = findViewById<EditText>(R.id.et_recomendacion)

                    id.error = null
                    recomendacion.error = null
                    nHistorial.error = null
                    peso.error = null
                    fechaConsulta.error = null
//preguntar motivo.toString()
                    if(validarCampos(id,nHistorial, fechaConsulta, esPresencial,
                            motivo.toString(),  peso,recomendacion)){
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

                        val respuesta = BDD
                            .bddAplicacion!!.crearConsultas(newConsulta)

                        if(respuesta) {

                            val data = Intent()
                            data.putExtra("codigoMascota", idMascotaC)
                            data.putExtra("message", "La consulta se ha creado exitosamente")
                            setResult(RESULT_OK, data)
                            finish()
                        }else{
                            mostrarSnackbar("Hubo un problema al crear la consulta")
                        }
                    }

                } catch (e: Exception) {
                    Log.e("Error", "Error en la aplicación", e)
                }
            }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.layout_crear_consulta), //view
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
            mostrarSnackbar("Porfavor especifique si es el consulta del día o no")
            return false
        }

        if(motivo.equals("--Seleccionar--", ignoreCase = true)){
            mostrarSnackbar("Porfavor especifique el tipo de cocina")
            return false
        }
        return true
    }
}