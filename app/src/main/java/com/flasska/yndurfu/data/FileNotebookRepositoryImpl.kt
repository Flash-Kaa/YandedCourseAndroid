package com.flasska.yndurfu.data

import android.content.Context
import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.domain.entity.parseToJson
import com.flasska.yndurfu.domain.entity.parseToNote
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID

class FileNotebookRepositoryImpl(
    context: Context,
) : FileNotebookRepository {
    private var _notesFlow = MutableStateFlow(emptySet<Note>())
    override val notesFlow = _notesFlow.asStateFlow().map { it.toList() }

    private val sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)

    override fun add(note: Note) {
        delete(note.uid)
        _notesFlow.update{ it + note }
    }

    override fun delete(uid: String) {
        _notesFlow.update{ notes -> notes.filter { it.uid != uid }.toSet() }
    }

    override fun save() {
        val value = _notesFlow.value.map { it.parseToJson().toString() }.toSet()

        sp.edit().putStringSet(STRING_SET_SP_KEY, value).apply()
    }

    override fun load() {
        _notesFlow.update { emptySet() }

        val now = LocalDateTime.now()

        sp.getStringSet(STRING_SET_SP_KEY, setOf())
            ?.mapNotNull { JSONObject(it).parseToNote() }
            ?.filter { it.deleteDateTime?.let { it >= now } != false }
            ?.let { notes -> _notesFlow.update{ notes.toSet() } }
    }

    companion object {
        private const val STRING_SET_SP_KEY = "notebook_content_sp_key"
        private const val SP_KEY = "notebook_sp_key"
    }
}
