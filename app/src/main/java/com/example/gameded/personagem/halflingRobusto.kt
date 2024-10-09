package com.example.gameded.personagem
import java.io.Serializable

class halflingRobusto : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.constituicao += 1
    }
}