package com.example.gameded.personagem
import java.io.Serializable

class meioOrc : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.forca += 2
        personagem.constituicao += 1
    }
}