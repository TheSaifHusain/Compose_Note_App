package com.thesaifhusain.note.repository

import com.thesaifhusain.note.dataBase.NoteDao
import com.thesaifhusain.note.dataBase.NoteData
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository @Inject constructor(private val noteDao: NoteDao) {

    suspend fun insertR(note: NoteData) = withContext(Dispatchers.IO) {
        noteDao.insert(note)
    }

    suspend fun updateR(note: NoteData) = withContext(Dispatchers.IO) {
        noteDao.update(note)
    }

    suspend fun deleteR(note: NoteData) = withContext(Dispatchers.IO) {
        noteDao.delete(note)
    }

    suspend fun updateM(
        id: Int,
        title: String,
        description: String,
        dateAndTime: String,
        textSize: Int,
        isTextAlignCenter: Boolean,
        isTextAlignRight: Boolean,
        isTextBold: Boolean,
        isTextUnderline: Boolean
    ) = withContext(Dispatchers.IO) {
        noteDao.updateManually(
            id,
            title,
            description,
            dateAndTime,
            textSize,
            isTextAlignCenter,
            isTextAlignRight,
            isTextBold,
            isTextUnderline
        )
    }

    fun getList() = noteDao.getAll()

    fun deleteAll() = noteDao.deleteAll()
}
