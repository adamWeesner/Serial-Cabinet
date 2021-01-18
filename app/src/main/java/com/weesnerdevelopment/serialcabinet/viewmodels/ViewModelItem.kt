package com.weesnerdevelopment.serialcabinet.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow

class ViewModelItem<T>(val defaultValue: T) : Flow<T> {
    private val channel = BroadcastChannel<T>(Channel.CONFLATED)
    var value: T = defaultValue
        private set

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) = channel.asFlow().collect(collector)

    /**
     * Updates the [value] of the item and also offers the [newValue] to the backing channel.
     */
    fun set(newValue: T) {
        value = newValue
        channel.offer(newValue)
    }

    /**
     * [set]s the new value and updates the [errorState]s value accordingly.
     */
    fun set(newValue: T, errorState: ViewModelItem<Boolean>) {
        set(newValue)
        checkError(errorState)
    }

    /**
     * Checks and sets whether the item is in an error state.
     */
    fun checkError(errorState: ViewModelItem<Boolean>) =
        (this.value == this.defaultValue).also {
            errorState.set(it)
        }

    /**
     * Resets the [value] back to its default, and updates the backing channel.
     */
    fun reset() {
        value = defaultValue
        channel.offer(defaultValue)
    }

    @Composable
    fun collectAsState(): State<T> = collectAsState(initial = defaultValue)
}
