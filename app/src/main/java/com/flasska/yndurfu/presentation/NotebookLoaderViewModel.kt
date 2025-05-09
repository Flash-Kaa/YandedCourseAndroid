package com.flasska.yndurfu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.yndurfu.domain.interfaces.NotebookManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotebookLoaderViewModel(
    private val notebookManager: NotebookManager,
) : ViewModel() {

    fun load() {
        viewModelScope.launch(Dispatchers.Default) {
            notebookManager.load()
        }
    }

    fun save() {
        viewModelScope.launch(Dispatchers.Default) {
            notebookManager.save()
        }
    }

    class Factory(
        private val notebookManager: NotebookManager,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotebookLoaderViewModel(
                notebookManager = notebookManager,
            ) as T
        }
    }
}