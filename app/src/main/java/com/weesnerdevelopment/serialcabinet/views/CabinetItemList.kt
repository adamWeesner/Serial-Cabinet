package com.weesnerdevelopment.serialcabinet.views

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.viewmodels.ElectronicsViewModel

@Composable
fun SerialItemsList(
    electronicsViewModel: ElectronicsViewModel,
    itemClick: (id: Int?) -> Unit
) {
    val items by electronicsViewModel.allElectronics.collectAsState()

    if (items.isEmpty())
        Text(
            stringResource(R.string.empty_cabinet_message),
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    else
        ScrollableColumn {
            for (item in items)
                SerialItem(item = item, click = itemClick)
        }
}
