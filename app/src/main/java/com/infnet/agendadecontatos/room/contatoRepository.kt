package com.infnet.agendadecontatos.room

class contatoRepository(private val dao: contatoDao) {

    val contatos = dao.getAllContatos()

    suspend fun insert(contato: contato): Long {
        return dao.insertContato(contato)
    }

    suspend fun update(contato: contato): Int {
        return dao.updateContato(contato)
    }

    suspend fun delete(contato: contato): Int {
        return dao.deleteContato(contato)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}