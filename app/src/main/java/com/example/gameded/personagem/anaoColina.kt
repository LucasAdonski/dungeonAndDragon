package com.example.gameded.personagem
import java.io.Serializable

class anaoColina : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.sabedoria += 1
    }
}