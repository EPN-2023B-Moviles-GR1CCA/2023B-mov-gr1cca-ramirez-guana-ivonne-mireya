package com.example.examen_1b

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.examen_1b.BDD.BDD_Mascotas
import com.example.examen_1b.model.Mascota
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {
    var mascotas = arrayListOf<Mascota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        BDDM.tablaMascota = BDD_Mascotas(this)
        mascotas = BDDM.tablaMascota!!.obtenerMascota()

        mostrarSnackbar(mascotas.size.toString())
        /*if(mascotas.size != 0){
            //listado de mascotas
            val listView = findViewById<ListView>(R.id.ls_Mascota)

            val adaptador = ArrayAdapter(
                this, // contexto
                android.R.layout.simple_list_item_1, // como se va a ver (XML)
                mascotas
            )

            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)
        }else{
            mostrarSnackbar("No existen mascotas")
        }*/
        
        
        
        //mascota
        val btnCrear = findViewById<Button>(R.id.btn_Crear)
        btnCrear
            .setOnClickListener {
                irActividad(mascota_Editada::class.java)
            }
    }

    //mascota
    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.constraint_Mascota), //view
                texto,
                Snackbar.LENGTH_LONG //TIEMPO
            ).show()

    }


}
