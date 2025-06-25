package com.flasska.yndurfu.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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
