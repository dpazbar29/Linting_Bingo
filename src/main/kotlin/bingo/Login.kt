@file:Suppress("UNREACHABLE_CODE")

package bingo
import java.io.File
import java.io.IOException


/**
 * Clase Usuario
 * @param nombreUsuario Nombre del usuario
 * @param password Contraseña del usuario
 * @constructor Crea un usuario con un nombre y una contraseña
 */
class Usuario(private val nombreUsuario: String, val password: String)


/**
 * Crear un archivo para almacenar los usuarios
 * @param nombreArchivo Nombre del archivo
 * @return archivo
 */
fun crearArchivoBaseDatos(nombreArchivo: String): File {
    val archivo = File(nombreArchivo)

    try {
        if (!archivo.exists()) {
            if (archivo.createNewFile()) {
                println("Archivo de base de datos creado: $nombreArchivo")
            }
        }
    } catch (e: IOException) {
        println("Error al crear o acceder al archivo de base de datos: ${e.message}")
    }

    return archivo
}


/**
 * Buscar un usuario en la base de datos
 * @param nombreUsuario Nombre del usuario
 * @param baseDatos Fichero que almacena los usuarios
 * @return usuario encontrado // null si no existe el usuario
 */
fun obtenerUsuario(nombreUsuario: String, baseDatos: File): Usuario? {
    try {
        baseDatos.useLines { lines ->
            for (linea in lines) {
                val (usuario, password) = linea.split(",")
                if (usuario == nombreUsuario) {
                    return Usuario(usuario, password)
                }
            }
        }
    } catch (e: IOException) {
        println("Error al obtener usuario: ${e.message}")
    }
    return null
}


/**
 * Crea un usuario
 * @param nombreUsuario nombre del nuevo usuario
 * @param password contraseña del nuevo usuario
 * @param baseDatos Fichero que almacena los usuarios
 * @return true si se ha registrado el usuario // false si no se ha podido registrar
 */
fun registro(nombreUsuario: String, password: String, baseDatos: File): Boolean {
    try {
        if (nombreUsuario.isBlank() || password.isBlank()) {
            println("Error: El nombre de usuario o la contraseña no pueden estar vacíos.")
            return false
        }

        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[A-Z]).{8,}\$")
        if (!password.matches(passwordRegex)) {
            println("Error: La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.")
            return false
        }

        var confirmPassword: String
        do {
            print("Confirma tu contraseña: ")
            confirmPassword = readlnOrNull().orEmpty()

            if (password != confirmPassword) {
                println("Error: Las contraseñas no coinciden.")
            }
        } while (password != confirmPassword)

        if (obtenerUsuario(nombreUsuario, baseDatos) != null) {
            println("Error: El nombre de usuario ya está registrado.")
            return false
        }

        baseDatos.appendText("$nombreUsuario,$password\n")
        println("Usuario registrado de forma satisfactoria")
        return true
    } catch (e: IOException) {
        println("Error al registrar el usuario: ${e.message}")
        return false
    }
}


/**
 * Permite iniciar sesión con un usuario que esté almacenado en la base de datos
 * @param nombreUsuario nombre del usuario
 * @param password contraseña del usuario
 * @param baseDatos fichero que almacena los usuarios
 * @return true si el usuario ha podido iniciar sesión // false si no ha podido iniciar sesión
 */
fun iniciarSesion(nombreUsuario: String, password: String, baseDatos: File): Boolean {
    try {
        if (nombreUsuario.isBlank() || password.isBlank()) {
            println("Error: El nombre de usuario o la contraseña no pueden estar vacíos.")
            return false
        }

        val usuario: Usuario?
        do {
            usuario = obtenerUsuario(nombreUsuario, baseDatos)
            if (usuario != null && usuario.password == password) {
                println("Inicio de sesión exitoso!")
                return true
            } else {
                println("Nombre de usuario o contraseña incorrectos.")
                print("Por favor, intentalo de nuevo.\nNombre de usuario: ")
                val nuevoNombreUsuario = readlnOrNull().orEmpty()
                print("Contraseña: ")
                val nuevaPassword = readlnOrNull().orEmpty()
                return iniciarSesion(nuevoNombreUsuario, nuevaPassword, baseDatos)
            }
        } while (usuario == null || usuario.password != password)

    } catch (e: IOException) {
        println("Error al iniciar sesión ${e.message}")
        return false
    }
}


/**
 * Elimina la cuenta de un usuario
 * @param nombreUsuario nombre del usuario
 * @param password contraseña del usuario
 * @param baseDatos fichero que almacena los usuarios
 * @return true si la cuenta se ha eliminado // false si la cuenta no se ha podido eliminar
 */
fun eliminarCuenta(nombreUsuario: String, password: String, baseDatos: File): Boolean {
    try {
        if (nombreUsuario.isBlank() || password.isBlank()) {
            println("Error: El nombre de usuario o la contraseña no pueden estar vacíos.")
            return false
        }

        val usuario: Usuario?
        do {
            usuario = obtenerUsuario(nombreUsuario, baseDatos)
            if (usuario != null && usuario.password == password) {
                val lineas = baseDatos.readLines()
                baseDatos.writeText("")
                for (linea in lineas) {
                    val (usuarioExistente, _) = linea.split(",")
                    if (usuarioExistente != nombreUsuario) {
                        baseDatos.appendText("$linea\n")
                    }
                }
                println("La cuenta ha sido eliminada exitosamente.")
                return true
            } else {
                println("Nombre de usuario o contraseña incorrectos.")
                print("Por favor, intentalo de nuevo.\nNombre de usuario: ")
                val nuevoNombreUsuario = readlnOrNull().orEmpty()
                print("Contraseña: ")
                val nuevaPassword = readlnOrNull().orEmpty()
                return eliminarCuenta(nuevoNombreUsuario, nuevaPassword, baseDatos)
            }
        } while (usuario == null || usuario.password != password)

    } catch (e: IOException) {
        println("Error al eliminar la cuenta: ${e.message}")
        return false
    }
}