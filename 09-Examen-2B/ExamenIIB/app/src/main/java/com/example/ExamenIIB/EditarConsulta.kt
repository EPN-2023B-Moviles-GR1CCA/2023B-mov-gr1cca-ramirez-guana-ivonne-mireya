package com.example.ExamenIIB

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarConsulta : AppCompatActivity() {

    var mascotaSeleccionado = Mascota("", "", "", "", "", 0)
    var consultaSeleccionado = Consulta("","", "", "", 0, "", "")
    val db = Firebase.firestore
    val mascotasO = db.collection("Mascota")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_consulta)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        mascotaSeleccionado = intent.getParcelableExtra<Mascota>("posicionMascotaeditar")!!
        consultaSeleccionado = intent.getParcelableExtra<Consulta>("consulta")!!

        val txtPresencialC = findViewById<TextInputEditText>(R.id.txtIn_nombreC_editar)
        val txtmotivoC = findViewById<TextInputEditText>(R.id.txtIn_motivoC_editar)
        val txtpesoC = findViewById<TextInputEditText>(R.id.txtIn_pesoC_editar)
        val txtrecomendacionC = findViewById<TextInputEditText>(R.id.txtIn_recomendacionC_editar)
        val txthistorialC = findViewById<TextInputEditText>(R.id.txtIn_anioC_editar)

                txtPresencialC.setText(consultaSeleccionado.esPresencial)
                txtmotivoC.setText(consultaSeleccionado.motivo)
                txtpesoC.setText(consultaSeleccionado.peso.toString())
                txtrecomendacionC.setText(consultaSeleccionado.recomendaciones)
                txthistorialC.setText(consultaSeleccionado.historialConsulta)


        val btnEditarConsulta = findViewById<Button>(R.id.btn_editar_consulta)
        btnEditarConsulta.setOnClickListener {
            mascotasO.document("${mascotaSeleccionado.idMascota}")
                .collection("Consulta")
                .document("${consultaSeleccionado.historial_Consulta}")
                .update(
                    "esPresencial" , txtPresencialC.text.toString(),
                    "motivo" , txtmotivoC.text.toString(),
                    "peso" , txtpesoC.text.toString(),
                    "recomendaciones" , txtrecomendacionC.text.toString(),
                    "historial Consulta" , txthistorialC.text.toString()
                )

            Toast.makeText(this,"Consulta actualizada exitosamente", Toast.LENGTH_SHORT).show()
            val intentEditSucces = Intent(this, InicioConsulta::class.java)
            startActivity(intentEditSucces)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_consulta_editar)
        btnCancelar.setOnClickListener{
            respuesta()
        }

    }

    fun respuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("posicionMascotaeditar",mascotaSeleccionado)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

}