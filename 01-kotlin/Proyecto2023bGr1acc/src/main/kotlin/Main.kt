fun main() {    println("Hola mundo")
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutable = "Vicente";

    // Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //  val > var
    // Duck Typing
    var ejemploVariable = " Adrian Eguez "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;

    // Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()


    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

}
// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    // "Nombre: " + variable + " bienvenido";
    println("Nombre : ${nombre}")
}
fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable
): Double{
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa ) + bonoEspecial
    }
}