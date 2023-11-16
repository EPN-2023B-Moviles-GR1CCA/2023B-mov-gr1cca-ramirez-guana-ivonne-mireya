fun main() {
    println("Hola mundo!")

   //inmutables (no se reasignan "=")
    val inmutable : String = "Ivonne;" ;
    //inmutable = "Mireya"
    // Mutables (Re asignar)
    var mutable: String = "Mireya";
    mutable = "Ivonne";

    //val>var
    //duck typing
    var ejemploVariable=" Ivonne Ramirez"
    val edadEjemplo:Int=12
    ejemploVariable.trim()
    // ejemploVariable= edadEjemplo;

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

}