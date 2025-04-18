package com.flasska.yndurfu.di

import android.content.Context
import com.flasska.yndurfu.domain.entity.FileNotebookRepositoryImpl
import com.flasska.yndurfu.domain.interfaces.FileNotebookRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideFileNotebookRepository(context: Context) : FileNotebookRepository {
        return FileNotebookRepositoryImpl(context)
    }
}
