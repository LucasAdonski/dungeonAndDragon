package com.example.gameded.personagem

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonagemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personagem: PersonagemEntity): Long

    @Update
    suspend fun update(personagem: PersonagemEntity)

    @Delete
    suspend fun delete(personagem: PersonagemEntity)

    @Query("SELECT * FROM personagem WHERE id = :id")
    fun getPersonagemById(id: Int): LiveData<PersonagemEntity>

    @Query("SELECT * FROM personagem")
    fun getAllPersonagens(): LiveData<List<PersonagemEntity>>

    @Query("DELETE FROM personagem")
    fun deleteAll()
}