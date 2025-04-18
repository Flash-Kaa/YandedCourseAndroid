package com.flasska.yndurfu.presentation.edit.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.flasska.yndurfu.R

private val colors = listOf(
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Cyan,
    Color.Yellow,
    Color.LightGray,
    Color.Magenta,
)

@Composable
internal fun ColorPicker(
    chosen: Color,
    onChoose: (Color) -> Unit
) {
    val scroll = rememberScrollState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.horizontalScroll(scroll)
    ) {
        colors.forEach { color ->
            ColorBox(
                color = color,
                isChosen = color == chosen,
                onChoose = onChoose
            )
        }

        if (chosen !in colors) {
            ColorBox(
                color = chosen,
                isChosen = true,
                onChoose = onChoose
            )
        }
    }
}

@Composable
private fun ColorBox(
    color: Color,
    isChosen: Boolean,
    onChoose: (Color) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.color_box_paddings))
            .size(dimensionResource(R.dimen.color_box_size))
            .background(color)
            .border(
                width = dimensionResource(R.dimen.border_width),
                color = if (isChosen) MaterialTheme.colorScheme.secondary else Color.Transparent
            )
            .clickable(enabled = !isChosen) { onChoose(color) }
    ) {
        if (isChosen) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.check_small),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(dimensionResource(R.dimen.color_box_check_icon_size))
            )
        }
    }
}
