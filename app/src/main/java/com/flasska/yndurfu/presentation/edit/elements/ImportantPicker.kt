package com.flasska.yndurfu.presentation.edit.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.flasska.yndurfu.R
import com.flasska.yndurfu.domain.entity.Important
import com.flasska.yndurfu.presentation.edit.utils.getTextValue

@Composable
internal fun ImportantPicker(
    chosen: Important,
    onChoose: (Important) -> Unit,
) {
    val context = LocalContext.current
    val scroll = rememberScrollState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.important_picker_arrangement)
        ),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.important_picker_paddings))
            .horizontalScroll(scroll)
    ) {
        Important.entries.forEach { important ->
            val isChosen = important == chosen
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.important_round)))
                    .border(
                        width = dimensionResource(R.dimen.border_width),
                        color = if (isChosen) MaterialTheme.colorScheme.secondary else Color.Transparent,
                        shape = RoundedCornerShape(dimensionResource(R.dimen.important_round)),
                    )
                    .background(
                        color = if (isChosen) MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f) else Color.Transparent,
                        shape = RoundedCornerShape(dimensionResource(R.dimen.important_round)),
                    )
                    .clickable(enabled = !isChosen) { onChoose(important) }
                    .padding(8.dp)
            ) {
                Text(
                    text = important.getTextValue(context)
                )
            }
        }
    }
}
