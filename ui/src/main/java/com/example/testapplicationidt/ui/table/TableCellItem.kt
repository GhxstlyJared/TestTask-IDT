package com.example.testapplicationidt.ui.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.testapplicationidt.domain.model.TableCell
import com.example.testapplicationidt.ui.theme.CellHighlight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableCellItem(
    cell: TableCell,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (cell.isHighlighted) {
        CellHighlight
    } else {
        MaterialTheme.colorScheme.surface
    }
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(backgroundColor)
            .combinedClickable(
                onClick = onClick,
                onDoubleClick = onDoubleClick,
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = cell.value,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
