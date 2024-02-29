package com.example.ExamenIIB

import android.os.Parcel
import android.os.Parcelable

class Mascota(

    var idMascota: String?,
    var nombreMascota: String?,
    var especieS: String?,
    var anioNacimiento: String?,
    var frecuencia: String?,
    var NivelAdiestramiento: Int?
    ): Parcelable {

    override fun toString(): String {
        return "${nombreMascota}"
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idMascota)
        parcel.writeString(nombreMascota)
        parcel.writeString(especieS)
        parcel.writeString(anioNacimiento!!)
        parcel.writeString(frecuencia)
        parcel.writeInt(NivelAdiestramiento!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mascota> {
        override fun createFromParcel(parcel: Parcel): Mascota {
            return Mascota(parcel)
        }

        override fun newArray(size: Int): Array<Mascota?> {
            return arrayOfNulls(size)
        }
    }

}