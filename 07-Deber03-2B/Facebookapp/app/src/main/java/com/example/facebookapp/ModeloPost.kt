package com.example.facebookapp

class ModeloPost(
    public var id: Int?,
    public var likes: Int?,
    public var corazon: Int?,
    public var divertido: Int?,
    public var enamorado: Int?,
    public var molesto: Int?,
    public var triste: Int?,
    public var comentariosCompartidos: String?,
    public var propic: Int?,
    public var postpic: Int?,
    public var nombre: String?,
    public var tiempo: String?,
    public var estado: String?,
) {
    override fun toString(): String {
        return "${likes} - ${corazon}- ${divertido}- ${enamorado}- ${molesto} - ${triste}- " +
                "${comentariosCompartidos} - ${propic} - ${postpic} - ${nombre} - ${tiempo} " +
                "- ${estado}"
    }


}