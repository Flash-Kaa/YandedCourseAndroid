package com.flasska.yndurfu.domain.interfaces

import com.flasska.yndurfu.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface FileNotebookRepository {
    val notesFlow: Flow<List<Note>>

    fun add(note: Note)
    fun delete(uid: String)
    fun save()
    fun load()
    fun sendNotes(notes: List<Note>)
}
