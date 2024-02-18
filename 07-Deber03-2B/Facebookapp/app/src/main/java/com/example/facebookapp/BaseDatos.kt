package com.example.facebookapp

class BaseDatos {
    companion object{
        val arregloPosts = arrayListOf<ModeloPost>()
        val arregloNotificaciones= arrayListOf<Notificacion>()

        init {
            arregloPosts
                .add(
                    ModeloPost(1,5, 10,2,1,1,5,"3 comentarios 10 veces compartido" ,R.drawable.perfil3,R.drawable.frase,"Alejandro Proaño","2 hrs","Para quien lo necesite <3")
                )
            arregloPosts
                .add(
                    ModeloPost(2,26,6,50,1,1,1,"26 comentarios 15 veces compartido" ,R.drawable.perfil2,R.drawable.viaje,"Isset Rodriguez", "8 hrs"," Y si asi mero jaja !!!")
                )
            arregloPosts
                .add(
                    ModeloPost(3,17,90,1,1,1,2,"53 comentarios 1M veces compartido" ,R.drawable.perfil4,R.drawable.bts," Adrian Zambrano", "2 mins","Orgulloso de mis tannies")
                )
            arregloPosts
                .add(
                    ModeloPost(4,1,10,25,2,1,3,"26 comentarios 15 veces compartido" ,R.drawable.perfil1,R.drawable.kdrama,"Amara Luan", "1 min","Esto se esta poniendo potente!!!")
                )
            arregloNotificaciones
                .add(
                    Notificacion(1,R.drawable.perfil4,"Anthony Villacis ha dado like a tu última publicación", "3 hrs")
                )
            arregloNotificaciones
                .add(
                    Notificacion(2,R.drawable.perfil2,"Carolina Ramirez ha compartido un post de Adicción Asíatica","16hrs")
                )
            arregloNotificaciones
                .add(
                    Notificacion(3,R.drawable.perfil3,"Mireya Ramirez ha modificado su foto de perfil", "24 hrs")
                )
            arregloNotificaciones
                .add(
                    Notificacion(4,R.drawable.perfil1,"Addicción Asiatica a subido un nuevo video", "2 diás")
                )

        }
    }
}