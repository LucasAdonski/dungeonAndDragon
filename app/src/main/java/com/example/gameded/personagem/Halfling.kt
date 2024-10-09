package com.example.gameded.personagem
import java.io.Serializable

class Halfling : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.destreza += 2
    }
}