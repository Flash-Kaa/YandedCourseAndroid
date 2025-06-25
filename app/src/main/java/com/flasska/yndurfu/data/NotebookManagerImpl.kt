package com.flasska.yndurfu.data

import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import com.flasska.yndurfu.domain.interfaces.NotebookManager
import com.flasska.yndurfu.domain.interfaces.NotebookNetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

internal class NotebookManagerImpl(
    private val fileNotebookRepository: FileNotebookRepository,
    private val notebookNetworkManager: NotebookNetworkManager,
    private val coroutineScope: CoroutineScope,
) : NotebookManager {
    override val notes = fileNotebookRepository.notesFlow

    override suspend fun add(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            fileNotebookRepository.add(note)
            notebookNetworkManager.sendNotes(listOf(note))
        }
    }

    override suspend fun getByIdOrNull(id: String): Note? {
        return coroutineScope.async(Dispatchers.IO) {
            fileNotebookRepository.notesFlow.firstOrNull()?.firstOrNull { it.uid == id }
        }.await()
    }

    override suspend fun deleteById(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            fileNotebookRepository.delete(id)
            notebookNetworkManager.deleteNote(id)
        }
    }

    override suspend fun save() {
        coroutineScope.launch(Dispatchers.IO) {
            fileNotebookRepository.save()
            notebookNetworkManager.sendNotes(notes.firstOrNull() ?: emptyList())
        }
    }

    override suspend fun load() {
        coroutineScope.launch(Dispatchers.IO) {
            fileNotebookRepository.load()
            fileNotebookRepository.notesFlow.firstOrNull()?.let { notes ->
                notebookNetworkManager.sendNotes(notes)
                val netNotes = notebookNetworkManager.loadNotes()
                // TODO fileNotebookRepository.sendNotes(netNotes)
            }
        }
    }
}
