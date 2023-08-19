package com.thesaifhusain.note.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesaifhusain.note.DataBase.NoteData
import com.thesaifhusain.note.Repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository):ViewModel() {

    val getAll=repository.getList()


    fun vmInsert(noteData: NoteData)=viewModelScope.launch {
        repository.insertR(noteData)
    }

    fun vmUpdate(noteData: NoteData)=viewModelScope.launch {
        repository.updateR(noteData)
    }

    fun vmDelete(noteData: NoteData)=viewModelScope.launch {
        repository.deleteR(noteData)
    }

    fun vmUpdateManually(id:Int,title:String,description:String, dateAndTime: String)=viewModelScope.launch {
        repository.updateM(id,title,description, dateAndTime)
    }
}