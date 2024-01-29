package com.example.examen_sqlite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.examen_sqlite.BDD.BDD
import com.example.examen_sqlite.Model.Consulta
import com.example.examen_sqlite.Repositorio.MascotasRepo
import com.google.android.material.snackbar.Snackbar

class listar_Consulta : AppCompatActivity() {
    var consultas = arrayListOf<Consulta>()
    //preguntar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_consulta)
        BDD.bddAplicacion = MascotasRepo(this)

        val idMascotaC = intent.extras?.getString("idMascota")
        val nombre_mascota = intent.extras?.getString("nombreMascota")

        findViewById<TextView>(R.id.tv_Consultas).setText(nombre_mascota)

        if(idMascotaC != null){
            consultas = BDD.bddAplicacion!!.obtenerConsultassPorMascota(idMascotaC)
            if(consultas.size != 0){
                val listView = findViewById<ListView>(R.id.lv_listados_consultas)

                val adaptador = ArrayAdapter(
                    this, // contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    consultas
                )

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }else{
                mostrarSnackbar("No existen consultas")
            }

            val btnCrearConsultas = findViewById<Button>(R.id.btn_crear_consulta)
            btnCrearConsultas
                .setOnClickListener {
                    val extras = Bundle()
                    extras.putString("idMascota", idMascotaC)
                    irActividad(crear_Consulta::class.java, extras)
                }
        }

    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.layout_listar_consulta), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun irActividad(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }

        callbackContenidoIntentExplicito.launch(intent)
    }
    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_consultas, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_consulta ->{
                val id = consultas.get(posicionItemSeleccionado).id
                val nombre_Consultas = consultas.get(posicionItemSeleccionado).recomendacion
                val codigoMascota = consultas.get(posicionItemSeleccionado).idMascotaC

                val extras = Bundle()
                extras.putString("id", id)
                extras.putString("codigoMascota", codigoMascota)
                extras.putString("nombreConsultas", nombre_Consultas)
                irActividad(actualizar_Consulta::class.java, extras)
                return true
            }
            R.id.mi_eliminar_consulta -> {
                val id = consultas.get(posicionItemSeleccionado).id
                val codigoMascota = consultas.get(posicionItemSeleccionado).idMascotaC

                val result: Boolean = abrirDialogo(id, codigoMascota)
                if(result) true else

                    return false
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(codigoConsultas: String, codigoMascota: String): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar esta consulta?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                val respuesta = BDD.bddAplicacion?.eliminarConsultasPoridYCodigoMascota(codigoConsultas, codigoMascota)

                if (respuesta == true) {
                    mostrarSnackbar("Consultas eliminado exitosamente")
                    cargarListaConsultass(codigoMascota)
                    eliminacionExitosa = true
                } else {
                    mostrarSnackbar("No se pudo eliminar esta consulta")
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

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    cargarListaConsultass("${data?.getStringExtra("codigoMascota")}")
                    mostrarSnackbar("${data?.getStringExtra("message")}")
                }
            }
        }

    private fun cargarListaConsultass(codigoMascota: String) {

        consultas = BDD.bddAplicacion!!.obtenerConsultassPorMascota(codigoMascota)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            consultas
        )
        val listView = findViewById<ListView>(R.id.lv_listados_consultas)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
}