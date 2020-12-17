package com.weesnerdevelopment.serialcabinet.components

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme

@Composable
fun EditText(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
    valueChange: (String) -> Unit = {}
) {
    val (currentValue, setCurrentValue) = remember {
        mutableStateOf(TextFieldValue(value ?: ""))
    }

    OutlinedTextField(
        label = { Text(text = label) },
        modifier = modifier,
        value = currentValue,
        onValueChange = {
            setCurrentValue(it)
            valueChange(it.text)
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
