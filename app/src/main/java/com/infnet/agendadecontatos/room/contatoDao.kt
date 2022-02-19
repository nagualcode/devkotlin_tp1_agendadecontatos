package com.infnet.agendadecontatos.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface contatoDao {

    @Insert
    suspend fun insertContato(contato: contato) : Long

    @Update
    suspend fun updateContato(contato: contato) : Int

    @Delete
    suspend fun deleteContato(contato: contato) : Int

    @Query("DELETE FROM contatos_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM contatos_table")
    fun getAllContatos():Flow<List<contato>>
}