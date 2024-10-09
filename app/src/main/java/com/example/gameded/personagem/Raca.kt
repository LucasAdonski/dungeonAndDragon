package com.example.gameded.personagem
import java.io.Serializable

interface Raca :  Serializable {
    fun aplicarBonus(personagem: Personagem)
}