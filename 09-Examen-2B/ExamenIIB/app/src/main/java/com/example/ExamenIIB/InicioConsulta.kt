package com.example.ExamenIIB

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InicioConsulta : AppCompatActivity() {

    var mascotaSeleccionado=Mascota("","","","","",0)
    val db = Firebase.firestore
    val mascota = db.collection("Mascota")
    var idItemSeleccionado = 0
    var adaptador: ArrayAdapter<Consulta>?=null
    var consultaSeleccionado:Consulta? = Consulta("","", "", "", 0, "", "")


    var resultAnadirConsulta = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null) {
                val data = result.data
                mascotaSeleccionado = intent.getParcelableExtra<Mascota>("PosMascota")!!
            }
        }

    }

    var resultEditarConsulta = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null) {
                val data = result.data
                mascotaSeleccionado = intent.getParcelableExtra<Mascota>("PosMascota")!!
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_consulta)
    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")
        mascotaSeleccionado = intent.getParcelableExtra<Mascota>("PosMascota")!!
        listViewConsultas()
        val txtPresencialMascota=findViewById<TextView>(R.id.tv_nombreP_C)
        txtPresencialMascota.setText("Mascota: "+mascotaSeleccionado.nombreMascota)

        val btnCrearConsulta = findViewById<Button>(R.id.btn_crear_consulta2)
        btnCrearConsulta.setOnClickListener {
            abrirActividadAddConsulta(CrearConsulta::class.java)
        }

        val btnVolverConsulta = findViewById<Button>(R.id.btn_volver_consulta)
        btnVolverConsulta.setOnClickListener {
            val intentAtrasConsulta = Intent(this, InicioMascota::class.java)
            startActivity(intentAtrasConsulta)
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
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSeleccionado = info.position
        Log.i("context-menu", "ID Consulta ${idItemSeleccionado}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        consultaSeleccionado = adaptador!!.getItem(idItemSeleccionado)
        return when (item.itemId) {
            R.id.mi_editarConsulta -> {
                Log.i("context-menu", "Edit position: ${idItemSeleccionado}")
                abrirActividadEditarConsulta(EditarConsulta::class.java)
                return true
            }
            R.id.mi_eliminarConsulta -> {
                Log.i("context-menu", "Delete position: ${idItemSeleccionado}")
                val playlistSubColeccion= mascota.document("${mascotaSeleccionado.idMascota}")
                    .collection("Consultas")
                    .document("${consultaSeleccionado!!.idConsulta}")
                    .delete()
                    .addOnSuccessListener {
                        Log.i("Eliminar-Consulta","Con exito")
                    }
                    .addOnFailureListener{
                        Log.i("Eliminar-Consulta","Fallido")
                    }
                listViewConsultas()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun listViewConsultas() {
        val playlistSubColeccion= mascota.document("${mascotaSeleccionado.idMascota}")
            .collection("Consulta")
            .whereEqualTo("idMascota","${mascotaSeleccionado.idMascota}")

        val consulta_lv = findViewById<ListView>(R.id.lv_consulta_lista)
        playlistSubColeccion.get().addOnSuccessListener { result ->
            var listaConsultas = arrayListOf<Consulta>()
            for(document in result){
                listaConsultas.add(
                    Consulta(
                        document.id.toString(),
                        document.data.get("idMascota").toString(),
                        document.data.get("nombreConsulta").toString(),
                        document.data.get("motivo").toString(),
                        document.data.get("peso").toString().toInt(),
                        document.data.get("recomendacion").toString(),
                        document.data.get("historialConsulta").toString()
                    )
                )
            }
            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                listaConsultas
            )
            consulta_lv.adapter=adaptador
            adaptador!!.notifyDataSetChanged()

            registerForContextMenu(consulta_lv)
        }
    }

    fun abrirActividadEditarConsulta(
        clase: Class<*>
    ) {
        val intentEditarConsulta = Intent(this, clase)
        intentEditarConsulta.putExtra("consulta", consultaSeleccionado)
        intentEditarConsulta.putExtra("posicionMascotaeditar",mascotaSeleccionado)
        resultEditarConsulta.launch(intentEditarConsulta)
    }

    fun abrirActividadAddConsulta(
        clase: Class<*>
    ) {
        val intentAddNewConsulta = Intent(this, clase)
        intentAddNewConsulta.putExtra("posicionMascota", mascotaSeleccionado)
        resultAnadirConsulta.launch(intentAddNewConsulta)
    }

}