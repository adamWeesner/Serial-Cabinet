package com.weesnerdevelopment.serialcabinet.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme

@Composable
fun EditText(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
    helperText: String? = null,
    isError: Boolean = false,
    valueChange: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            label = { Text(text = label) },
            isErrorValue = isError,
            value = value ?: "",
            onValueChange = {
                valueChange(it)
            }
        )
        if (helperText != null)
            Text(
                color = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.caption,
                text = "    $helperText"
            )

    }
}

@Composable
fun EditText(
    @StringRes label: Int,
    value: String?,
    modifier: Modifier = Modifier,
    @StringRes helperText: Int? = null,
    isError: Boolean = false,
    valueChange: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(label)) },
            isErrorValue = isError,
            value = value ?: "",
            onValueChange = valueChange
        )
        if (helperText != null)
            Text(
                color = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.caption,
                text = "    ${stringResource(helperText, stringResource(label))}"
            )
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItem() {
    SerialCabinetTheme {
        EditText(
            label = "Name",
            value = "Adam",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItemHelper() {
    SerialCabinetTheme {
        EditText(
            label = "Name",
            value = null,
            helperText = "Helper text goes here",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItemHelperInt() {
    SerialCabinetTheme {
        EditText(
            label = R.string.name,
            value = null,
            isError = true,
            helperText = R.string.blank_message,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItemError() {
    SerialCabinetTheme {
        EditText(
            label = "Name",
            value = "Adam",
            isError = true,
            modifier = Modifier.padding(8.dp)
        )
    }
}
