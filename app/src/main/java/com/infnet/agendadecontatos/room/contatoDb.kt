package com.infnet.agendadecontatos.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [contato::class], version = 1)
abstract class contatoDb : RoomDatabase() {
    abstract val contatoDao: contatoDao

    companion object {
        @Volatile
        private var INSTANCE: contatoDb? = null
        fun getInstance(context: Context): contatoDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        contatoDb::class.java,
                        "contatos_database"
                    ).build()
                }
                return instance
            }
        }
    }
}

