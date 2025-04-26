package com.flasska.yndurfu.presentation.edit

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal data class EditScreen(val id: String? = null)

internal fun NavHostController.navigateToEdit(editScreen: EditScreen) {
    navigate(editScreen)
}

internal fun NavGraphBuilder.editScreen(
    navigateBack: () -> Unit,
) {
    composable<EditScreen> {
        EditScreenDrawer(
            navigateBack = navigateBack,
            editScreen = it.toRoute(),
        )
    }
}
