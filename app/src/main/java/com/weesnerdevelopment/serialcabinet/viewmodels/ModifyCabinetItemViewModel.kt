package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.lifecycle.ViewModel
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Category
import shared.serialCabinet.Electronic

class ModifyCabinetItemViewModel : ViewModel() {
    val name = ViewModelItem("")
    val description = ViewModelItem("")
    val categories = ViewModelItem<List<Category>>(listOf())

    val barcode = ViewModelItem("")
    val barcodeImage = ViewModelItem<ByteArray?>(null)
    val serialNumber = ViewModelItem("")
    val modelNumber = ViewModelItem("")

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
