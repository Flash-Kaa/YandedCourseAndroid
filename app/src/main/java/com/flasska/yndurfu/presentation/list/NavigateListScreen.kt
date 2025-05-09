package com.flasska.yndurfu.presentation.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.flasska.yndurfu.presentation.edit.EditScreen
import kotlinx.serialization.Serializable

@Serializable
internal data object ListScreen

internal fun NavHostController.navigateToList() {
    navigate(ListScreen)
}

internal fun NavGraphBuilder.listScreen(
    navigateToEdit: (EditScreen) -> Unit,
) {
    composable<ListScreen> {
        ListScreenDrawer(navigateToEdit)
    }
}
