package com.weesnerdevelopment.serialcabinet.views

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weesnerdevelopment.serialcabinet.components.ChoiceChip
import com.weesnerdevelopment.serialcabinet.components.DeletableChip
import com.weesnerdevelopment.serialcabinet.components.EditText
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Electronic

private val Modifier.basePadding get() = padding(2.dp, 8.dp)
private val Modifier.fullWidthWPadding get() = fillMaxWidth().basePadding

@Composable
fun ModifySerialItem(item: CabinetItem? = null) {
    Column(
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    ) {
        EditText("Name", item?.name, Modifier.fullWidthWPadding) {

        }

        EditText("Description", item?.description, Modifier.fullWidthWPadding) {

        }

        ScrollableRow {
            ChoiceChip(text = "Add Category", Modifier.basePadding) {

            }
            for (category in item?.categories ?: listOf()) {
                DeletableChip(text = category.name, Modifier.basePadding) {

                }
            }
        }

        val electronicItem = item as? Electronic

        Row {
            EditText("Barcode", electronicItem?.barcode, Modifier.basePadding) {

            }

            electronicItem?.barcodeImage?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        Modifier.basePadding.width(96.dp).height(48.dp)
                    )
                }
            }
        }

        EditText("Serial Number", electronicItem?.serialNumber, Modifier.fullWidthWPadding) {

        }

        EditText("Model Number", electronicItem?.modelNumber, Modifier.fullWidthWPadding) {

        }

        Box(Modifier.fillMaxSize()) {
            Button(
                onClick = {

                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(text = if (item != null) "Update" else "Save")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItemNullItem() {
    SerialCabinetTheme {
        ModifySerialItem()
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItem() {
    SerialCabinetTheme {
        ModifySerialItem(item = mockCabinetItem)
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewElectronicSerialItem() {
    SerialCabinetTheme {
        ModifySerialItem(item = mockElectronicCabinetItem)
    }
}
