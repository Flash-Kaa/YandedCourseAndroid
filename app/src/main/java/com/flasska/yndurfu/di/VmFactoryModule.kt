package com.flasska.yndurfu.di

import com.flasska.yndurfu.domain.interfaces.NotebookManager
import com.flasska.yndurfu.presentation.NotebookLoaderViewModel
import com.flasska.yndurfu.presentation.edit.EditScreenViewModel
import com.flasska.yndurfu.presentation.list.ListScreenViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class VmFactoryModule {
    @Singleton
    @Provides
    fun provideEditViewModel(
        notebookManager: NotebookManager,
    ) = EditScreenViewModel.Factory(
        notebookManager = notebookManager,
    )

    @Singleton
    @Provides
    fun provideListViewModel(
        notebookManager: NotebookManager,
    ) = ListScreenViewModel.Factory(
        notebookManager = notebookManager,
    )

    @Provides
    @Singleton
    fun provideNotebookLoaderViewModel(
        notebookManager: NotebookManager,
    ) = NotebookLoaderViewModel.Factory(
        notebookManager = notebookManager,
    )
}
