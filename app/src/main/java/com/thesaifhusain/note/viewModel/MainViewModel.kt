package com.thesaifhusain.note.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesaifhusain.note.dataBase.NoteData
import com.thesaifhusain.note.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val repository: Repository
) : ViewModel() {

    val getAll = repository.getList()

    fun vmInsert(noteData: NoteData) = viewModelScope.launch {
        repository.insertR(noteData)
    }

    fun vmUpdate(noteData: NoteData) = viewModelScope.launch {
        repository.updateR(noteData)
    }

    fun vmDelete(noteData: NoteData) = viewModelScope.launch {
        repository.deleteR(noteData)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.deleteAll()
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("DELETE_ALL_ERROR", it) }
        }
    }

    fun vmUpdateManually(
        id: Int,
        title: String,
        description: String,
        dateAndTime: String,
        textSize: Int,
        isCenter: Boolean,
        isRight: Boolean,
        isBold: Boolean,
        isUnderline: Boolean
    ) = viewModelScope.launch {
        repository.updateM(
            id, title, description, dateAndTime, textSize, isCenter, isRight, isBold, isUnderline
        )
        Log.e("M_UPDATE", "M_UPDATE")
    }
}
