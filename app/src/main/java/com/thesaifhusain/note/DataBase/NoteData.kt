package com.thesaifhusain.note.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "NoteTable")
data class NoteData(

    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var title:String,
    var description:String
)
