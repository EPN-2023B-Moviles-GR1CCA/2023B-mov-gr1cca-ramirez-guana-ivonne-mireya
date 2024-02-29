package com.example.ExamenIIB

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CrearMascota : AppCompatActivity() {

    val db = Firebase.firestore
    val mascota = db.collection("Mascota")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)
    }

    override fun onStart() {
        super.onStart()

        var txtInNombreP = findViewById<TextInputEditText>(R.id.txtIn_nombreP_editar2)
        var txtInEspecie = findViewById<TextInputEditText>(R.id.txtIn_especieP_editar)
        var txtInanioNacimiento = findViewById<TextInputEditText>(R.id.txtIn_anioP_editar)
        var txtInFrecuencia = findViewById<TextInputEditText>(R.id.txtIn_autorP_editar)
        var txtInNivelA = findViewById<TextInputEditText>(R.id.txtIn_numCP_editar)

        var btnCrearMascota = findViewById<Button>(R.id.btn_guardar_cambios)
        btnCrearMascota.setOnClickListener {
            var playlist = hashMapOf(
                "nombreMascota" to txtInNombreP.text.toString(),
                "especieMascota" to txtInEspecie.text.toString(),
                "anioNacimiento" to txtInanioNacimiento.text.toString(),
                "frecuencia" to txtInFrecuencia.text.toString(),
                "NivelAdiestramiento" to txtInNivelA.text.toString()
            )

            mascota.add(playlist).addOnSuccessListener {
                txtInNombreP.text!!.clear()
                txtInEspecie.text!!.clear()
                txtInanioNacimiento.text!!.clear()
                txtInFrecuencia.text!!.clear()
                txtInNivelA.text!!.clear()
                Toast.makeText(this,"Mascota registrado con exito", Toast.LENGTH_SHORT).show();
                Log.i("Crear-Mascota","Success")
            }.addOnSuccessListener {
                Log.i("Crear-Mascota","Failed")
            }


            val intentAddSucces = Intent(this, InicioMascota::class.java)
            startActivity(intentAddSucces)
        }

        var btnCancelarMascota = findViewById<Button>(R.id.btn_cancelar_editar)
        btnCancelarMascota.setOnClickListener {
            val intentAddCancel = Intent(this, InicioMascota::class.java)
            startActivity(intentAddCancel)
        }
    }

}