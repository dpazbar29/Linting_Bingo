package bingo

import java.util.*

/**
 * Clase Juego
 * Representa los pasos que se siguen durante una partida del bingo
 */
object Juego {
    /**Pantalla intermedia entre número y número del bombo que pide una validación por teclado*/
    fun pasarRonda(){
        println("Pulse ENTER para continuar al siguiente número...")
        Scanner(System.`in`).nextLine()
    }


  /** Limpia la consola en sistemas operativos Linux y Windows
   * Está muy cutre porque no hemos conseguido que funcione lo dejamos para el sprint 2*/
  fun limpiarConsola() {
      /**if (System.getProperty("os.name").contains("Windows")) {
          ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
      } else {
          ProcessBuilder("clear").inheritIO().start().waitFor()
      }**/

      repeat(35) { println() }
  }


    /**
     * Pregunta al usuario el número de cartones que quiere simular con un minimo de 2 y maximo de 5
     * @return número de cartones elegidos por el jugador
     * */
    fun mostrarNumeroCartones(): Int {
        val scanner = Scanner(System.`in`)
        var numCartones: Int

        try {
            do {
                println("¿Cuántos cartones quieres generar? (Entre 2 y 5)")
                numCartones = scanner.nextInt()
            } while (numCartones !in 2..5)
        } catch (e: InputMismatchException) {
            println("Error: Ingresa un número válido.")
            // Puedes agregar lógica adicional si es necesario
            return mostrarNumeroCartones()
        }

        return numCartones
    }


    /**
     * Te pregunta si quieres volver a iniciar el programa
     * */
    fun repetirSimulacion(): Boolean {
        var jugarDeNuevo: String?

        do {
            try {
                print("¿Quieres jugar de nuevo? (s/n): ")
                jugarDeNuevo = readlnOrNull()?.lowercase()

                if ((jugarDeNuevo == "s") or (jugarDeNuevo == "n")) {
                    return jugarDeNuevo == "s"
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                // Puedes agregar lógica adicional si es necesario
            }
        } while (true)
    }

}