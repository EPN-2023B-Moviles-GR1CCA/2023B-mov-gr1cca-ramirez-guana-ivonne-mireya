package com.example.examen_1b

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examen_1b.Model.Consulta

class actualizar_Consulta : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_consulta)

        val idConsulta = ver_Consulta.idConsultaSeleccionada
        var consulta = Consulta(null, "","","","",0, this)
        consulta = consulta.getConsultaById(idConsulta)

        var id  = findViewById<EditText>(R.id.idConsulta)
        id.setText(consulta.getidConsulta().toString())

        var nombre =  findViewById<EditText>(R.id.et_nombreUPDConsulta)
        nombre.setText(consulta.getnombreConsulta())

        var motivo = findViewById<EditText>(R.id.et_motivoUDPConsulta)
        motivo.setText(consulta.getmotivo())

        var peso = findViewById<EditText>(R.id.et_tpesoUPDConsulta)
        peso.setText(consulta.getpeso())

        var esPresencial = findViewById<EditText>(R.id.et_EsPresencialUPDConsulta)
        esPresencial.setText(consulta.getesPresencial())

        var idMascota = findViewById<EditText>(R.id.et_idMascotaUPD_Consulta)
        idMascota.setText(consulta.getidMascota().toString())

        val btnactualizar_Consulta = findViewById<Button>(R.id.btn_actualizarConsulta)
        btnactualizar_Consulta.setOnClickListener {

            consulta.setnombreConsulta(nombre.text.toString())
            consulta.setmotivo(motivo.text.toString())
            consulta.setpeso(peso.text.toString())
            consulta.setesPresencial(esPresencial.text.toString())
            consulta.setidMascota(idMascota.text.toString().toInt())

            val resultado = consulta.updateConsulta()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }



    fun cleanEditText(){
        val nombre = findViewById<EditText>(R.id.ed_nombreConsulta)
        nombre.setText("")
        val  motivo = findViewById<EditText>(R.id.ed_motivo)
        motivo.setText("")

        val peso = findViewById<EditText>(R.id.edt_peso)
        peso.setText("")

        val Presencial = findViewById<EditText>(R.id.edt_esPresencial)
        Presencial.setText("")

        val idMascota = findViewById<EditText>(R.id.ed_idMascotaConsulta)
        idMascota.setText("")
    }
}

