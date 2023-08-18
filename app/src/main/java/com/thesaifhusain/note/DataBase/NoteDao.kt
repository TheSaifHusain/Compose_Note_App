package com.thesaifhusain.note.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteData: NoteData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteData: NoteData)

    @Query(value = "UPDATE NoteTable SET title=:title,description=:description Where id LIKE :id")
    suspend fun updateManually(id:Int,title:String,description:String)


    @Delete
    suspend fun delete(noteData: NoteData)

    @Query("SELECT * FROM NoteTable")
    fun getAll(): LiveData<List<NoteData>>
}