package com.example.gameded.personagem
import java.io.Serializable

class Anao : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.constituicao += 2
    }
}