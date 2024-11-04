package com.example.gameded.personagem

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonagemEntity::class], version = 1, exportSchema = false)
abstract class PersonagemDatabase : RoomDatabase() {
    abstract fun personagemDAO(): PersonagemDAO

    companion object {
        @Volatile
        private var INSTANCE: PersonagemDatabase? = null

        fun getDatabase(context: Context): PersonagemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonagemDatabase::class.java,
                    "personagem_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}