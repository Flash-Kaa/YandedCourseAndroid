package com.flasska.yndurfu.presentation.edit

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data object EditScreen

fun NavHostController.navigateToEdit() {
    navigate(EditScreen)
}

fun NavGraphBuilder.editScreen(
    navigateBack: () -> Unit,
) {
    composable<EditScreen> {
        EditScreenDrawer(
            navigateBack = navigateBack,
        )
    }
}
