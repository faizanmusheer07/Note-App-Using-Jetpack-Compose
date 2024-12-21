package com.example.noteappusingjetpackcompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    fun upsertNote(noteEntity: NoteEntity)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM noteentity ORDER BY title ASC")
    fun getOrderedByTitle(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM noteentity ORDER BY dateAdded")
    fun getOrderedByDateAddedBY(): Flow<List<NoteEntity>>
}