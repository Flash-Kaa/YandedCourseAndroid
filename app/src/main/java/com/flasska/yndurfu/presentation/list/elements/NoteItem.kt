package com.flasska.yndurfu.presentation.list.elements

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flasska.yndurfu.R
import com.flasska.yndurfu.domain.entity.Important
import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.presentation.edit.utils.getTextValue
import com.flasska.yndurfu.ui.theme.YndUrfuTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.math.abs

private const val X_OFFSET_TO_DELETE = 30

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) = with(note) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val draggableState = rememberDraggableState { delta ->
        coroutineScope.launch {
            offsetX.animateTo(delta + offsetX.value)
        }
    }
    val rotation by animateFloatAsState(
        targetValue = 60f * (offsetX.value / X_OFFSET_TO_DELETE).coerceIn(-1f, 1f),
        animationSpec = tween(durationMillis = 100, easing = LinearEasing),
        label = "rotation",
    )

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color(color).copy(alpha = 0.3f),
        ),
        modifier = Modifier
            .graphicsLayer {
                alpha = 1f - 0.3f * (abs(offsetX.value) / X_OFFSET_TO_DELETE).coerceIn(0f, 1f)
                translationX = rotation
                translationY = abs(rotation)
                rotationZ = rotation
            }
            .draggable(
                state = draggableState,
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    if (abs(offsetX.value) >= X_OFFSET_TO_DELETE) {
                        onDelete()
                    }
                    offsetX.animateTo(0f)
                }
            )
            .drawWithContent {
                val progress = offsetX.value / X_OFFSET_TO_DELETE
                val colorStops = if (offsetX.value >= 0) {
                    arrayOf(
                        0f to Color.Black,
                        (1f - 0.6f * progress.coerceIn(0f, 1f)) to Color.Black,
                        1f to Color.Transparent
                    )
                } else {
                    arrayOf(
                        0f to Color.Transparent,
                        (0.4f * (-progress).coerceIn(0f, 1f)) to Color.Black,
                        1f to Color.Black
                    )
                }

                drawContent()
                drawRect(
                    brush = Brush.horizontalGradient(
                        colorStops = colorStops,
                    ),
                    blendMode = BlendMode.DstIn
                )
            }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
            )

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            Text(text = content)

            Spacer(Modifier.height(16.dp))

            Text(
                text = important.getTextValue(context),
            )

            deleteDateTime?.let {
                Text(
                    text = stringResource(R.string.delete_date, it.toLocalDate()),
                )
            }

            Spacer(Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = onDelete
                ) {
                    Text(
                        text = stringResource(R.string.delete),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    YndUrfuTheme {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.width(220.dp)
            ) {
                NoteItem(
                    note = Note(
                        title = "this is title",
                        color = Color.Cyan.toArgb(),
                        content = "this is content",
                        important = Important.Important,
                        deleteDateTime = LocalDateTime.now(),
                    ),
                    onClick = {},
                    onDelete = {}
                )
            }
        }
    }
}
