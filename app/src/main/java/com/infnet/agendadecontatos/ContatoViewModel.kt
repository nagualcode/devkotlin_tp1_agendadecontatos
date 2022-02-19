package com.infnet.agendadecontatos

import android.util.Patterns
import androidx.lifecycle.*
import com.infnet.agendadecontatos.room.contato
import com.infnet.agendadecontatos.room.contatoRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ContatoViewModel(private val repository: contatoRepository) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var contatoToUpdateOrDelete: contato
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Salvar"
        clearAllOrDeleteButtonText.value = "Deletar Todos"
    }

    fun initUpdateAndDelete(contato: contato) {
        inputName.value = contato.name
        inputEmail.value = contato.email
        isUpdateOrDelete = true
        contatoToUpdateOrDelete = contato
        saveOrUpdateButtonText.value = "Atualizar"
        clearAllOrDeleteButtonText.value = "Deletar"
    }

    fun saveOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Insira o nome do contato")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Insira o email do contato")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Insira um email válido")
        } else {
            if (isUpdateOrDelete) {
                contatoToUpdateOrDelete.name = inputName.value!!
                contatoToUpdateOrDelete.email = inputEmail.value!!
                updateContato(contatoToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                novoContato(contato(0, name, email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }

    private fun novoContato(contato: contato) = viewModelScope.launch {
        val newRowId = repository.insert(contato)
        if (newRowId > -1) {
            statusMessage.value = Event("Cadastrado no id: $newRowId")
        } else {
            statusMessage.value = Event("Erro!")
        }
    }


    private fun updateContato(contato: contato) = viewModelScope.launch {
        val noOfRows = repository.update(contato)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Salvar"
            clearAllOrDeleteButtonText.value = "Deletar Todos"
            statusMessage.value = Event("$noOfRows Atualizado")
        } else {
            statusMessage.value = Event("Erro!")
        }
    }

    fun getContatos() = liveData {
        repository.contatos.collect {
            emit(it)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteContato(contatoToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun deleteContato(contato: contato) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(contato)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Salvar"
            clearAllOrDeleteButtonText.value = "Deletar Todos"
            statusMessage.value = Event("$noOfRowsDeleted Deletado!")
        } else {
            statusMessage.value = Event("Erro!")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted deletados!")
        } else {
            statusMessage.value = Event("Erro!")
        }
    }
}