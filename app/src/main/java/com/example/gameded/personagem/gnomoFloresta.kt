package com.example.gameded.personagem
import java.io.Serializable

class gnomoFloresta : Raca, Serializable {

    override fun aplicarBonus(personagem: Personagem) {
        personagem.destreza += 1
    }
}