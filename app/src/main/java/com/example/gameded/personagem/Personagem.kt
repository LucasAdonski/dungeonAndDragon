package com.example.gameded.personagem
import java.io.Serializable

open class Personagem : Serializable {
    var nome: String = ""
    var raca: Raca? = null
    var forca: Int = 8
    var destreza: Int = 8
    var constituicao: Int = 8
    var inteligencia: Int = 8
    var sabedoria: Int = 8
    var carisma: Int = 8
    var pontosDeVida: Int = 10

    fun calcularModificador(valor: Int): Int {
        return when (valor) {
            in 30..Int.MAX_VALUE -> +10
            in 28..29 -> +9
            in 26..27 -> +8
            in 24..25 -> +7
            in 22..23 -> +6
            in 20..21 -> +5
            in 18..19 -> +4
            in 16..17 -> +3
            in 14..15 -> +2
            in 12..13 -> +1
            in 10..11 -> 0
            in 8..9 -> -1
            else -> -2
        }
    }

    fun aplicarBonus() {
        raca?.aplicarBonus(this)
    }

    open fun mostrarStatus(personagem: Personagem) {
        println("Nome:  ${personagem.nome}")
        println("Força: ${personagem.forca} (Modificador: ${calcularModificador(personagem.forca)})")
        println("Destreza: ${personagem.destreza} (Modificador: ${calcularModificador(personagem.destreza)})")
        println("Constituição: ${personagem.constituicao} (Modificador: ${calcularModificador(personagem.constituicao)})")
        println("Inteligência: ${personagem.inteligencia} (Modificador: ${calcularModificador(personagem.inteligencia)})")
        println("Sabedoria: ${personagem.sabedoria} (Modificador: ${calcularModificador(personagem.sabedoria)})")
        println("Carisma: ${personagem.carisma} (Modificador: ${calcularModificador(personagem.carisma)})")
        val vidaTotal = pontosDeVida + calcularModificador(personagem.constituicao)
        println("Pontos de Vida: $vidaTotal")
    }
}