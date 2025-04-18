package com.flasska.yndurfu.presentation.edit.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.flasska.yndurfu.R
import com.flasska.yndurfu.presentation.edit.EditScreenEvent
import com.flasska.yndurfu.presentation.edit.EditScreenState
import com.flasska.yndurfu.presentation.edit.NextDaysSelectable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DeleteDatePicker(
    screenState: EditScreenState,
    screenEvent: (EditScreenEvent) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.date_picker_arrangement))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.date_picker_arrangement)
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = screenState.addDeleteDate,
                onCheckedChange = { screenEvent(EditScreenEvent.UpdateAddingDeleteDate) }
            )

            Text(text = stringResource(R.string.add_destroy_date))
        }


        if (screenState.addDeleteDate) {
            var showDatePicker by remember { mutableStateOf(false) }
            val dateState = rememberDatePickerState(
                selectableDates = NextDaysSelectable,
                initialSelectedDateMillis = NextDaysSelectable.initialSelectedDateMillis,
            )
            Button(
                onClick = { showDatePicker = true }
            ) {
                Text(text = stringResource(R.string.select_date))
            }

            Text(text = stringResource(R.string.chosen, screenState.deleteDate.toLocalDate()))

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                dateState.selectedDateMillis?.let {
                                    screenEvent(EditScreenEvent.UpdateDeleteDate(it))
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text(text = stringResource(R.string.add))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDatePicker = false }
                        ) {
                            Text(text = stringResource(R.string.cancel))
                        }
                    }
                ) {
                    DatePicker(state = dateState)
                }
            }
        }
    }
}
