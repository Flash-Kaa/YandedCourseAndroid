package com.flasska.yndurfu.di

import android.content.Context
import com.flasska.yndurfu.presentation.NotebookLoaderViewModel
import com.flasska.yndurfu.presentation.edit.EditScreenViewModel
import com.flasska.yndurfu.presentation.list.ListScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [VmFactoryModule::class, DataModule::class])
internal interface AppComponent {

    fun provideEditViewModelFactory() : EditScreenViewModel.Factory
    fun provideListViewModelFactory() : ListScreenViewModel.Factory
    fun provideNotebookLoaderViewModelFactory() : NotebookLoaderViewModel.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
