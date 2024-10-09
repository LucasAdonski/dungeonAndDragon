package com.example.gameded.personagem
import java.io.Serializable

class altoElfo : Raca, Serializable {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.inteligencia += 1
    }
}