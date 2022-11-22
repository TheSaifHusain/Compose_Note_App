package com.thesaifhusain.note.Di

import android.content.Context
import androidx.room.Room
import com.thesaifhusain.note.DataBase.NoteDao
import com.thesaifhusain.note.DataBase.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun getDbName():String="RoomDb"

    @Provides
    @Singleton
    fun getRoomDb(@ApplicationContext context: Context,name:String): NoteDataBase =
        Room.databaseBuilder(context, NoteDataBase::class.java,name).build()

    @Provides
    @Singleton
    fun getNoteDao(db: NoteDataBase): NoteDao =db.getNoteDao()
}