package com.example.examen_1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar


class mascota_Creada : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota_creada)


        //spinner-si no
        val spinnerMascota = findViewById<Spinner>(R.id.sp_Esta_Estilirizado)

        val adaptador = ArrayAdapter.createFromResource(
            this,
            R.array.items_si_no,
            android.R.layout.simple_list_item_1

        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerMascota.adapter = adaptador

        val botonGuardarMascota = findViewById<Button>(R.id.bt_Guardar)
        botonGuardarMascota.setOnClickListener {
            try {
               /* val id = findViewById<EditText>(R.id.txt_codigoUnico)
                val nombre = findViewById<EditText>(R.id.txt_nombre)
                val apellido = findViewById<EditText>(R.id.txt_apellido)
                val edad = findViewById<EditText>(R.id.txt_edad)
                val fechaContratacion = findViewById<EditText>(R.id.txt_fecha)
                val salario = findViewById<EditText>(R.id.txt_salario)
                val isMainChef = spinnerChefPrincipal.selectedItem.toString()*/


                // Limpiar errores anteriores
                //codigoUnico.error = null
              //  nombre.error = null
               /* apellido.error = null
                edad.error = null
                fechaContratacion.error = null
                salario.error = null

                if (validarCampos(
                        codigoUnico,
                        nombre,
                        apellido,
                        edad,
                        fechaContratacion,
                        salario,
                        isMainChef
                    )
                ) {*/
                   /* val esPrincipal = isMainChef.equals("Si")
                    val newChef = Cocinero(
                        codigoUnico.text.toString(),
                        nombre.text.toString(),
                        apellido.text.toString(),
                        edad.text.toString().toInt(),
                        fechaContratacion.text.toString(),
                        salario.text.toString().toDouble(),
                        esPrincipal
                    )

                    val respuesta = BDD
                        .tablaCocinero!!.crearCocinero(newChef)

                    if (respuesta) {
                        mostrarSnackbar("El cocinero se ha creado exitosamente")
                    } else {
                        mostrarSnackbar("Hubo un problema en la creacion del cocinero")
                    }*/
                //}

            } catch (e: Exception) {
                Log.e("Error", "Error en la aplicación", e)
            }

        }


    }



    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_Mascota), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }


}