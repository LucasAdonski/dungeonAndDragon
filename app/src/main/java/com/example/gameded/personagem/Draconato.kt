package com.example.gameded.personagem
import java.io.Serializable

class Draconato : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.forca += 2
        personagem.carisma += 1
    }
}