package com.example.examen_1b

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.examen_1b.BDD.BDD


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val dbHelper: BDD = BDD(this)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        if (db != null) {

        } else {
            Toast.makeText(this, "ERROR AL CREAR LA BDD", Toast.LENGTH_LONG).show()
        }
        irActividad(crear_Mascota::class.java)

    }

    //Ir a la actividad
    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


}
