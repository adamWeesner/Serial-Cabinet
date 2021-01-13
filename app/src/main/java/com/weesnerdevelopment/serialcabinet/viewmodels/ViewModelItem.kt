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

class ViewModelItem<T>(private val defaultValue: T) : Flow<T> {
    private val channel = BroadcastChannel<T>(Channel.CONFLATED)
    var value: T = defaultValue
        private set

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) = channel.asFlow().collect(collector)

    fun set(newValue: T) {
        value = newValue
        channel.offer(newValue)
    }

    fun reset() {
        value = defaultValue
        channel.offer(defaultValue)
    }

    @Composable
    fun collectAsState(): State<T> = collectAsState(initial = defaultValue)
}
