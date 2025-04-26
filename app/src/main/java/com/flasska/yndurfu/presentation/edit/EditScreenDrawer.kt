package com.flasska.yndurfu.presentation.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasska.yndurfu.appComponent

@Composable
internal fun EditScreenDrawer(
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val vm =  viewModel<EditScreenViewModel>(
        factory = context.appComponent.provideEditViewModel()
    )
    val state by vm.state.collectAsStateWithLifecycle()
    EditScreen(
        screenState = state,
        screenEvent = vm::processEvent,
        navigateBack = navigateBack,
    )
}
