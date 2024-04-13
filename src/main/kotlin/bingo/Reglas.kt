package bingo

/**
 * Clase Reglas
 * Representa las reglas del juego
 */
object Reglas {

    /**
     * Imprime en la consola las reglas del bingo.
     */
    fun mostrarReglas(){
        println("Reglas del juego del bingo")
        println("1- La simulacion del juego del bingo se realiza con un minimo de 2 personas y un maximo de 5")
        println("2- Cada persona esta limitada con 1 solo carton")
        println("3- Se sacaran numeros de forma aleatoria del 1 al 90")
        println("4- El carton sera de unas dimensiones de 3x9")
        println("5- Cada linea del carton sera cantada por consola")
        println("6- Si se canta bingo en un carton el juego finalizara")
        println("7- Para pasar de ronda y sacar otro numero del bombo el usuario debera de introducir un input 'Enter' en la consola")
        println("8- Si el usuario desea puede volver a comenzar otra simulacion")
        println()
    }
}