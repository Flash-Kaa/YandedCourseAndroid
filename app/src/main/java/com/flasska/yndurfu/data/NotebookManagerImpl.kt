package com.flasska.yndurfu.data

import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import com.flasska.yndurfu.domain.interfaces.NotebookManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

internal class NotebookManagerImpl(
    private val notebookRepository: FileNotebookRepository,
    private val coroutineScope: CoroutineScope,
) : NotebookManager {
    override val notes = notebookRepository.notesFlow

    override suspend fun add(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            notebookRepository.add(note)
        }
    }

    override suspend fun getByIdOrNull(id: String): Note? {
        return coroutineScope.async(Dispatchers.IO) {
            notebookRepository.notesFlow.firstOrNull()?.firstOrNull { it.uid == id }
        }.await()
    }

    override suspend fun deleteById(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            notebookRepository.delete(id)
        }
    }

    override suspend fun save() {
        coroutineScope.launch(Dispatchers.IO) {
            notebookRepository.save()
        }
    }

    override suspend fun load() {
        coroutineScope.launch(Dispatchers.IO) {
            notebookRepository.load()
        }
    }
}
