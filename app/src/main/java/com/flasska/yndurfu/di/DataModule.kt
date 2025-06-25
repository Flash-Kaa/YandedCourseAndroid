package com.flasska.yndurfu.di

import android.content.Context
import com.flasska.yndurfu.data.FileNotebookRepositoryImpl
import com.flasska.yndurfu.data.NotebookManagerImpl
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import com.flasska.yndurfu.domain.interfaces.NotebookManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideFileNotebookRepository(context: Context): FileNotebookRepository {
        return FileNotebookRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideNotebookManager(
        notebookRepository: FileNotebookRepository,
        coroutineScope: CoroutineScope,
    ): NotebookManager = NotebookManagerImpl(
        notebookRepository = notebookRepository,
        coroutineScope = coroutineScope,
    )

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)
}
