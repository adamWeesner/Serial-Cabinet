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
import com.weesnerdevelopment.frontendutils.AuthViewModel
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.basePadding
import com.weesnerdevelopment.serialcabinet.components.*
import com.weesnerdevelopment.serialcabinet.fullWidthWPadding
import com.weesnerdevelopment.serialcabinet.smallHorizontal
import com.weesnerdevelopment.serialcabinet.viewmodels.CategoriesViewModel
import com.weesnerdevelopment.serialcabinet.viewmodels.ElectronicsViewModel
import com.weesnerdevelopment.serialcabinet.viewmodels.ManufacturersViewModel
import com.weesnerdevelopment.serialcabinet.viewmodels.ModifyCabinetItemViewModel
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Category
import shared.serialCabinet.Electronic
import java.util.*

@Composable
fun ModifySerialItem(
    authViewModel: AuthViewModel,
    itemViewModel: ModifyCabinetItemViewModel,
    categoriesViewModel: CategoriesViewModel,
    electronicsViewModel: ElectronicsViewModel,
    manufacturersViewModel: ManufacturersViewModel,
    item: CabinetItem? = null,
    back: () -> Unit,
    barcodeCamera: () -> Unit,
) {
    val context = AmbientContext.current
    val (choosingCategories, setChoosingCategories) = remember { mutableStateOf(false) }
    val (addCategory, setAddCategory) = remember { mutableStateOf(false) }

    remember { itemViewModel.currentItem(item) }

    val name by itemViewModel.name.collectAsState()
    val nameError by itemViewModel.nameError.collectAsState()

    val description by itemViewModel.description.collectAsState()
    val descriptionError by itemViewModel.descriptionError.collectAsState()

    val categories by itemViewModel.categories.collectAsState()
    val categoriesError by itemViewModel.categoriesError.collectAsState()

    val barcode by itemViewModel.barcode.collectAsState()
    val barcodeError by itemViewModel.barcodeError.collectAsState()

    val barcodeImage by itemViewModel.barcodeImage.collectAsState()

    val serialNumber by itemViewModel.serialNumber.collectAsState()
    val serialNumberError by itemViewModel.serialNumberError.collectAsState()

    val modelNumber by itemViewModel.modelNumber.collectAsState()
    val modelNumberError by itemViewModel.modelNumberError.collectAsState()

    val allCategories by categoriesViewModel.allCategories.collectAsState()
    val allManufacturers by manufacturersViewModel.allManufacturers.collectAsState()

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
            label = R.string.name,
            value = name,
            modifier = Modifier.fullWidthWPadding,
            helperText = if (nameError) R.string.blank_message else null,
            isError = nameError
        ) {
            itemViewModel.name.set(it, itemViewModel.nameError)
        }

        EditText(
            label = R.string.description,
            value = description,
            modifier = Modifier.fullWidthWPadding,
            helperText = if (descriptionError) R.string.blank_message else null,
            isError = descriptionError
        ) {
            itemViewModel.description.set(it, itemViewModel.descriptionError)
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
                label = R.string.barcode,
                value = barcode,
                modifier = Modifier.basePadding.weight(1.25f),
                helperText = if (barcodeError) R.string.blank_message else null,
                isError = barcodeError
            ) {
                itemViewModel.barcode.set(it, itemViewModel.barcodeError)
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
            label = R.string.serial_number,
            value = serialNumber,
            modifier = Modifier.fullWidthWPadding,
            helperText = if (serialNumberError) R.string.blank_message else null,
            isError = serialNumberError
        ) {
            itemViewModel.serialNumber.set(it, itemViewModel.serialNumberError)
        }

        EditText(
            label = R.string.model_number,
            value = modelNumber,
            modifier = Modifier.fullWidthWPadding,
            helperText = if (modelNumberError) R.string.blank_message else null,
            isError = modelNumberError
        ) {
            itemViewModel.modelNumber.set(it, itemViewModel.modelNumberError)
        }

        Box(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomEnd)
                    .basePadding
            ) {
                if (item != null) {
                    Button(
                        text = R.string.delete,
                    ) {
                        if (item is Electronic) {
                            electronicsViewModel.deleteElectronic(item)
                            back()
                        }
                    }
                }
                Button(
                    enabled = listOf(
                        nameError,
                        descriptionError,
                        barcodeError,
                        serialNumberError,
                        modelNumberError
                    ).all { !it },
                    text = if (item != null) R.string.update else R.string.save,
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.space_default))
                ) {
                    if (itemViewModel.checkForErrors()) return@Button

                    electronicsViewModel.addElectronic(
                        Electronic(
                            owner = authViewModel.currentUser
                                ?: throw IllegalArgumentException("No user found."),
                            name = name,
                            description = description,
                            categories = categories,
                            image = null,
                            barcode = barcode,
                            barcodeImage = barcodeImage,
                            serialNumber = serialNumber,
                            modelNumber = modelNumber,
                            manufacturer = allManufacturers.first(),
                            manufactureDate = Date().time,
                            purchaseDate = Date().time
                        )
                    )
                    back()
                }
            }
        }
    }
}
