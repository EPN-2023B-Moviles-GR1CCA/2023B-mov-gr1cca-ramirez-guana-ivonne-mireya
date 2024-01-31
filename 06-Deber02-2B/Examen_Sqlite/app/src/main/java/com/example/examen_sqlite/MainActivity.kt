package com.example.examen_sqlite

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examen_sqlite.bdd.BDD
import com.example.examen_sqlite.model.Mascota
import com.example.examen_sqlite.repositorio.MascotasRepo
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    var mascotas = arrayListOf<Mascota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BDD.bddAplicacion = MascotasRepo(this)
        mascotas = BDD.bddAplicacion!!.obtenerMascotas()

        if(mascotas.size != 0){
            //listado de mascotas
            val listView = findViewById<ListView>(R.id.lv_list_mascotas)

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
        }

        val btnCrearMascota = findViewById<Button>(R.id.btnCrearMascota)
        btnCrearMascota
            .setOnClickListener {
                val intent = Intent(this, crear_Mascota::class.java)
                callbackContenidoIntentExplicito.launch(intent)
            }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_mascotas), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                val idMascota = mascotas.get(posicionItemSeleccionado).idMascota
                val nombre_Mascota = mascotas.get(posicionItemSeleccionado).nombre + " - " + mascotas.get(posicionItemSeleccionado).especie

                val extras = Bundle()
                extras.putString("idMascota", idMascota)
                extras.putString("nombreMascota", nombre_Mascota)
                irEdicionMascota(actualizar_Mascota::class.java, extras)
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSnackbar(mascotas.get(posicionItemSeleccionado).idMascota)
                val result: Boolean = abrirDialogo(mascotas.get(posicionItemSeleccionado).idMascota)
                if(result) true else

                    return false
            }
            R.id.mi_ver_consulta -> {
                val idMascota = mascotas.get(posicionItemSeleccionado).idMascota
                val nombre_Mascota = mascotas.get(posicionItemSeleccionado).nombre + " - " + mascotas.get(posicionItemSeleccionado).especie

                val extras = Bundle()
                extras.putString("idMascota", idMascota)
                extras.putString("nombreMascota", nombre_Mascota)
                irEdicionMascota(listar_Consulta::class.java, extras)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(idMascota: String): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar a esta mascota?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                val respuesta = BDD.bddAplicacion?.eliminarMascotaPorIdMascota(idMascota)

                if (respuesta == true) {
                    mostrarSnackbar("Mascota eliminada exitosamente")
                    cargarListaMascotas()
                    eliminacionExitosa = true
                } else {
                    mostrarSnackbar("No se pudo eliminar la mascota")
                    eliminacionExitosa = false
                }
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()

        return eliminacionExitosa
    }

    fun irEdicionMascota(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }

        callbackContenidoIntentExplicito.launch(intent)
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    cargarListaMascotas()
                    mostrarSnackbar("${data?.getStringExtra("message")}")
                }
            }
        }

    private fun cargarListaMascotas() {

        mascotas = BDD.bddAplicacion!!.obtenerMascotas()
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mascotas
        )
        val listView = findViewById<ListView>(R.id.lv_list_mascotas)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

}