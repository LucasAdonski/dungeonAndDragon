package com.example.gameded.personagem
import java.io.Serializable

class elfoFloresta : Raca, Serializable {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.sabedoria += 1
    }
}