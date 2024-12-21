package com.example.noteappusingjetpackcompose.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.noteappusingjetpackcompose.data.NoteEntity

data
class NoteState (


    val notes : List<NoteEntity> = emptyList(),
    val title : MutableState<String> = mutableStateOf(""),
    val desc: MutableState<String> = mutableStateOf("")
)