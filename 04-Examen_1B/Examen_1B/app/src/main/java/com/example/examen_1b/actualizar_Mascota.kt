package com.example.examen_1b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.examen_1b.Model.Mascota

class actualizar_Mascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_mascota)

        val idPadre = crear_Mascota.idSeleccionado
        var  mascota = Mascota(null,"","","","",this)
        mascota = mascota.getMascotaById(idPadre)


        var id = findViewById<EditText>(R.id.tv_updidMascota)
        id.setText(mascota.getidMascota().toString())

        var nombre = findViewById<EditText>(R.id.tv_updNombreMascota)
        nombre.setText(mascota.getnombreMascota())


        var fechaNacimiento = findViewById<EditText>(R.id.tv_updFechaNMascota)
        fechaNacimiento.setText(mascota.getfechaNacimiento())


        var frecuenciaCardiaca = findViewById<EditText>(R.id.tv_updfecuenciaCardiacaMascota)

        frecuenciaCardiaca.setText(mascota.getfrecuenciaCardiaca())


        var especie = findViewById<EditText>(R.id.tv_updespecieMascota)
        especie.setText(mascota.getespecie())


        val btnActualizarMascota = findViewById<Button>(R.id.btn_updMascota)

        btnActualizarMascota.setOnClickListener {
            mascota.setnombreMascota(nombre.text.toString())
            mascota.setfechaNacimiento(fechaNacimiento.text.toString())
            mascota.setfrecuenciaCardiaca(frecuenciaCardiaca.text.toString())
            mascota.setespecie(especie.text.toString())

            val resultado = mascota.updateMascota()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()

                cleanEditText()


                val intent = Intent(this, crear_Mascota::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun cleanEditText(){
        val nombre = findViewById<EditText>(R.id.tv_updNombreMascota)
        val fechaNacimiento = findViewById<EditText>(R.id.tv_updFechaNMascota)
        val frecuenciaCardiaca = findViewById<EditText>(R.id.tv_updfecuenciaCardiacaMascota)
        val especie = findViewById<EditText>(R.id.tv_updespecieMascota)

        nombre.text.clear()
        fechaNacimiento.text.clear()
        frecuenciaCardiaca.text.clear()
        especie.text.clear()

        nombre.requestFocus()
    }
}