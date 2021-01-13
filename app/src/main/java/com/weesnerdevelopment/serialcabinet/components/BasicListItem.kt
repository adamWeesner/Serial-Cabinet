package com.weesnerdevelopment.serialcabinet.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.weesnerdevelopment.serialcabinet.R

@Composable
fun BasicListItem(name: String, click: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = click)
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(dimensionResource(R.dimen.space_double))
        )
    }
}
