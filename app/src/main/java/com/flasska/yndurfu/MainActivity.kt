package com.flasska.yndurfu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.flasska.yndurfu.ui.theme.YndUrfuTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val notebookRepository by lazy {
        CustomDi.getFileNotebookRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.plant(Timber.DebugTree())

        setContent {
            YndUrfuTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        notebookRepository.load()
    }

    override fun onStop() {
        super.onStop()
        notebookRepository.save()
    }
}