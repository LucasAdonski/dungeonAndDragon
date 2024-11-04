package com.example.gameded.personagem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PersonagemViewModel(application: Application) : AndroidViewModel(application) {
    private val personagemDAO: PersonagemDAO
    val allPersonagens: LiveData<List<PersonagemEntity>>

    init {
        val db = PersonagemDatabase.getDatabase(application)
        personagemDAO = db.personagemDAO()
        allPersonagens = personagemDAO.getAllPersonagens()
    }

    fun insert(personagem: PersonagemEntity, callback: (Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = personagemDAO.insert(personagem)
            callback(id)
        }
    }

    fun update(personagem: PersonagemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            personagemDAO.update(personagem)
        }
    }

    fun delete(personagem: PersonagemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            personagemDAO.delete(personagem)
        }
    }

    fun getPersonagemById(id: Int): LiveData<PersonagemEntity> {
        return personagemDAO.getPersonagemById(id)
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            personagemDAO.deleteAll()
        }
    }
}
