package com.flasska.yndurfu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.flasska.yndurfu.presentation.NavGraph
import com.flasska.yndurfu.presentation.NotebookLoaderViewModel
import com.flasska.yndurfu.ui.theme.YndUrfuTheme

class MainActivity : ComponentActivity() {

    private val notebookLoaderViewModel by viewModels<NotebookLoaderViewModel> {
        appComponent.provideNotebookLoaderViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YndUrfuTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        notebookLoaderViewModel.load()
        super.onResume()
    }

    override fun onPause() {
        notebookLoaderViewModel.save()
        super.onPause()
    }
}
