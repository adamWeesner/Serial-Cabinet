package com.weesnerdevelopment.serialcabinet.views

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme
import shared.serialCabinet.CabinetItem

@Composable
fun SerialItemsList(items: List<CabinetItem>) {
    if (items.isEmpty())
        Text(
            text = "No Serial Cabinet items, click the \"+\" to add one.",
            Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
    else
        ScrollableColumn {
            for (item in items)
                SerialItem(item = item)
        }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItemList() {
    SerialCabinetTheme {
        val items = listOf(
            mockCabinetItem,
            mockCabinetItem,
            mockCabinetItem,
            mockCabinetItem,
        )

        SerialItemsList(items)
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItemListEmpty() {
    SerialCabinetTheme {
        SerialItemsList(listOf())
    }
}
