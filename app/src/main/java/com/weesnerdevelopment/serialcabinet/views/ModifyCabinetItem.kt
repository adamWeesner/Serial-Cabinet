package com.weesnerdevelopment.serialcabinet.views

import android.app.Activity
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.basePadding
import com.weesnerdevelopment.serialcabinet.components.*
import com.weesnerdevelopment.serialcabinet.fullWidthWPadding
import com.weesnerdevelopment.serialcabinet.smallHorizontal
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
    val (addCategory, setAddCategory) = remember { mutableStateOf(false) }

    if (item != null) itemViewModel.currentItem(item)

    val name by itemViewModel.name.collectAsState()
    val description by itemViewModel.description.collectAsState()
    val categories by itemViewModel.categories.collectAsState()
    val barcode by itemViewModel.barcode.collectAsState()
    val barcodeImage by itemViewModel.barcodeImage.collectAsState()
    val serialNumber by itemViewModel.serialNumber.collectAsState()
    val modelNumber by itemViewModel.modelNumber.collectAsState()

    val allCategories by categoriesViewModel.allCategories.collectAsState()

    remember { categoriesViewModel.getCategories() }

    if (choosingCategories)
        ListDialog(
            items = listOf(
                Category(-1, stringResource(R.string.add_category), "Adds a new category.")
            ) + allCategories,
            saveClick = { setChoosingCategories(false) },
            dismiss = { setChoosingCategories(false) },
            itemView = {
                BasicListItem(name = it.name, categories.contains(it)) {
                    if (it.id == -1) {
                        setAddCategory(true)
                        return@BasicListItem
                    }

                    val mutableCategories = categories.toMutableList()

                    if (categories.contains(it)) mutableCategories.remove(it)
                    else mutableCategories.add(it)

                    itemViewModel.categories.set(mutableCategories)
                }
            }
        )

    if (addCategory)
        AddCategoryDialog(categoriesViewModel) { setAddCategory(false) }

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
                DeletableChip(category.name, Modifier.smallHorizontal) {
                    val mutableCategories = categories.toMutableList()
                    mutableCategories.remove(category)
                    itemViewModel.categories.set(mutableCategories)
                }
            }
        }

        Row {
            EditText(
                stringResource(R.string.barcode),
                barcode,
                Modifier.basePadding.weight(1.25f)
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
                text = R.string.barcode_image,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = dimensionResource(R.dimen.space_default))
                    .weight(1f),
            ) {
                val cameraPerm = arrayOf(android.Manifest.permission.CAMERA)
                if (!hasPermissions(context, cameraPerm))
                    requestPermissions(context as Activity, cameraPerm, 999)
                else
                    barcodeCamera()
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
                text = if (item != null) R.string.update else R.string.save,
                modifier = Modifier.align(Alignment.BottomEnd).basePadding
            ) {

            }
        }
    }
}
