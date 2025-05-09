package com.flasska.yndurfu.data

import android.util.Log
import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.domain.interfaces.NotebookNetworkManager

class NotebookNetworkManagerImpl : NotebookNetworkManager {

    override fun loadNotes(): List<Note> {
        Log.i(TAG, "load notes")
        return emptyList<Note>().also { Log.i(TAG, "loaded notes: $it") }
    }

    override fun sendNotes(notes: List<Note>) {
        Log.i(TAG, "send notes: $notes")
    }

    override fun deleteNote(id: String) {
        Log.i(TAG, "delete note by id")
    }

    companion object {
        private const val TAG = "NotebookNetworkManagerImpl"
    }
}
