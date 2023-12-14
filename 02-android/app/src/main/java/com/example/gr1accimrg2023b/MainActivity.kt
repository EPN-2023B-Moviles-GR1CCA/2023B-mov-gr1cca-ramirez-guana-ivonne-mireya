package com.example.gr1accimrg2023b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
         botonCicloVida
             .setOnClickListener{
                 irActividad(ACicloVida::class.java)
             }
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase )
        startActivity(intent)
        }
    }


