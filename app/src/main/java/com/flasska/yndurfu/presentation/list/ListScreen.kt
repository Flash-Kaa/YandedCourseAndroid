package com.flasska.yndurfu.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.flasska.yndurfu.R
import com.flasska.yndurfu.domain.entity.Note
import com.flasska.yndurfu.presentation.edit.EditScreen
import com.flasska.yndurfu.presentation.list.elements.NoteItem

@Composable
internal fun ListScreen(
    notes: List<Note>,
    screenEvent: (ListScreenEvent) -> Unit,
    navigateToEdit: (EditScreen) -> Unit,
) {
    val configuration = LocalConfiguration.current
    Scaffold(
        floatingActionButton = {
            Button(
                onClick = { navigateToEdit(EditScreen(null)) },
                shape = CircleShape,
                modifier = Modifier.size(96.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.rounded_add_24),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    ) { innerPaddings ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(configuration.orientation + 1), // land: 2, port: 3
            contentPadding = innerPaddings,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalItemSpacing = 24.dp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onClick = { navigateToEdit(EditScreen(note.uid)) },
                    onDelete = { screenEvent(ListScreenEvent.DeleteItem(note.uid)) }
                )
            }
        }
    }
}
