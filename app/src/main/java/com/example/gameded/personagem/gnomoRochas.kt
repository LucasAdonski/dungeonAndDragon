package com.example.gameded.personagem
import java.io.Serializable

class gnomoRochas : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.constituicao += 1
    }
}