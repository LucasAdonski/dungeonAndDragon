package com.example.gameded.personagem
import java.io.Serializable

class meioElfo : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.carisma += 2
    }
}