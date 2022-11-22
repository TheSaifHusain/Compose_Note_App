package com.thesaifhusain.note.DataBase


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteData::class], version = 1, exportSchema = false)
abstract class NoteDataBase :RoomDatabase(){
    abstract fun getNoteDao(): NoteDao

}