package com.flasska.yndurfu

import android.content.Context
import com.flasska.yndurfu.domain.entity.FileNotebookRepository

object CustomDi {
    fun getFileNotebookRepository(context: Context) = FileNotebookRepository(context)
}