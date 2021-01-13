package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weesnerdevelopment.frontendutils.request
import com.weesnerdevelopment.serialcabinet.middleware.CategoryMiddleware
import kimchi.Kimchi
import kotlinx.coroutines.launch
import shared.serialCabinet.Category

class CategoriesViewModel @ViewModelInject constructor(
    private val categoryMiddleware: CategoryMiddleware
) : ViewModel() {
    val allCategories = ViewModelItem<List<Category>>(emptyList())
    val failedCategory = ViewModelItem("")

    fun getCategories() {
        viewModelScope.launch {
            request(
                { categoryMiddleware.getAll() },
                {
                    Kimchi.debug("successfully got categories $it")
                    allCategories.set(it ?: emptyList())
                },
                {
                    Kimchi.debug("failed to get categories $it")
                    allCategories.set(emptyList())
                }
            )
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            request(
                { categoryMiddleware.add(category) },
                {
                    Kimchi.debug("successfully added category $it")
                    getCategories()
                },
                {
                    Kimchi.debug("failed to add category $it")
                    failedCategory.set("Failed to add category, try again.")
                }
            )
        }
    }
}
