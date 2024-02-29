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

class CrearConsulta : AppCompatActivity() {

    var mascotaSeleccionado = Mascota("", "", "", "", "", 0)
    val db = Firebase.firestore
    val mascotas = db.collection("Mascota")
    val consultas = db.collection("Consulta")
    var idConsultaSelect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ciclo-vida","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_consulta)
    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida","onStart")

        mascotaSeleccionado = intent.getParcelableExtra<Mascota>("posicionMascota")!!
        val playlistSubColeccion = mascotas.document("${mascotaSeleccionado.idMascota}")
            .collection("Consulta")

        var txtPresencial = findViewById<TextInputEditText>(R.id.txtIn_nombreC_crear)
        var txtmotivo = findViewById<TextInputEditText>(R.id.txtIn_motivoC_crear)
        var txtPeso = findViewById<TextInputEditText>(R.id.txtIn_pesoC_crear)
        var txtrecomen= findViewById<TextInputEditText>(R.id.txtIn_recomendacionC_crear)
        var txthistorialConsulta = findViewById<TextInputEditText>(R.id.txtIn_anioC_crear)

        Log.i("posMascota", "${mascotaSeleccionado.idMascota}")

        var btnAddConsulta= findViewById<Button>(R.id.btn_crear_consulta)
        btnAddConsulta.setOnClickListener {
            var consulta = hashMapOf(
                "idMascota" to mascotaSeleccionado.idMascota,
                "esPresencial" to txtPresencial.text.toString(),
                "motivo" to txtmotivo.text.toString(),
                "peso" to txtPeso.text.toString(),
                "recomendaciones" to txtrecomen.text.toString(),
                "historial Consulta" to txthistorialConsulta.text.toString()
            )

            playlistSubColeccion.add(consulta).addOnSuccessListener {
                Toast.makeText(this, "Consulta registrada exitosamente", Toast.LENGTH_SHORT).show();
                Log.i("Crear-Consulta", "Con exito")
            }.addOnFailureListener {
                Log.i("Crear-Consulta", "Fallido")
            }

            val intentAddSucces = Intent(this, InicioConsulta::class.java)
            startActivity(intentAddSucces)
        }

        var btnCancelarConsulta = findViewById<Button>(R.id.btn_cancelar_consulta_crear)
        btnCancelarConsulta.setOnClickListener {
            respuesta()
        }
    }

    fun respuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("posMascota", mascotaSeleccionado)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

}