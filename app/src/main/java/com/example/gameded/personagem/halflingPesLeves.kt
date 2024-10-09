package com.example.gameded.personagem
import java.io.Serializable

class halflingPesLeves : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.carisma += 1
    }
}