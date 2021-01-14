package com.weesnerdevelopment.serialcabinet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.basePadding
import com.weesnerdevelopment.serialcabinet.components.EditText
import com.weesnerdevelopment.serialcabinet.components.TextButton
import com.weesnerdevelopment.serialcabinet.viewmodels.CategoriesViewModel
import shared.serialCabinet.Category

@Composable
fun AddCategoryDialog(
    categoriesViewModel: CategoriesViewModel,
    dismiss: () -> Unit
) {
    val (name, setName) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

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
                    text = stringResource(R.string.add_category),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.space_default)),
                    style = MaterialTheme.typography.h6
                )

                EditText(
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.space_default)),
                    label = stringResource(R.string.name),
                    value = name,
                    valueChange = { setName(it) }
                )

                EditText(
                    label = stringResource(R.string.description),
                    value = description,
                    valueChange = { setDescription(it) }
                )

                Row(
                    Modifier
                        .align(Alignment.End)
                        .wrapContentSize()
                        .padding(top = dimensionResource(R.dimen.space_default))
                ) {
                    TextButton(R.string.save) {
                        categoriesViewModel.addCategory(
                            Category(name = name, description = description)
                        )
                        dismiss()
                    }
                }
            }
        }
    }
}
