package com.weesnerdevelopment.serialcabinet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme

@Composable
fun DeletableChip(
    text: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
) = Chip(text = text, modifier = modifier, deletable = true, onDelete = onDelete)

@Composable
fun ChoiceChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit = {}
) = Chip(text = text, modifier = modifier, onDelete = {}, onClick = onClick)

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier,
    deletable: Boolean = false,
    onDelete: () -> Unit = {},
    selectable: Boolean = false,
    selected: Boolean = false,
    onClick: (Boolean) -> Unit = {}
) {
    val (currentlySelected, setCurrentlySelected) = remember { mutableStateOf(selected) }
    Surface(
        border = if (currentlySelected) BorderStroke(0.dp, MaterialTheme.colors.primary) else null,
        color = when {
            currentlySelected -> MaterialTheme.colors.primary.copy(alpha = 0.08f)
            else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            currentlySelected -> MaterialTheme.colors.primary
            else -> MaterialTheme.colors.onSurface
        },
        shape = CircleShape,
        modifier = modifier.clip(CircleShape).clickable(onClick = {
            if (selectable) setCurrentlySelected(!currentlySelected)
            onClick(currentlySelected)
        })
    ) {
        Row {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = if (deletable) 0.dp else 16.dp,
                        bottom = 8.dp,
                        top = 8.dp
                    )
                    .align(Alignment.CenterVertically)
            )

            if (deletable)
                Image(
                    imageVector = Icons.Outlined.Close,
                    modifier = modifier
                        .padding(end = 4.dp)
                        .align(Alignment.CenterVertically)
                        .height(24.dp)
                        .clip(CircleShape)
                        .clickable(onClick = {
                            onDelete()
                        })
                        .padding(4.dp)
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun previewChoiceChip() {
    SerialCabinetTheme {
        ChoiceChip(text = "Banana", modifier = Modifier.padding(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun previewChoiceChipDeletable() {
    SerialCabinetTheme {
        DeletableChip(
            text = "Banana",
            modifier = Modifier.padding(4.dp),
        ) {

        }
    }
}
