package com.example.gameded.personagem
import java.io.Serializable

class Gnomo : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.inteligencia += 2
    }
}


