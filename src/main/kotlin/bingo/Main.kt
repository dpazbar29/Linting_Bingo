package bingo
import java.util.*
import java.io.File


/**
 * Iniciar la simulación del bingo
 */
fun jugar() {
    try {
        Reglas.mostrarReglas()

        val numCartones = Juego.mostrarNumeroCartones()
        val cartonesEnJuego: MutableList<Carton> = mutableListOf()

        repeat(numCartones) { index ->
            val miCarton = Carton(index + 1)
            cartonesEnJuego += miCarton
            miCarton.imprimirCarton()
        }

        var hayBingo = false

        while (!hayBingo) {
            try {
                Juego.pasarRonda()
                Juego.limpiarConsola()

                val bombo = Bombo

                val numeroSacado = bombo.sacarNumero()

                Bombo.mostrarHistorialNumeros()
                println("NÚMERO SACADO: $numeroSacado")
                for (carton in cartonesEnJuego) {
                    carton.marcarNumero(numeroSacado)

                    carton.verificarLineas()
                    carton.imprimirCarton()
                }

                for (carton in cartonesEnJuego) {
                    if (carton.verificarLineas()) {
                        if (carton.verificarBingo()) {
                            hayBingo = true
                            println("BINGO EN CARTÓN ${carton.numeroCarton}")
                            println()
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")

            }
        }

        if (Juego.repetirSimulacion()) {
            Bombo.reiniciarBombo()
            jugar()
        }
    } catch (e: InputMismatchException) {
        println("Error: Ingresa un número válido.")

        jugar()
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}


/**
 * Muestra el menu
 * @return true si el usuario inicia sesión correctamente // false si el usuario elige salir de la simulación
 */
fun mostrarMenuLogin(baseDatos: File): Boolean {
    while (true) {
        println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
        println("1. Registrarse")
        println("2. Iniciar sesión")
        println("3. Eliminar cuenta")
        println("4. Salir")
        println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
        print("Seleccione una opción: ")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> {
                print("Nombre de usuario: ")
                val nombreUsuario = readlnOrNull().orEmpty()
                print("Contraseña (mínimo 8 caracteres, 1 mayúscula y 1 número): ")
                val contrasena = readlnOrNull().orEmpty()
                registro(nombreUsuario, contrasena, baseDatos)
            }
            2 ->  {
                print("Nombre de usuario: ")
                val nombreUsuario = readlnOrNull().orEmpty()
                print("Contraseña: ")
                val contrasena = readlnOrNull().orEmpty()
                val sesionIniciada = iniciarSesion(nombreUsuario, contrasena, baseDatos)
                if (sesionIniciada) {
                    return true // Devolver true si el inicio de sesión es exitoso
                }
            }
            3 -> {
                print("Nombre de usuario: ")
                val nombreUsuarioEliminar = readlnOrNull().orEmpty()
                print("Confirma la contraseña para eliminar la cuenta: ")
                val contrasenaEliminar = readlnOrNull().orEmpty()
                eliminarCuenta(nombreUsuarioEliminar, contrasenaEliminar, baseDatos)
            }
            4 -> return false // Devolver false si el usuario elige salir
            else -> println("Opción inválida")
        }
    }
}

fun main() {
    val archivoBaseDatos = crearArchivoBaseDatos("base_datos.txt")

    val sesionIniciada = mostrarMenuLogin(archivoBaseDatos)
    if (sesionIniciada) {
        jugar()
    } else {
        println("¡Adiós!")
    }
}