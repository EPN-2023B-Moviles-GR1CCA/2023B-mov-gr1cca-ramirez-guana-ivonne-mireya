package com.example.examen_1b

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.examen_1b.Model.Consulta
import com.example.examen_1b.Model.Mascota

class ver_Consulta : AppCompatActivity() {

    companion object {
        var idConsultaSeleccionada = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_consulta)


        val idMascota = crear_Mascota.idSeleccionado
        var idMascotaAux =  Mascota(null, "", "","","",this)

        val textViewPadre = findViewById<TextView>(R.id.tv_padreVerConsultas)
        textViewPadre.text = "Id:" + idMascotaAux.getMascotaById(idMascota).getnombreMascota()

        val btnCrearConsultas = findViewById<Button>(R.id.btn_CrearConsultas)
        btnCrearConsultas.setOnClickListener {
            irActividad(crear_Mascota::class.java)
        }

        showListView(idMascota)


    }

    fun  showListView(id:Int){
        val objConsulta = Consulta(null,"","","","",0,this)
        val listviewConsultas = findViewById<ListView>(R.id.lv_Consulta)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            objConsulta.mostrarConsulta(id)
        )
        listviewConsultas.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listviewConsultas)
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
        idConsultaSeleccionada = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId){
            R.id.mi_editarconsulta ->{
                irActividad(actualizar_Consulta::class.java)
                return true
            }
            R.id.mi_eliminarconsultas->{
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }



    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar esta consulta?")
        builder.setPositiveButton(
            "SI",
            DialogInterface.OnClickListener { dialog, which ->
                val consulta = Consulta(null, "", "", "", "", 0, this)
                val resultado = consulta.deleteConsulta(idConsultaSeleccionada)
                if (resultado > 0) {
                    Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG).show()
                }
                val idMascota = crear_Mascota.idSeleccionado
                showListView(idMascota)
            }
        )
        builder.setNegativeButton(
            "NO",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }



    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


}


