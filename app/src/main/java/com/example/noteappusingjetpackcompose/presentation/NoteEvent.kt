package com.example.noteappusingjetpackcompose.presentation

import android.icu.text.CaseMap.Title
import com.example.noteappusingjetpackcompose.data.NoteEntity

sealed interface NoteEvent {
    object sortNotes : NoteEvent

    data class DeleteNotes(var noteEntity: NoteEntity) : NoteEvent
    data class SaveNotes(
        var title: String,
        var desc: String
    ) : NoteEvent
}