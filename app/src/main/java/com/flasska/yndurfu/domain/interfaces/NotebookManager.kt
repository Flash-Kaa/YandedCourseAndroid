package com.flasska.yndurfu.domain.interfaces

import com.flasska.yndurfu.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NotebookManager {
    val notes: Flow<List<Note>>
    suspend fun add(note: Note)
    suspend fun getByIdOrNull(id: String): Note?
    suspend fun deleteById(id: String)
    suspend fun save()
    suspend fun load()
}
