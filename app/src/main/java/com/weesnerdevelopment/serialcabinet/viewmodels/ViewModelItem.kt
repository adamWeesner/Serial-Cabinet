package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModelItem<T>(private val defaultValue: T) {
    private val mutableValue = MutableLiveData(defaultValue)

    val value: LiveData<T>
        get() = mutableValue

    fun setValue(newValue: T) {
        mutableValue.value = newValue
    }

    fun reset() {
        mutableValue.value = defaultValue
    }
}
