package com.flasska.yndurfu.domain.entity

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import timber.log.Timber
import java.time.LocalDateTime
import java.util.UUID

class FileNotebookRepository(
    context: Context,
) {
    private var _notesFlow = MutableStateFlow(emptyList<Note>())
    val notesFlow = _notesFlow.asStateFlow()

    private val sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)

    fun add(note: Note) {
        Timber.i("Add $note")
        _notesFlow.update{ it + note }
    }

    fun delete(uid: UUID) {
        Timber.i("Delete by uid $uid")
        _notesFlow.update{ notes -> notes.filter { it.uid != uid } }
    }

    fun save() {
        val value = notesFlow.value.map { it.parseToJson().toString() }.toSet().also {
            Timber.i("Save notes: $it")
        }

        sp.edit().putStringSet(STRING_SET_SP_KEY, value).apply()
    }

    fun load() {
        _notesFlow.update { emptyList() }

        val now = LocalDateTime.now()

        sp.getStringSet(STRING_SET_SP_KEY, setOf())
            ?.mapNotNull { JSONObject(it).parseToNote() }
            ?.filter { it.deleteDateTime >= now }
            ?.let { notes ->
                Timber.i("Load notes: $notes")
                _notesFlow.update{ notes }
            }
    }

    companion object {
        private const val STRING_SET_SP_KEY = "notebook_content_sp_key"
        private const val SP_KEY = "notebook_sp_key"
    }
}