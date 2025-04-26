package com.flasska.yndurfu.presentation.edit

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.yndurfu.domain.entity.Important
import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

internal class EditScreenViewModel(
    private val fileNotebookRepository: FileNotebookRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(EditScreenState())
    val state = _state.asStateFlow()

    fun processEvent(event: EditScreenEvent) {
        when (event) {
            is EditScreenEvent.UpdateTitle -> updateTitle(event.value)
            is EditScreenEvent.UpdateText -> updateText(event.value)
            is EditScreenEvent.UpdateDeleteDate -> updateDeleteDate(event.value)
            is EditScreenEvent.UpdateImportant -> updateImportant(event.value)
            is EditScreenEvent.UpdateColor -> updateColor(event.value)
            EditScreenEvent.UpdateAddingDeleteDate -> updateAddingDeleteDate()
            EditScreenEvent.Save -> save()
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

    private fun save() = with (state.value) {
        viewModelScope.launch(Dispatchers.Default) {
            fileNotebookRepository.add(
                Note(
                    title = title,
                    content = text,
                    color = color.toArgb(),
                    important = important,
                    deleteDateTime = deleteDate,
                )
            )
        }
    }

    class Factory(
        private val fileNotebookRepository: FileNotebookRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditScreenViewModel(
                fileNotebookRepository = fileNotebookRepository,
            ) as T
        }
    }
}
