package com.example.ExamenIIB

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarMascota : AppCompatActivity() {

    var mascotaSeleccionado = Mascota("", "", "", "", "", 0)
    val db = Firebase.firestore
    val mascotas = db.collection("Mascota")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ciclo-vida", "onCreate")
        setContentView(R.layout.activity_editar_mascota)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        mascotaSeleccionado = intent.getParcelableExtra<Mascota>("PosMascota")!!

        val editarNombreP = findViewById<TextInputEditText>(R.id.txtIn_nombreP_editar2)
        val editarEspecie = findViewById<TextInputEditText>(R.id.txtIn_especieP_editar2)
        val editaranioNacimiento = findViewById<TextInputEditText>(R.id.txtIn_anioP_editar2)
        val editarFrecuencialaylist = findViewById<TextInputEditText>(R.id.txtIn_autorP_editar2)
        val editarNumC = findViewById<TextInputEditText>(R.id.txtIn_numCP_editar2)

        editarNombreP.setText(mascotaSeleccionado.nombreMascota)
        editarEspecie.setText(mascotaSeleccionado.especieS.toString())
        editaranioNacimiento.setText(mascotaSeleccionado.anioNacimiento.toString())
        editarFrecuencialaylist.setText(mascotaSeleccionado.frecuencia.toString())
        editarNumC.setText(mascotaSeleccionado.NivelAdiestramiento.toString())


        val btnGuardarCambios = findViewById<Button>(R.id.btn_guardar_cambios2)
        btnGuardarCambios.setOnClickListener {
            mascotas.document("${mascotaSeleccionado.idMascota}").update(
                "nombreMascota" , editarNombreP.text.toString(),
                "especieMascota" , editarEspecie.text.toString(),
                "anioNacimiento" , editaranioNacimiento.text.toString(),
                "frecuencia" , editarFrecuencialaylist.text.toString(),
                "NivelAdiestramiento" , editarNumC.text.toString()
            )
            Toast.makeText(this,"Mascota actualizado exitosamente", Toast.LENGTH_SHORT).show()

            val intentEditSucces = Intent(this, InicioMascota::class.java)
            startActivity(intentEditSucces)
        }

        val btnCancelarEditar = findViewById<Button>(R.id.btn_cancelar_editar2)
        btnCancelarEditar.setOnClickListener{
            val intentEditCancel = Intent(this, InicioMascota::class.java)
            startActivity(intentEditCancel)
        }

    }

}