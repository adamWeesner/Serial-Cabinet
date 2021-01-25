package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weesnerdevelopment.frontendutils.request
import com.weesnerdevelopment.serialcabinet.middleware.ElectronicsMiddleware
import kimchi.Kimchi
import kotlinx.coroutines.launch
import shared.serialCabinet.Electronic

class ElectronicsViewModel @ViewModelInject constructor(
    private val electronicsMiddleware: ElectronicsMiddleware
) : ViewModel() {
    val allElectronics = ViewModelItem<List<Electronic>>(emptyList())
    val failedElectronic = ViewModelItem("")

    fun getElectronics() {
        viewModelScope.launch {
            request(
                { electronicsMiddleware.getAll() },
                {
                    Kimchi.debug("successfully got electronics $it")
                    allElectronics.set(it ?: emptyList())
                },
                {
                    Kimchi.debug("failed to get electronics $it")
                    allElectronics.set(emptyList())
                }
            )
        }
    }

    fun addElectronic(electronic: Electronic) {
        viewModelScope.launch {
            request(
                { electronicsMiddleware.add(electronic) },
                {
                    Kimchi.debug("successfully added Electronic $it")
                    getElectronics()
                },
                {
                    Kimchi.debug("failed to add Electronic $it")
                    failedElectronic.set("Failed to add Electronic, try again.")
                }
            )
        }
    }

    fun deleteElectronic(electronic: Electronic) {
        viewModelScope.launch {
            request(
                { electronicsMiddleware.remove(electronic.id.toString()) },
                {
                    Kimchi.debug("successfully deleted Electronic $it")
                    getElectronics()
                },
                {
                    Kimchi.debug("failed to delete Electronic $it")
                    failedElectronic.set("Failed to delete Electronic, try again.")
                }
            )
        }
    }
}
