package com.example.gameded.personagem
import java.io.Serializable

class Tiefling : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.inteligencia += 1
        personagem.carisma += 2
    }
}