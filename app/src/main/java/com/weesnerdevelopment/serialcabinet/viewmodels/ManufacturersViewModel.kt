package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weesnerdevelopment.frontendutils.request
import com.weesnerdevelopment.serialcabinet.middleware.ManufacturersMiddleware
import kimchi.Kimchi
import kotlinx.coroutines.launch
import shared.serialCabinet.Manufacturer

class ManufacturersViewModel @ViewModelInject constructor(
    private val ManufacturerMiddleware: ManufacturersMiddleware
) : ViewModel() {
    val allManufacturers = ViewModelItem<List<Manufacturer>>(emptyList())
    val failedManufacturer = ViewModelItem("")

    fun getManufacturers() {
        viewModelScope.launch {
            request(
                { ManufacturerMiddleware.getAll() },
                {
                    Kimchi.debug("successfully got Manufacturers $it")
                    allManufacturers.set(it ?: emptyList())
                },
                {
                    Kimchi.debug("failed to get Manufacturers $it")
                    allManufacturers.set(emptyList())
                }
            )
        }
    }

    fun addManufacturer(Manufacturer: Manufacturer) {
        viewModelScope.launch {
            request(
                { ManufacturerMiddleware.add(Manufacturer) },
                {
                    Kimchi.debug("successfully added Manufacturer $it")
                    getManufacturers()
                },
                {
                    Kimchi.debug("failed to add Manufacturer $it")
                    failedManufacturer.set("Failed to add Manufacturer, try again.")
                }
            )
        }
    }
}
