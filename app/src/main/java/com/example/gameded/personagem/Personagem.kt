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

    fun racaToString(): String {
        return when (raca) {
            is Elfo -> "Elfo"
            is Anao -> "Anão"
            is altoElfo -> "Alto elfo"
            is anaoMontanha -> "Anão da montanha"
            is anaoColina -> "Anão da colina"
            is Draconato -> "Draconato"
            is Drow -> "Drow"
            is elfoFloresta -> "Elfo da floresta"
            is Gnomo -> "Gnomo"
            is gnomoFloresta -> "Gnomo da floresta"
            is gnomoRochas -> "Gnomo das rochas"
            is Halfling -> "Halfling"
            is halflingPesLeves -> "Halfling pés-leves"
            is halflingRobusto -> "Halfling robusto"
            is Humano -> "Humano"
            is meioElfo -> "Meio elfo"
            is meioOrc -> "Meio orc"
            is Tiefling -> "Tiefling"
            else -> "Desconhecido"
        }
    }

    fun aplicarBonus() {
        raca?.aplicarBonus(this)
    }

    fun toEntity(id: Int = 0): PersonagemEntity {
        return PersonagemEntity(
            id = id,
            nome = this.nome,
            nomeRaca = this.racaToString(),
            forca = this.forca,
            destreza = this.destreza,
            constituicao = this.constituicao,
            inteligencia = this.inteligencia,
            sabedoria = this.sabedoria,
            carisma = this.carisma,
            pontosDeVida = this.pontosDeVida
        )
    }
}

fun getRacaFromString(raca: String): Raca? {
    return when (raca.lowercase().trim()) {
        "elfo" -> Elfo()
        "anão" -> Anao()
        "alto elfo" -> altoElfo()
        "anão da montanha" -> anaoMontanha()
        "anão da colina" -> anaoColina()
        "draconato" -> Draconato()
        "drow" -> Drow()
        "elfo da floresta" -> elfoFloresta()
        "gnomo" -> Gnomo()
        "gnomo da floresta" -> gnomoFloresta()
        "gnomo das rochas" -> gnomoRochas()
        "halfling" -> Halfling()
        "halfling pés-leves" -> halflingPesLeves()
        "halfling robusto" -> halflingRobusto()
        "humano" -> Humano()
        "meio elfo" -> meioElfo()
        "meio orc" -> meioOrc()
        "tiefling" -> Tiefling()
        else -> null
    }
}

fun Raca.getNomeFormatado(): String {
    return when (this) {
        is Elfo -> "Elfo"
        is Anao -> "Anão"
        is altoElfo -> "Alto Elfo"
        is anaoColina -> "Anão da Colina"
        is anaoMontanha -> "Anão da Montanha"
        is Draconato -> "Draconato"
        is Drow -> "Drow"
        is elfoFloresta -> "Elfo da Floresta"
        is Gnomo -> "Gnomo"
        is gnomoFloresta -> "Gnomo da Floresta"
        is gnomoRochas -> "Gnomo das Rochas"
        is Halfling -> "Halfling"
        is halflingPesLeves -> "Halfling Pés-Leves"
        is halflingRobusto -> "Halfling Robusto"
        is Humano -> "Humano"
        is meioElfo -> "Meio Elfo"
        is meioOrc -> "Meio Orc"
        is Tiefling -> "Tiefling"
        else -> "Raça desconhecida"
    }
}
