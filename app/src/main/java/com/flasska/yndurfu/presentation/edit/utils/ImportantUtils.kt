package com.flasska.yndurfu.presentation.edit.utils

import android.content.Context
import com.flasska.yndurfu.R
import com.flasska.yndurfu.domain.entity.Important

fun Important.getTextValue(context: Context): String {
    return when (this) {
        Important.Unimportant -> context.getString(R.string.unimportant)
        Important.Common -> context.getString(R.string.common)
        Important.Important -> context.getString(R.string.important)
    }
}
