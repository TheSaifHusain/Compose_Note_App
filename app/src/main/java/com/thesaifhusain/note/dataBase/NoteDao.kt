package com.thesaifhusain.note.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteData: NoteData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteData: NoteData)

    @Query(
        value = "UPDATE NoteTable SET " +
            "title=:title," +
            "description=:description," +
            "dateAndTime=:dateAndTime," +
            "textSize=:textSize," +
            "isTextAlignCenter=:isCenter," +
            "isTextAlignRight=:isRight," +
            "isTextBold=:isBold," +
            "isTextUnderline=:isUnderline" +
            " WHERE id LIKE :id"
    )
    suspend fun updateManually(
        id: Int,
        title: String,
        description: String,
        dateAndTime: String,
        textSize: Int,
        isCenter: Boolean,
        isRight: Boolean,
        isBold: Boolean,
        isUnderline: Boolean
    )

    @Delete
    suspend fun delete(noteData: NoteData)

    @Query("SELECT * FROM NoteTable")
    fun getAll(): LiveData<List<NoteData>>

    @Query("DELETE FROM NoteTable")
    fun deleteAll()
}
