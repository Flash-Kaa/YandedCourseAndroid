package com.flasska.yndurfu.domain.interfaces

import com.flasska.yndurfu.domain.entity.Note

interface NotebookNetworkManager {

    fun loadNotes(): List<Note>
    fun sendNotes(notes: List<Note>)
    fun deleteNote(id: String)
}
