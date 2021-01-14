package com.weesnerdevelopment.serialcabinet.components

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Button(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    click: () -> Unit,
) {
    androidx.compose.material.Button(
        onClick = click,
        modifier = modifier
    ) {
        Text(
            text = stringResource(text),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TextButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    click: () -> Unit
) {
    androidx.compose.material.TextButton(
        modifier = modifier,
        onClick = click
    ) {
        Text(stringResource(text))
    }
}
