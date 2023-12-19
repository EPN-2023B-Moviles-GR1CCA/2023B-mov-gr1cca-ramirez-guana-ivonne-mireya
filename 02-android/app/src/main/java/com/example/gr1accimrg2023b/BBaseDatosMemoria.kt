package com.example.gr1accimrg2023b
class BBaseDatosMemoria {
    // COMPANION OBJECT
    companion object{
        val arregloBEntrenador= arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "mireya","a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "ivonne","b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "raul","c@c.com")
                )

        }
    }
}