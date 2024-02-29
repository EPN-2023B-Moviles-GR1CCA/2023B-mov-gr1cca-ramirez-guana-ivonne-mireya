package com.example.ExamenIIB

import android.os.Parcel
import android.os.Parcelable

class Consulta (

    var idConsulta: String?,
    var historial_Consulta:String?,
    var esPresencial:String?,
    var motivo: String?,
    var peso: Int?,
    var recomendaciones: String?,
    var historialConsulta:String?

    ): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(historial_Consulta)
        parcel.writeString(idConsulta)
        parcel.writeString(esPresencial)
        parcel.writeString(motivo)
        parcel.writeInt(peso!!)
        parcel.writeString(recomendaciones)
        parcel.writeString(historialConsulta)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${esPresencial}"
    }

    companion object CREATOR : Parcelable.Creator<Consulta> {
        override fun createFromParcel(parcel: Parcel): Consulta {
            return Consulta(parcel)
        }

        override fun newArray(size: Int): Array<Consulta?> {
            return arrayOfNulls(size)
        }
    }


}