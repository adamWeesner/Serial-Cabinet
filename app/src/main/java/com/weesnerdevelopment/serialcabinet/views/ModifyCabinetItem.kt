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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.weesnerdevelopment.serialcabinet.components.BasicListItem
import com.weesnerdevelopment.serialcabinet.components.ChoiceChip
import com.weesnerdevelopment.serialcabinet.components.DeletableChip
import com.weesnerdevelopment.serialcabinet.components.EditText
import com.weesnerdevelopment.serialcabinet.fullWidthWPadding
import com.weesnerdevelopment.serialcabinet.viewmodels.CategoriesViewModel
import com.weesnerdevelopment.serialcabinet.viewmodels.ModifyCabinetItemViewModel
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Category

@Composable
fun ModifySerialItem(
    itemViewModel: ModifyCabinetItemViewModel,
    categoriesViewModel: CategoriesViewModel,
    item: CabinetItem? = null,
    barcodeCamera: () -> Unit
) {
    val context = AmbientContext.current
    val (choosingCategories, setChoosingCategories) = remember { mutableStateOf(false) }

    if (item != null) itemViewModel.currentItem(item)

    val name by itemViewModel.name.collectAsState()
    val description by itemViewModel.description.collectAsState()
    val categories by itemViewModel.categories.collectAsState()
    val barcode by itemViewModel.barcode.collectAsState()
    val barcodeImage by itemViewModel.barcodeImage.collectAsState()
    val serialNumber by itemViewModel.serialNumber.collectAsState()
    val modelNumber by itemViewModel.modelNumber.collectAsState()

    val allCategories by categoriesViewModel.allCategories.collectAsState()

    categoriesViewModel.getCategories()

    if (choosingCategories)
        ListDialog(
            items = listOf(
                Category(-1, "Add Category", "Adds a new category.")
            ) + allCategories,
            saveClick = { setChoosingCategories(false) },
            dismiss = { setChoosingCategories(false) },
            itemView = {
                BasicListItem(name = it.name) {
                    val mutableCategories = categories.toMutableList()

                    if (categories.contains(it)) mutableCategories.remove(it)
                    else mutableCategories.add(it)

                    itemViewModel.categories.set(mutableCategories)
                }
            }
        )

    Column(
        Modifier
            .padding(dimensionResource(id = R.dimen.space_default))
            .fillMaxWidth()
    ) {
        EditText(
            stringResource(R.string.name),
            name,
            Modifier.fullWidthWPadding
        ) {
            itemViewModel.name.set(it)
        }

        EditText(
            stringResource(R.string.description),
            description,
            Modifier.fullWidthWPadding
        ) {
            itemViewModel.description.set(it)
        }

        ScrollableRow {
            ChoiceChip(stringResource(R.string.add_category), Modifier.basePadding) {
                setChoosingCategories(true)
            }
            for (category in categories) {
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
                itemViewModel.barcode.set(it)
            }

            barcodeImage?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        Modifier
                            .basePadding
                            .weight(1f)
                            .heightIn(48.dp, 56.dp)
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
            itemViewModel.serialNumber.set(it)
        }

        EditText(
            stringResource(R.string.model_number),
            modelNumber,
            Modifier.fullWidthWPadding
        ) {
            itemViewModel.modelNumber.set(it)
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
