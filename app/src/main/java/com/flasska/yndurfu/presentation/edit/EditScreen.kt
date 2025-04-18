package com.flasska.yndurfu.presentation.edit

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.flasska.yndurfu.R
import com.flasska.yndurfu.presentation.edit.elements.ColorPicker
import com.flasska.yndurfu.presentation.edit.elements.DeleteDatePicker
import com.flasska.yndurfu.presentation.edit.elements.ImportantPicker

@Composable
internal fun EditScreen(
    screenState: EditScreenState,
    screenEvent: (EditScreenEvent) -> Unit,
    navigateBack: () -> Unit,
) {
    val scroll = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.edit_screen_arrangement)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(
                top = dimensionResource(R.dimen.edit_screen_padding_top),
                start = dimensionResource(R.dimen.edit_screen_padding_start),
                end = dimensionResource(R.dimen.edit_screen_padding_end),
            )
            .verticalScroll(scroll)
    ) {
        TextField(
            value = screenState.title,
            onValueChange = { screenEvent(EditScreenEvent.UpdateTitle(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text(text = stringResource(R.string.note_name))
            }
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = screenState.text,
            onValueChange = { screenEvent(EditScreenEvent.UpdateText(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(R.string.note_content))
            }
        )

        DeleteDatePicker(
            screenState = screenState,
            screenEvent = screenEvent,
        )

        ColorPicker(
            chosen = screenState.color,
            onChoose = { screenEvent(EditScreenEvent.UpdateColor(it)) },
        )

        ImportantPicker(
            chosen = screenState.important,
            onChoose = { screenEvent(EditScreenEvent.UpdateImportant(it)) }
        )
    }
}
