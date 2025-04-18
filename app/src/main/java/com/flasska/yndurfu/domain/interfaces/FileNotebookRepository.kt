package com.flasska.yndurfu.domain.interfaces

import com.flasska.yndurfu.domain.entity.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FileNotebookRepository {
    val notesFlow: Flow<List<Note>>

    fun add(note: Note)
    fun delete(uid: UUID)
    fun save()
    fun load()
}
