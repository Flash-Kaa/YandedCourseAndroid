package com.flasska.yndurfu.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.yndurfu.domain.interfaces.NotebookManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ListScreenViewModel(
    private val notebookManager: NotebookManager,
) : ViewModel() {
    val list = notebookManager.notes

    fun processEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.DeleteItem -> deleteItem(event)
        }
    }

    private fun deleteItem(event: ListScreenEvent.DeleteItem) {
        viewModelScope.launch(Dispatchers.Default) {
            notebookManager.deleteById(event.id)
        }
    }

    class Factory(
        private val notebookManager: NotebookManager,
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListScreenViewModel(
                notebookManager = notebookManager,
            ) as T
        }
    }
}
