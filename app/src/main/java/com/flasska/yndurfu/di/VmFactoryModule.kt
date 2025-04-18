package com.flasska.yndurfu.di

import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import com.flasska.yndurfu.presentation.edit.EditScreenViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class VmFactoryModule {
    @Singleton
    @Provides
    fun provideEditViewModel(
        fileNotebookRepository: FileNotebookRepository,
    ) = EditScreenViewModel.Factory(
        fileNotebookRepository = fileNotebookRepository,
    )
}
