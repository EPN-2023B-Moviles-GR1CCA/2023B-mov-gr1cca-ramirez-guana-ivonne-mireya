package com.example.examen_1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.examen_1b.Model.Mascota


class crear_Mascota : AppCompatActivity() {


    companion object{
        var idSeleccionado = 0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)
        showListViewMascota()
        val nombre = findViewById<EditText>(R.id.editTextText_NombreMascota)
        val fechaNacimiento = findViewById<EditText>(R.id.editTextText_FechaNacimiento)
        val frecuenciaCardiaca = findViewById<EditText>(R.id.editTexFrecuencia)
        val especie = findViewById<EditText>(R.id.editTextText_Especie)

        val btncrearMascota = findViewById<Button>(R.id.btnCrearMascota)

        btncrearMascota.setOnClickListener {
            //Crear instancia
            val padre = Mascota(
                null,
                nombre.text.toString(),
                fechaNacimiento.text.toString(),
                frecuenciaCardiaca.text.toString(),
                especie.text.toString(), this
            )
            val  resultado = padre.insertMascota()

            if(resultado > 0){

                Toast.makeText(this,"Registro Guardado", Toast.LENGTH_LONG).show()
                cleanEditText()
                showListViewMascota()

            }else{
                Toast.makeText(this,"Error ", Toast.LENGTH_LONG).show()
            }
        }


    }


    //Menu contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_mascota, menu)
        // Obtener el id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idSeleccionado = id

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId){
            R.id.mi_editarmascota -> {
                irActividad(actualizar_Mascota::class.java)
                return true
            }R.id.mi_eliminarmascota->{
                abrirDialogo()
                return true

            }
            R.id.mi_verconsultas->{
                irActividad(ver_Consulta::class.java)
                return true

            }else-> super.onContextItemSelected(item)

        }

    }


    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar este mascota?")

        builder.setPositiveButton("SI") { dialog, which ->
            // Verificar que id
            if (idSeleccionado >= 0) {val padre = Mascota(null, "", "", "", "", this)
                val resultado = padre.deleteMascota(idSeleccionado )

                if (resultado > 0) {
                    Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                    runOnUiThread {
                        showListViewMascota()
                    }
                } else {
                    Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG).show()
                }

            } else {
                // Manejar el caso en que idSeleccionado no es válido
                Toast.makeText(this, "Selección no válida", Toast.LENGTH_LONG).show()
            }
        }

        builder.setNegativeButton("NO") { dialog, which ->

            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_LONG).show()
        }

        val dialogo = builder.create()
        dialogo.show()
    }
    fun showListViewMascota() {
        // ListView
        val mascota = Mascota(null, "", "", "", "", this)
        val listView = findViewById<ListView>(R.id.lvView_Mascota)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mascota.showMascotas()
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    fun cleanEditText(){
        val nombre = findViewById<EditText>(R.id.editTextText_NombreMascota)
        val fechaNacimiento = findViewById<EditText>(R.id.editTextText_FechaNacimiento)
        val frecuenciaCardiaca = findViewById<EditText>(R.id.editTexFrecuencia)
        val especie = findViewById<EditText>(R.id.editTextText_Especie)

        nombre.text.clear()
        fechaNacimiento.text.clear()
        frecuenciaCardiaca.text.clear()
        especie.text.clear()

        // la primer input
        nombre.requestFocus()
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}

