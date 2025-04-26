package com.flasska.yndurfu.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasska.yndurfu.appComponent
import com.flasska.yndurfu.presentation.edit.EditScreen

@Composable
internal fun ListScreenDrawer(
    navigateToEdit: (EditScreen) -> Unit,
) {
    val context = LocalContext.current
    val vm = viewModel<ListScreenViewModel>(
        factory = context.appComponent.provideListViewModelFactory()
    )

    val notes by vm.list.collectAsStateWithLifecycle(emptyList())
    ListScreen(
        screenEvent = vm::processEvent,
        notes = notes,
        navigateToEdit = navigateToEdit,
    )
}
