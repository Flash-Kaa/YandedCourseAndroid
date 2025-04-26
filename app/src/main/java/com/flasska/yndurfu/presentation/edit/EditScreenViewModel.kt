package com.flasska.yndurfu.presentation.edit

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.yndurfu.domain.entity.Important
import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.domain.interfaces.NotebookManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

internal class EditScreenViewModel(
    private val notebookManager: NotebookManager,
) : ViewModel() {

    private val _state = MutableStateFlow(EditScreenState())
    val state = _state.asStateFlow()

    fun loadFromId(id: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            val state = id?.let {
                notebookManager.getByIdOrNull(id)?.let { note ->
                    EditScreenState(
                        id = note.uid,
                        title = note.title,
                        text = note.content,
                        addDeleteDate = note.deleteDateTime != null,
                        deleteDate = note.deleteDateTime ?: LocalDateTime.now(),
                        color = Color(note.color),
                        important = note.important,
                    )
                }
            } ?: EditScreenState()

            _state.update { state }
        }
    }

    fun processEvent(event: EditScreenEvent) {
        when (event) {
            is EditScreenEvent.UpdateTitle -> updateTitle(event.value)
            is EditScreenEvent.UpdateText -> updateText(event.value)
            is EditScreenEvent.UpdateDeleteDate -> updateDeleteDate(event.value)
            is EditScreenEvent.UpdateImportant -> updateImportant(event.value)
            is EditScreenEvent.UpdateColor -> updateColor(event.value)
            EditScreenEvent.UpdateAddingDeleteDate -> updateAddingDeleteDate()
            EditScreenEvent.SaveEdit -> saveEdit()
        }
    }

    private fun updateTitle(title: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update { it.copy(title = title) }
            updateCreateButtonIsEnabled()
        }
    }

    private fun updateText(text: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update { it.copy(text = text) }
            updateCreateButtonIsEnabled()
        }
    }

    private fun updateColor(color: Color) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update { it.copy(color = color) }
        }
    }

    private fun updateAddingDeleteDate() {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update { it.copy(addDeleteDate = it.addDeleteDate.not()) }
        }
    }

    private fun updateDeleteDate(date: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update {
                it.copy(
                    deleteDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.UTC)
                )
            }
        }
    }

    private fun updateImportant(important: Important) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update { it.copy(important = important) }
        }
    }

    private fun updateCreateButtonIsEnabled() {
        _state.update { value ->
            value.copy(
                createButtonIsEnabled = value.title.isNotBlank() && value.text.isNotBlank()
            )
        }
    }

    private fun saveEdit() = with (state.value) {
        viewModelScope.launch(Dispatchers.Default) {
            notebookManager.add(
                Note(
                    uid = id ?: UUID.randomUUID().toString(),
                    title = title,
                    content = text,
                    color = color.toArgb(),
                    important = important,
                    deleteDateTime = if (addDeleteDate) deleteDate else null,
                )
            )
        }
    }

    class Factory(
        private val notebookManager: NotebookManager,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditScreenViewModel(
                notebookManager = notebookManager,
            ) as T
        }
    }
}
