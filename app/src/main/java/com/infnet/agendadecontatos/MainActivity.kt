package com.infnet.agendadecontatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.infnet.agendadecontatos.databinding.ActivityMainBinding
import com.infnet.agendadecontatos.room.contato
import com.infnet.agendadecontatos.room.contatoDb
import com.infnet.agendadecontatos.room.contatoRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contatoViewModel: ContatoViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = contatoDb.getInstance(application).contatoDao
        val repository = contatoRepository(dao)
        val factory = ContatoViewModelFactory(repository)
        contatoViewModel = ViewModelProvider(this, factory).get(ContatoViewModel::class.java)
        binding.myViewModel = contatoViewModel
        binding.lifecycleOwner = this

        contatoViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.contatoRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({ selectedItem: contato -> listItemClicked(selectedItem) })
        binding.contatoRecyclerView.adapter = adapter
        displayContatosList()
    }

    private fun displayContatosList() {
        contatoViewModel.getContatos().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: contato) {
        contatoViewModel.initUpdateAndDelete(subscriber)
    }
}