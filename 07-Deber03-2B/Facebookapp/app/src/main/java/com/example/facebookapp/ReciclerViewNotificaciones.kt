package com.example.facebookapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReciclerViewNotificaciones(
    private val contexto: Notificaciones,
    private val lista: ArrayList<Notificacion>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<ReciclerViewNotificaciones.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val perfilTextView: ImageView
        val textoTextView: TextView
        val horaTextView: TextView

        init {
            perfilTextView = view.findViewById(R.id.imgPerfilNot)
            textoTextView = view.findViewById(R.id.tvNombreNot)
            horaTextView = view.findViewById(R.id.tvTiempoNot)
        }

    }
    // Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.notificaciones,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }
    // Setear los datos para la iteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notificacionActual = this.lista[position]

        holder.textoTextView.text = notificacionActual.texto
        holder.horaTextView.text = notificacionActual.horaNot
        // Load and set profile picture using the propic ID
        holder.perfilTextView.setImageResource(notificacionActual.perfil ?: R.drawable.perfil1)

    }

    // tamano del arreglo
    override fun getItemCount(): Int {
        return this.lista.size
    }
}