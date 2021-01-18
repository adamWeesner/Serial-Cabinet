package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.lifecycle.ViewModel
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Category
import shared.serialCabinet.Electronic

class ModifyCabinetItemViewModel : ViewModel() {
    val name = ViewModelItem("")
    val nameError = ViewModelItem(false)

    val description = ViewModelItem("")
    val descriptionError = ViewModelItem(false)

    val categories = ViewModelItem(emptyList<Category>())
    val categoriesError = ViewModelItem(false)

    val barcode = ViewModelItem("")
    val barcodeError = ViewModelItem(false)

    val barcodeImage = ViewModelItem<ByteArray?>(null)

    val serialNumber = ViewModelItem("")
    val serialNumberError = ViewModelItem(false)

    val modelNumber = ViewModelItem("")
    val modelNumberError = ViewModelItem(false)

    fun currentItem(item: CabinetItem) {
        name.set(item.name)
        description.set(item.description)
        categories.set(item.categories)

        if (item is Electronic) {
            barcode.set(item.barcode ?: "")
            barcodeImage.set(item.barcodeImage)
            serialNumber.set(item.serialNumber ?: "")
            modelNumber.set(item.modelNumber ?: "")
        }
    }

    fun checkForErrors(): Boolean {
        var hasError = false

        name.checkError(nameError).also { if (!hasError) hasError = it }
        description.checkError(descriptionError).also { if (!hasError) hasError = it }
        categories.checkError(categoriesError).also { if (!hasError) hasError = it }
        barcode.checkError(barcodeError).also { if (!hasError) hasError = it }
        serialNumber.checkError(serialNumberError).also { if (!hasError) hasError = it }
        modelNumber.checkError(modelNumberError).also { if (!hasError) hasError = it }

        return hasError
    }

    fun clear() {
        name.reset()
        description.reset()
        categories.reset()
        barcode.reset()
        barcodeImage.reset()
        serialNumber.reset()
        modelNumber.reset()
    }
}
