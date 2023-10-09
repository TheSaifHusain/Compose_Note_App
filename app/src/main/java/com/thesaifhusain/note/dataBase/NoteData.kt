package com.thesaifhusain.note.dataBase

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "NoteTable")
data class NoteData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    var dateAndTime: String,
    var textSize: Int,
    var isTextAlignCenter: Boolean = false,
    var isTextAlignRight: Boolean = false,
    var isTextBold: Boolean = false,
    var isTextUnderline: Boolean = false
)
