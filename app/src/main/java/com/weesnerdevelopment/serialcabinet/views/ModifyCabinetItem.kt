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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.components.ChoiceChip
import com.weesnerdevelopment.serialcabinet.components.DeletableChip
import com.weesnerdevelopment.serialcabinet.components.EditText
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Electronic

@Composable
private val Modifier.basePadding
    get() = padding(
        dimensionResource(R.dimen.space_default),
        dimensionResource(R.dimen.space_default)
    )

@Composable
private val Modifier.fullWidthWPadding
    get() = fillMaxWidth().basePadding

@Composable
fun ModifySerialItem(item: CabinetItem? = null) {
    Column(
        Modifier.padding(dimensionResource(id = R.dimen.space_default)).fillMaxWidth()
    ) {
        EditText(stringResource(R.string.name), item?.name, Modifier.fullWidthWPadding) {

        }

        EditText(
            stringResource(R.string.description),
            item?.description,
            Modifier.fullWidthWPadding
        ) {

        }

        ScrollableRow {
            ChoiceChip(stringResource(R.string.add_category), Modifier.basePadding) {

            }
            for (category in item?.categories ?: listOf()) {
                DeletableChip(category.name, Modifier.basePadding) {

                }
            }
        }

        val electronicItem = item as? Electronic

        Row {
            EditText(
                stringResource(R.string.barcode),
                electronicItem?.barcode,
                Modifier.basePadding.weight(1.5f)
            ) {

            }

            electronicItem?.barcodeImage?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        Modifier.basePadding.weight(1f)
                    )
                }
            } ?: Button(
                {

                },
                Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = dimensionResource(R.dimen.space_default))
                    .weight(1f),
            ) {
                Text(
                    text = stringResource(R.string.barcode_image),
                    textAlign = TextAlign.Center
                )
            }
        }

        EditText(
            stringResource(R.string.serial_number),
            electronicItem?.serialNumber,
            Modifier.fullWidthWPadding
        ) {

        }

        EditText(
            stringResource(R.string.model_number),
            electronicItem?.modelNumber,
            Modifier.fullWidthWPadding
        ) {

        }

        Box(Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier.align(Alignment.BottomEnd).basePadding,
                onClick = {

                },
            ) {
                Text(if (item != null) stringResource(R.string.update) else stringResource(R.string.save))
            }
        }
    }
}
