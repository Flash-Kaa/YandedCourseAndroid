package com.flasska.yndurfu.presentation.edit

import androidx.compose.ui.graphics.Color
import com.flasska.yndurfu.domain.entity.Important
import java.time.LocalDateTime

internal data class EditScreenState(
    val title: String = "",
    val text: String = "",
    val addDeleteDate: Boolean = false,
    val deleteDate: LocalDateTime = LocalDateTime.now(),
    val color: Color = Color.Green,
    val important: Important = Important.Common,
    val createButtonIsEnabled: Boolean = false,
)
