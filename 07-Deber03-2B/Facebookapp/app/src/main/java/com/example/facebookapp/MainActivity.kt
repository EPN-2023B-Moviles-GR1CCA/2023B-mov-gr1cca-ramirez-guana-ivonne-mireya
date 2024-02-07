package com.example.facebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarRecyclerView()

        val botonInicio = findViewById<Button>(R.id.btnInicio)
        botonInicio
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }
        val botonNotificaciones = findViewById<Button>(R.id.btnNotificaciones)
        botonNotificaciones
            .setOnClickListener {
                irActividad(Notificaciones::class.java)
            }


    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adaptador = RecyclerViewAdaptador(
            this, // Contexto
            BaseDatos.arregloPosts, // Arreglo datos
            recyclerView // Recycler view
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}