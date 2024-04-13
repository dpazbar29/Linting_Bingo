package bingo

/**
 * Clase Cartón
 * @param numeroCarton identifica el cartón
 * @constructor Crea un cartón con un número identificativo
 * */
class Carton(val numeroCarton: Int) {
    private val matriz: Array<Array<String>> = Array(3) { Array(9) { "  " } }
    private var numeros: MutableList<Int> = mutableListOf()
    private var lineasTachadas: Int = 0

    init {
        try {
            crearHuecos()
            posicionNumeros()
        } catch (e: Exception) {
            // Manejo de la excepción (puedes imprimir un mensaje, registrar el error, etc.)
            println("Error al inicializar el cartón: ${e.message}")
        }
    }


    /**
     * Muestra en consola el cartón del Bingo
     * */
    fun imprimirCarton() {
        try {
            println(" ==================CARTÓN ($numeroCarton)==================")
            for (fila in matriz.dropLast(1)) {
                println(" | " + fila.joinToString(" | ") + " |")
                println(" " + "-".repeat(46))
            }
            println(" | " + matriz.last().joinToString(" | ") + " |")
            println()
            println("Lineas completadas: $lineasTachadas")
            println()
            println()
        } catch (e: Exception) {
            println("Error al imprimir el cartón: ${e.message}")
        }
    }


    /**
     * Coloca los números del cartón
     */
    private fun posicionNumeros() {
        for (fila in 0 until 3) {
            for (columna in 0 until 9) {
                if (matriz[fila][columna] == "  ") {
                    var nuevoNumero = 0
                    do {
                        when (columna) {
                            0 -> nuevoNumero = (1..9).random()
                            1 -> nuevoNumero = (10..19).random()
                            2 -> nuevoNumero = (20..29).random()
                            3 -> nuevoNumero = (30..39).random()
                            4 -> nuevoNumero = (40..49).random()
                            5 -> nuevoNumero = (50..59).random()
                            6 -> nuevoNumero = (60..69).random()
                            7 -> nuevoNumero = (70..79).random()
                            8 -> nuevoNumero = (80..90).random()
                        }
                    } while (nuevoNumero in numeros)
                    numeros.add(nuevoNumero)
                    val escribirNumero: String = nuevoNumero.toString()
                    if (escribirNumero.length < 2){
                        matriz[fila][columna] = /*"0" + */nuevoNumero.toString()
                    } else { matriz[fila][columna] = nuevoNumero.toString() }

                }
            }
        }
    }


    /**
     * Comprueba si el cartón tiene las líneas completadas
     * @return True si todas las líneas están completas // False si todas las líneas no están completas
     */
    fun verificarLineas(): Boolean{
        var lineasTachadas = 0
        for (linea in matriz){
            var celdasLibres = 9
            for (celda in linea){
                if ((celda == "--") || (celda == "XX")){
                    celdasLibres -= 1
                }
            }
            if (celdasLibres == 0){
                lineasTachadas += 1
            }
        }
        this.lineasTachadas = lineasTachadas
        return lineasTachadas == 3
    }


    /**
     * Comprueba si todos lo numeros han salido para dar la condicion de victoria de bingo
     * @return True si hay bingo // False si no hay bingo
     * */
    fun verificarBingo(): Boolean {
        var celdasMarcadas = 0

        for (fila in matriz) {
            for (celda in fila) {
                if (celda == "XX") {
                    celdasMarcadas++
                }
            }
        }

        return celdasMarcadas == 15

    }


    /**
     * Marca los huecos donde no se pondran numeros en cada fila
     * */
    private fun crearHuecos() {
        val huecoBlanco = "--"
        for (fila in matriz) {
            var contador = 0
            var numero: Int
            var posicionOcupada: Boolean
            do {

                do {
                    numero = (0..8).random()
                    posicionOcupada = posicionHuecos(fila, numero)
                } while (posicionOcupada)

                fila[numero] = huecoBlanco
                contador += 1
            } while (contador < 4)

        }
    }


    /**
     * Verifica que huecos están marcados como huecos en blanco
     * @return True si la celda ya está marcada como hueco en blanco // False si la celda todavía no está marcada
     * */
    private fun posicionHuecos(fila: Array<String>,columna:Int): Boolean{
        val huecoBlanco= "--"
        val celda = fila[columna]

        return celda == huecoBlanco
    }


    /**
     * Marca el número en el carton si ha salido en el bombo con "XX".
     * */
    fun marcarNumero(numero: Int) {
        try {
            for (fila in 0 until 3) {
                for (columna in 0 until 9) {
                    if (matriz[fila][columna] == numero.toString()) {
                        matriz[fila][columna] = "XX"
                    }
                }
            }
        } catch (e: Exception) {
            println("Error al marcar el número en el cartón: ${e.message}")
        }
    }
}


