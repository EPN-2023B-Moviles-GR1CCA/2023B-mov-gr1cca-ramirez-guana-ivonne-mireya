package com.example.ExamenIIB

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class InicioMascota : AppCompatActivity() {
    val db = Firebase.firestore
    val mascotasO = db.collection("Mascota")
    var idItemSeleccionado = 0
    var adaptador: ArrayAdapter<Mascota>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_mascota)
        Log.i("ciclo-vida", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")
        listarMascotas()
        val btnAnadirMascota = findViewById<Button>(R.id.btn_crear_nueva_playlist)
        btnAnadirMascota.setOnClickListener {
            val intentAddMascota = Intent(this, CrearMascota::class.java)
            startActivity(intentAddMascota)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        Log.i("context-menu", "ID ${id}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var mascotaSeleccionado:Mascota = adaptador!!.getItem(idItemSeleccionado)!!

        return when (item.itemId) {
            R.id.mi_editar -> {
                Log.i("context-menu", "Edit position: ${mascotaSeleccionado.idMascota}")
                val abrirEditarMascota = Intent(this, EditarMascota::class.java)
                abrirEditarMascota.putExtra("PosMascota",mascotaSeleccionado)
                startActivity(abrirEditarMascota)
                return true
            }
            R.id.mi_eliminar -> {
                Log.i("context-menu", "Delete position: ${idItemSeleccionado}")
                mascotasO.document("${mascotaSeleccionado.idMascota}").delete()
                    .addOnSuccessListener {
                        Log.i("Eliminar-Mascota", "Exito")
                    }
                    .addOnFailureListener{
                        Log.i("Eliminar-Mascota","Fallido")
                    }
                listarMascotas()
                return true
            }

            R.id.mi_consulta -> {
                Log.i("context-menu", "Consultas: ${idItemSeleccionado}")
                val abrirInicioConsultas = Intent(this, InicioConsulta::class.java)
                abrirInicioConsultas.putExtra("PosMascota",mascotaSeleccionado)
                startActivity(abrirInicioConsultas)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun listarMascotas(){
        val lv_mascotas = findViewById<ListView>(R.id.lv_mascotas_lista)
        mascotasO.get().addOnSuccessListener{ result ->
            var mascotaLista = arrayListOf<Mascota>()
            for (document in result) {
                mascotaLista.add(
                    Mascota(
                        document.id.toString(),
                        document.get("nombreMascota").toString(),
                        document.get("especieMascota").toString(),
                        document.get("anioNacimiento").toString(),
                        document.get("autorMascota").toString(),
                        document.get("numConsultas").toString().toInt(),
                    )
                )
            }
            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                mascotaLista
            )
            lv_mascotas.adapter = adaptador
            adaptador!!.notifyDataSetChanged()
            registerForContextMenu(lv_mascotas)

        }.addOnFailureListener{
            Log.i("Error", "Creacion de lista de mascotas fallida")
        }
    }

}