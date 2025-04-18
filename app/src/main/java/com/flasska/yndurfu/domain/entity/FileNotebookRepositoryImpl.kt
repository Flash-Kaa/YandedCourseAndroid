package com.flasska.yndurfu.domain.entity

import android.content.Context
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID

class FileNotebookRepositoryImpl(
    context: Context,
) : FileNotebookRepository {
    private var _notesFlow = MutableStateFlow(emptyList<Note>())
    override val notesFlow = _notesFlow.asStateFlow()

    private val sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)

    override fun add(note: Note) {
        _notesFlow.update{ it + note }
    }

    override fun delete(uid: UUID) {
        _notesFlow.update{ notes -> notes.filter { it.uid != uid } }
    }

    override fun save() {
        val value = notesFlow.value.map { it.parseToJson().toString() }.toSet()

        sp.edit().putStringSet(STRING_SET_SP_KEY, value).apply()
    }

    override fun load() {
        _notesFlow.update { emptyList() }

        val now = LocalDateTime.now()

        sp.getStringSet(STRING_SET_SP_KEY, setOf())
            ?.mapNotNull { JSONObject(it).parseToNote() }
            ?.filter { it.deleteDateTime >= now }
            ?.let { notes -> _notesFlow.update{ notes } }
    }

    companion object {
        private const val STRING_SET_SP_KEY = "notebook_content_sp_key"
        private const val SP_KEY = "notebook_sp_key"
    }
}
