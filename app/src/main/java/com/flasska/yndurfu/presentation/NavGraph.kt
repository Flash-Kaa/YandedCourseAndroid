package com.flasska.yndurfu.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.flasska.yndurfu.presentation.edit.EditScreen
import com.flasska.yndurfu.presentation.edit.editScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EditScreen,
        modifier = modifier,
    ) {
        editScreen(navController::navigateUp)
    }
}
