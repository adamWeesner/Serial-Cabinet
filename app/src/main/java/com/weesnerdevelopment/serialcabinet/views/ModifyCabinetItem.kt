package com.weesnerdevelopment.serialcabinet.views

import android.app.Activity
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.basePadding
import com.weesnerdevelopment.serialcabinet.components.ChoiceChip
import com.weesnerdevelopment.serialcabinet.components.DeletableChip
import com.weesnerdevelopment.serialcabinet.components.EditText
import com.weesnerdevelopment.serialcabinet.fullWidthWPadding
import com.weesnerdevelopment.serialcabinet.viewmodels.ModifyCabinetItemViewModel
import shared.serialCabinet.CabinetItem

@Composable
fun ModifySerialItem(
    viewModel: ModifyCabinetItemViewModel,
    item: CabinetItem? = null,
    barcodeCamera: () -> Unit
) {
    val context = AmbientContext.current

    if (item != null) viewModel.currentItem(item)

    val name by viewModel.name.value.observeAsState()
    val description by viewModel.description.value.observeAsState()
    val categories by viewModel.categories.value.observeAsState()
    val barcode by viewModel.barcode.value.observeAsState()
    val barcodeImage by viewModel.barcodeImage.value.observeAsState()
    val serialNumber by viewModel.serialNumber.value.observeAsState()
    val modelNumber by viewModel.modelNumber.value.observeAsState()

    Column(
        Modifier.padding(dimensionResource(id = R.dimen.space_default)).fillMaxWidth()
    ) {
        EditText(
            stringResource(R.string.name),
            name,
            Modifier.fullWidthWPadding
        ) {
            viewModel.name.setValue(it)
        }

        EditText(
            stringResource(R.string.description),
            description,
            Modifier.fullWidthWPadding
        ) {
            viewModel.description.setValue(it)
        }

        ScrollableRow {
            ChoiceChip(stringResource(R.string.add_category), Modifier.basePadding) {

            }
            for (category in categories ?: listOf()) {
                DeletableChip(category.name, Modifier.basePadding) {

                }
            }
        }

        Row {
            EditText(
                stringResource(R.string.barcode),
                barcode,
                Modifier.basePadding.weight(1.5f)
            ) {
                viewModel.barcode.setValue(it)
            }

            barcodeImage?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        Modifier.basePadding.weight(1f).heightIn(48.dp, 56.dp)
                    )
                }
            } ?: Button(
                onClick = {
                    val cameraPerm = arrayOf(android.Manifest.permission.CAMERA)
                    if (!hasPermissions(context, cameraPerm))
                        requestPermissions(context as Activity, cameraPerm, 999)
                    else
                        barcodeCamera()
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
            serialNumber,
            Modifier.fullWidthWPadding
        ) {
            viewModel.serialNumber.setValue(it)
        }

        EditText(
            stringResource(R.string.model_number),
            modelNumber,
            Modifier.fullWidthWPadding
        ) {
            viewModel.modelNumber.setValue(it)
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
