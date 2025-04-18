package com.flasska.yndurfu.presentation.edit

import androidx.compose.ui.graphics.Color
import com.flasska.yndurfu.domain.entity.Important

internal interface EditScreenEvent {
    data class UpdateTitle(val value: String) : EditScreenEvent
    data class UpdateText(val value: String) : EditScreenEvent
    data class UpdateDeleteDate(val value: Long) : EditScreenEvent
    data class UpdateImportant(val value: Important) : EditScreenEvent
    data class UpdateColor(val value: Color) : EditScreenEvent

    data object UpdateAddingDeleteDate : EditScreenEvent
    data object Save : EditScreenEvent
}
