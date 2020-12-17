package com.weesnerdevelopment.serialcabinet.views

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.components.SerialCabinetToolbar
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme

@Composable
fun MainView(
    toolbarIcon: ImageVector? = null,
    @StringRes title: Int = R.string.app_name,
    up: (() -> Unit)? = null,
    editable: (() -> Unit)? = null,
    deletable: (() -> Unit)? = null,
    fabClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    SerialCabinetTheme {
        Scaffold(

            topBar = {
                SerialCabinetToolbar(
                    icon = toolbarIcon,
                    title = stringResource(id = title),
                    up = up,
                    editable = editable,
                    deletable = deletable
                )
            },
            floatingActionButton = {
                if (fabClick != null)
                    FloatingActionButton(onClick = fabClick) {
                        Icon(Icons.Outlined.Add, tint = MaterialTheme.colors.onSecondary)
                    }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun previewMainView() {
    SerialCabinetTheme {
        MainView(fabClick = {}) {

        }
    }
}
