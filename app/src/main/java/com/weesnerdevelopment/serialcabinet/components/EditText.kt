package com.weesnerdevelopment.serialcabinet.components

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme

@Composable
fun EditText(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
    valueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        label = { Text(text = label) },
        modifier = modifier,
        value = value ?: "",
        onValueChange = {
            valueChange(it)
        }
    )
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItem() {
    SerialCabinetTheme {
        EditText(
            label = "Name",
            value = "Adam"
        )
    }
}
