package com.flasska.yndurfu.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.flasska.yndurfu.appComponent
import com.flasska.yndurfu.presentation.edit.editScreen
import com.flasska.yndurfu.presentation.edit.navigateToEdit
import com.flasska.yndurfu.presentation.list.ListScreen
import com.flasska.yndurfu.presentation.list.listScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ListScreen,
        modifier = modifier,
    ) {
        listScreen(navController::navigateToEdit)
        editScreen(navController::navigateUp)
    }
}
