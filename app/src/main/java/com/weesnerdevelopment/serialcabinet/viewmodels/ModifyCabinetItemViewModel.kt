package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Category
import shared.serialCabinet.Electronic

class ModifyCabinetItemViewModel @ViewModelInject constructor() : ViewModel() {
    val name = ViewModelItem("")
    val description = ViewModelItem("")
    val categories = ViewModelItem<List<Category>>(listOf())

    val barcode = ViewModelItem("")
    val barcodeImage = ViewModelItem<ByteArray?>(null)
    val serialNumber = ViewModelItem("")
    val modelNumber = ViewModelItem("")

    fun currentItem(item: CabinetItem) {
        name.setValue(item.name)
        description.setValue(item.description)
        categories.setValue(item.categories)

        if (item is Electronic) {
            barcode.setValue(item.barcode ?: "")
            barcodeImage.setValue(item.barcodeImage)
            serialNumber.setValue(item.serialNumber ?: "")
            modelNumber.setValue(item.modelNumber ?: "")
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
