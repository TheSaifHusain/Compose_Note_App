package com.thesaifhusain.note.Repository

import com.thesaifhusain.note.DataBase.NoteDao
import com.thesaifhusain.note.DataBase.NoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val noteDao: NoteDao) {

    suspend fun insertR(note: NoteData)= withContext(Dispatchers.IO){
        noteDao.insert(note)
    }

    suspend fun updateR(note: NoteData)= withContext(Dispatchers.IO){
        noteDao.update(note)
    }

    suspend fun deleteR(note: NoteData)= withContext(Dispatchers.IO){
        noteDao.delete(note)
    }

    suspend fun updateM(id:Int,title:String,description:String,dateAndTime:String)= withContext(Dispatchers.IO){
        noteDao.updateManually(id,title,description, dateAndTime )
    }

    fun getList()=noteDao.getAll()

}