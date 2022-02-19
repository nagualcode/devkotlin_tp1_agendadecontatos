package com.infnet.agendadecontatos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.infnet.agendadecontatos.databinding.ListItemBinding
import com.infnet.agendadecontatos.room.contato

class MyRecyclerViewAdapter(private val clickListener: (contato) -> Unit) :
        RecyclerView.Adapter<MyViewHolder>() {
    private val contatosList = ArrayList<contato>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contatosList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contatosList[position], clickListener)
    }

    fun setList(contatos: List<contato>) {
        contatosList.clear()
        contatosList.addAll(contatos)

    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contato: contato, clickListener: (contato) -> Unit) {
        binding.nameTextView.text = contato.name
        binding.emailTextView.text = contato.email
        binding.listItemLayout.setOnClickListener {
            clickListener(contato)
        }
    }
}