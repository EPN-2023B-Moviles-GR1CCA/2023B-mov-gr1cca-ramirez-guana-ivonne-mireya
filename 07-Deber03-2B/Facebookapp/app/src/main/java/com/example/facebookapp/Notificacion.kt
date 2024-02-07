package com.example.facebookapp

class Notificacion(
    public var id: Int?,
    public var perfil: Int?,
    public var texto: String?,
    public var horaNot: String?,
) {
    override fun toString(): String {
        return "${perfil} - ${texto} - ${horaNot}"
    }
}