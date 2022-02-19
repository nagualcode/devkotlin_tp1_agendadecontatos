package com.infnet.agendadecontatos.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contatos_table")
data class contato(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contato_id")
    var id: Int,

    @ColumnInfo(name = "contato_name")
    var name: String,

    @ColumnInfo(name = "contato_email")
    var email: String

)