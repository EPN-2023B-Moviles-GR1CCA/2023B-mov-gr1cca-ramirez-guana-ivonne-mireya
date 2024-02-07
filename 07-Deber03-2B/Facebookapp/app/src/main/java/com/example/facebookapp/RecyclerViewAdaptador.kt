package com.example.facebookapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdaptador(
    private val contexto: MainActivity,
    private val lista: ArrayList<ModeloPost>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<RecyclerViewAdaptador.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val likesTextView: TextView
        val coraTextView: TextView
        val divTextView: TextView
        val enaTextView: TextView
        val molesTextView: TextView
        val trisTextView: TextView

        val comentarioTextViews: TextView
        val propicTextView: ImageView
        val postpicTextView: ImageView
        val nombreTextView: TextView
        val tiempoTextView: TextView
        val estadoTextView: TextView
        var numeroLikes = 0
        init {
            nombreTextView = view.findViewById(R.id.tv_nombre)
            comentarioTextViews = view.findViewById(R.id.tvComentario)
            propicTextView = view.findViewById(R.id.imgView_proPic)
            postpicTextView = view.findViewById(R.id.imgView_postPic)
            likesTextView = view.findViewById(R.id.likes)
            coraTextView = view.findViewById(R.id.corazones)
            divTextView = view.findViewById(R.id.divertidos)
            enaTextView = view.findViewById(R.id.enamorados)
            molesTextView = view.findViewById(R.id.molestos)
            trisTextView = view.findViewById(R.id.tristes)
            tiempoTextView = view.findViewById(R.id.tv_tiempo)
            estadoTextView = view.findViewById(R.id.tvStatus)
        }

    }
    // Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.posts,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }
    // Setear los datos para la iteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val postActual = this.lista[position]

        holder.nombreTextView.text = postActual.nombre
        holder.comentarioTextViews.text = postActual.comentariosCompartidos.toString()
        holder.likesTextView.text = postActual.likes.toString() // Set likes count
        holder.coraTextView.text = postActual.corazon.toString() // Set corazon count
        holder.divTextView.text = postActual.divertido.toString() // Set divertido count
        holder.enaTextView.text = postActual.enamorado.toString() // Set enamorado count
        holder.molesTextView.text = postActual.molesto.toString() // Set molesto count
        holder.trisTextView.text = postActual.triste.toString() // Set triste count
        // Load and set profile picture using the propic ID
        holder.propicTextView.setImageResource(postActual.propic ?: R.drawable.perfil1)

        // Load and set post picture using the postpic ID
        holder.postpicTextView.setImageResource(postActual.postpic ?: R.drawable.frase)

        holder.tiempoTextView.text = postActual.tiempo
        holder.estadoTextView.text = postActual.estado
    }

    // tamano del arreglo
    override fun getItemCount(): Int {
        return this.lista.size
    }
}