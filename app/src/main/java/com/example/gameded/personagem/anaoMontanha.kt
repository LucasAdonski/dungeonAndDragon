package com.example.gameded.personagem
import java.io.Serializable

class anaoMontanha : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.forca += 2
    }
}