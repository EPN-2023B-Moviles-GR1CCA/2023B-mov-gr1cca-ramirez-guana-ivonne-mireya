package com.example.examen_1b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examen_1b.Model.Consulta

class crear_Consulta : AppCompatActivity() {
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_consulta)

        val nombre = findViewById<EditText>(R.id.ed_nombreConsulta)
        nombre.requestFocus()
        val  motivo = findViewById<EditText>(R.id.ed_motivo)
        val peso = findViewById<EditText>(R.id.edt_peso)
        val esPresencial = findViewById<EditText>(R.id.edt_esPresencial)
        val idMascota = findViewById<EditText>(R.id.ed_idMascotaConsulta)

        val btnInsertar = findViewById<Button>(R.id.btn_InsertarConsulta)
        btnInsertar.setOnClickListener {

            val consulta: Consulta = Consulta(
                null,
                nombre.text.toString(),
                motivo.text.toString(),
                peso.text.toString(),
                esPresencial.text.toString(),
                idMascota.text.toString().toInt(),
                this
            )
            val resultado = consulta.InsertarConsulta()


            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL INSERTAR REGISTRO", Toast.LENGTH_LONG).show()
            }


        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_consulta, menu)
        // Obtener el id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado= id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId){
            R.id.mi_editarconsulta -> {
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminarconsultas -> {
                "${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun cleanEditText(){
        val nombre = findViewById<EditText>(R.id.ed_nombreConsulta)
        nombre.setText("")
        val  motivo = findViewById<EditText>(R.id.ed_motivo)
        motivo.setText("")

        val peso = findViewById<EditText>(R.id.edt_peso)
        peso.setText("")

        val esPresencial = findViewById<EditText>(R.id.edt_esPresencial)
        esPresencial.setText("")

        val idMascota = findViewById<EditText>(R.id.ed_idMascotaConsulta)
        idMascota.setText("")
    }




}
