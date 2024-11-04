package com.example.gameded.personagem

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "personagem")
data class PersonagemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var nome: String = "",
    var nomeRaca: String = "",
    var forca: Int = 8,
    var destreza: Int = 8,
    var constituicao: Int = 8,
    var inteligencia: Int = 8,
    var sabedoria: Int = 8,
    var carisma: Int = 8,
    var pontosDeVida: Int = 10
) : Serializable {
    fun toPersonagem(): Personagem {
        val personagem = Personagem()
        personagem.nome = this.nome
        personagem.raca = getRacaFromString(this.nomeRaca)
        personagem.forca = this.forca
        personagem.destreza = this.destreza
        personagem.constituicao = this.constituicao
        personagem.inteligencia = this.inteligencia
        personagem.sabedoria = this.sabedoria
        personagem.carisma = this.carisma
        personagem.pontosDeVida = this.pontosDeVida
        return personagem
    }
}


