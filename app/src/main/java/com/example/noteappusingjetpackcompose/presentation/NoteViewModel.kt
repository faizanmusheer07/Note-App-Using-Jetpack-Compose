package com.example.noteappusingjetpackcompose.presentation


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappusingjetpackcompose.data.NoteDao
import com.example.noteappusingjetpackcompose.data.NoteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(private var dao: NoteDao) : ViewModel() {
    private var isSortedByDateAdded = MutableStateFlow(true)
    private var notes = isSortedByDateAdded.flatMapLatest { sort ->
        if (sort) {
            dao.getOrderedByDateAddedBY()

        } else {
            dao.getOrderedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(NoteState())
    val state = combine(_state, isSortedByDateAdded, notes) { state, isSortedByDateAdded, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())


    fun OnEvent(event: NoteEvent) {


        when (event) {
            is NoteEvent.DeleteNotes -> {
                viewModelScope.launch {
                    dao.deleteNote(event.noteEntity)
                }
            }

            is NoteEvent.SaveNotes -> {
                val note = NoteEntity(
                    title = state.value.title.value,
                        desc = state.value.desc.value,
                        dateAdded = System.currentTimeMillis()

                )
                viewModelScope.launch {
                    dao.upsertNote(noteEntity = note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        desc = mutableStateOf("")
                    )
                }
            }

            NoteEvent.sortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }

        }
    }

}