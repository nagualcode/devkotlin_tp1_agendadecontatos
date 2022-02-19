package com.infnet.agendadecontatos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infnet.agendadecontatos.room.contatoRepository
import java.lang.IllegalArgumentException

class ContatoViewModelFactory(
        private val repository: contatoRepository
        ):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     if(modelClass.isAssignableFrom(ContatoViewModel::class.java)){
         return ContatoViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown View Model class")
    }

}