package com.weesnerdevelopment.serialcabinet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.weesnerdevelopment.serialcabinet.R
import shared.serialCabinet.CabinetItem

@Composable
fun SerialItem(item: CabinetItem) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.space_default)).fillMaxWidth()
    ) {
        val categoryText = when (item.categories.size) {
            0 -> ""
            1 -> stringResource(R.string.single_category, item.categories.first().name)
            2 -> stringResource(R.string.two_categories, item.categories.first().name)
            else -> stringResource(
                R.string.many_categories,
                item.categories.first().name,
                item.categories.lastIndex
            )
        }

        Text(text = item.name, fontWeight = FontWeight.Bold)
        Text(text = categoryText, style = MaterialTheme.typography.subtitle2)
    }
}
