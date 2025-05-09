package com.flasska.yndurfu.di

import android.content.Context
import com.flasska.yndurfu.data.FileNotebookRepositoryImpl
import com.flasska.yndurfu.data.NotebookManagerImpl
import com.flasska.yndurfu.data.NotebookNetworkManagerImpl
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import com.flasska.yndurfu.domain.interfaces.NotebookManager
import com.flasska.yndurfu.domain.interfaces.NotebookNetworkManager
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
        notebookNetworkManager: NotebookNetworkManager,
        coroutineScope: CoroutineScope,
    ): NotebookManager = NotebookManagerImpl(
        fileNotebookRepository = notebookRepository,
        coroutineScope = coroutineScope,
        notebookNetworkManager = notebookNetworkManager,
    )

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)

    @Singleton
    @Provides
    fun provideNotebookNetworkManager(
    ): NotebookNetworkManager = NotebookNetworkManagerImpl(
    )
}
