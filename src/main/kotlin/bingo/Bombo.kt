package bingo

/**
 * Clase Bombo
 * @constructor crear un bombo que generará números entre el 1 y el 90
 */
object Bombo {
    private val numerosSalidos: MutableList<Int> = mutableListOf()
    private val historialUltimosNumeros: MutableList<Int> = mutableListOf()

    /**
     * Saca un numero aleatorio del 1 al 90
     * @return número generado
     * */
    fun sacarNumero(): Int {
        var numero: Int
        do {
            numero = (1..90).random()
        } while(numerosSalidos.contains(numero))

        numerosSalidos.add(numero)
        actualizarHistoriaNumeros(numero)
        return numero
    }

    /**
     * Actuliza la lista con los 10 últimos números que han salido del bombo
     * @param numeroNuevo número que acaba de salir del bombo
     * */
    private fun actualizarHistoriaNumeros(numeroNuevo: Int) {

        if (historialUltimosNumeros.size == 10) {
            this.historialUltimosNumeros.removeAt(0)
            this.historialUltimosNumeros.add(numeroNuevo)
        } else {
            this.historialUltimosNumeros.add(numeroNuevo)
        }

    }


    /**
     * Mostrar por pantalla el historial de los 10 últimos números que han salido del bombo
     */
    fun mostrarHistorialNumeros() {
        var cadena = "\nÚltimos 10 números ->"

        for (numero in this.historialUltimosNumeros) {
            cadena += " $numero"
        }

        println(cadena)
    }


    /**
     * Renicia el bombo. Vacía la listas de los números salidos
     * */
    fun reiniciarBombo() {
        this.numerosSalidos.clear()
        this.historialUltimosNumeros.clear()
    }
}
