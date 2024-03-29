package com.example.gr1accimrg2023b

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Logica Negocio
                    val data = result.data
                    mostrarSnackbar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_list_view), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
            .show()
    }

    val callbackIntentImplicitoTelefono =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode === Activity.RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null, null, null
                        )
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Telefono ${telefono}")
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EbaseDeDatos.tablaEntrenador=EsqliteHelperEntrenador(this)//inicializar la base de datos
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
         botonCicloVida
             .setOnClickListener{
                 irActividad(ACicloVida::class.java)
             }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }


        val botonIntentImplicito = findViewById<Button>(
            R.id.btn_ir_intent_implicito)
        botonIntentImplicito
            .setOnClickListener {
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackIntentImplicitoTelefono.launch(intentConRespuesta)
            }

        val botonIntentExplicito = findViewById<Button>(
            R.id.btn_ir_intent_explicito)
        botonIntentExplicito
            .setOnClickListener {
                abrirActividadConParametros(
                    CIntentExplicitoParametros::class.java)
            }

        val botonSqlite =findViewById<Button>(R.id.btn_sqlite)
        botonSqlite
            .setOnClickListener {
                irActividad(ECrudEntrenador::class.java)
            }

        val botonRView =findViewById<Button>(R.id.btn_revcycler_view)
        botonSqlite
            .setOnClickListener {
                irActividad(FRecyclerView::class.java)
            }
        val botonGoogleMaps =findViewById<Button>(R.id.btn_google_maps)
        botonGoogleMaps
            .setOnClickListener {
                irActividad(GGoogIeMapsActivity::class.java)
            }

        val botonFireBaseUI =findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonFireBaseUI
            .setOnClickListener {
                irActividad(HFirebaseUIAuth::class.java)
            }

        val botonFirestore =findViewById<Button>(R.id.btn_intent_firestore)
        botonFirestore
            .setOnClickListener {
                irActividad(IFirestore::class.java)
            }


    }
    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        // Enviar parametros (solamente variables primitivas)
        intentExplicito.putExtra("nombre", "mireya")
        intentExplicito.putExtra("apellido", "ramirez")
        intentExplicito.putExtra("edad", 21)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase )
        startActivity(intent)
    }
}





