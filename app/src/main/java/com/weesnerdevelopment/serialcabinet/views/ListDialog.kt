package com.weesnerdevelopment.serialcabinet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.basePadding

@Composable
fun <T> ListDialog(
    items: List<T>,
    saveClick: () -> Unit,
    dismiss: () -> Unit,
    itemView: @Composable (T) -> Unit
) {
    Dialog(onDismissRequest = dismiss) {
        Surface(Modifier.wrapContentSize()) {
            Column(
                Modifier
                    .basePadding
                    .padding(
                        start = dimensionResource(R.dimen.space_default),
                        top = dimensionResource(R.dimen.space_default),
                        end = dimensionResource(R.dimen.space_default)
                    )
            ) {
                Text(
                    text = stringResource(R.string.categories),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.space_default)),
                    style = MaterialTheme.typography.h6
                )

                LazyColumnFor(items = items) { item ->
                    itemView(item)
                }

                Row(
                    Modifier
                        .align(Alignment.End)
                        .wrapContentSize()
                        .padding(top = dimensionResource(R.dimen.space_default))
                ) {
                    TextButton(onClick = saveClick) {
                        Text(stringResource(R.string.update))
                    }
                }
            }
        }
    }
}
