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
import com.example.examen_sqlite.Model.Mascota
import com.google.android.material.snackbar.Snackbar

class crear_Mascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)
        val spinnerEstaEsterilizado = findViewById<Spinner>(R.id.sp_esta_Esterilizado)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_esta_Esterilizado,
            android.R.layout.simple_spinner_item
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerEstaEsterilizado.adapter = adaptador


        val btnGuardarMascota = findViewById<Button>(R.id.btn_guardar_mascota)
        btnGuardarMascota
            .setOnClickListener {
                try {
                    val idMascota = findViewById<EditText>(R.id.txt_idMascota)
                    val nombre = findViewById<EditText>(R.id.txt_nombre)
                    val especie = findViewById<EditText>(R.id.txt_especie)
                    val nivelAdiestramiento = findViewById<EditText>(R.id.txt_nivelAdiestramiento)
                    val fechaNacimiento = findViewById<EditText>(R.id.txt_fecha)
                    val frecuenciaCardiaca = findViewById<EditText>(R.id.txt_frecuenciaCardiaca)
                    val estaEsterilizado = spinnerEstaEsterilizado.selectedItem.toString()


                    idMascota.error = null
                    nombre.error = null
                    especie.error = null
                    nivelAdiestramiento.error = null
                    fechaNacimiento.error = null
                    frecuenciaCardiaca.error = null

                    if(validarCampos(idMascota, nombre, especie, nivelAdiestramiento, fechaNacimiento, frecuenciaCardiaca, estaEsterilizado)){
                        val esPrincipal = estaEsterilizado.equals("Si")
                        val newMascota = Mascota(
                            idMascota.text.toString(),
                            nombre.text.toString(),
                            especie.text.toString(),
                            nivelAdiestramiento.text.toString().toInt(),
                            fechaNacimiento.text.toString(),
                            frecuenciaCardiaca.text.toString().toDouble(),
                            esPrincipal
                        )

                        val respuesta = BDD
                            .bddAplicacion!!.crearMascota(newMascota)

                        if(respuesta) {

                            val data = Intent()
                            data.putExtra("message", "El mascota se ha creado exitosamente")
                            setResult(RESULT_OK, data)
                            finish()
                        }else{
                            mostrarSnackbar("Hubo un problema en la creacion del mascota")
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
                findViewById(R.id.layout_crear_mascota),
                texto,
                Snackbar.LENGTH_LONG
            )
            .show()
    }

    fun validarCampos(idMascota: EditText, nombre: EditText, especie: EditText, nivelAdiestramiento: EditText, fecha: EditText, frecuenciaCardiaca: EditText, estaEsterilizado: String): Boolean{
        if (idMascota.text.isBlank()) {
            idMascota.error = "Campo requerido"
            return false
        }

        if (nombre.text.isBlank()) {
            nombre.error = "Campo requerido"
            return false
        }

        if(especie.text.isBlank()){
            especie.error = "Campo requerido"
            return false
        }

        if (nivelAdiestramiento.text.isBlank()) {
            nivelAdiestramiento.error = "Campo requerido"
            return false
        } else {
            val nivelAdiestramientoInt = nivelAdiestramiento.text.toString().toInt()
            if (nivelAdiestramientoInt < 0) {
                nivelAdiestramiento.error = "La nivelAdiestramiento debe ser entre 0 a 5"
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

        if(frecuenciaCardiaca.text.isBlank()){
            frecuenciaCardiaca.error = "Campo requerido"
            return false
        }else{
            val frecuenciaCardiacaDouble = frecuenciaCardiaca.text.toString().toDouble()
            if (frecuenciaCardiacaDouble <=0) {
                frecuenciaCardiaca.error = "El frecuenciaCardiaca debe ser un número mayor a 0"
                return false
            }
        }

        if(estaEsterilizado.equals("--Seleccionar--", ignoreCase = true)){
            mostrarSnackbar("Porfavor especifique si el mascota es principal o no")
            return false
        }
        return true
    }
}