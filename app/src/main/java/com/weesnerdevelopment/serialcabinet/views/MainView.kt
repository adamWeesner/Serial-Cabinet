package com.weesnerdevelopment.serialcabinet.views

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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
    snackMessage: (@Composable () -> Unit)? = null,
    loading: Boolean = true,
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
                if (fabClick != null && !loading)
                    FloatingActionButton(onClick = fabClick) {
                        Icon(Icons.Outlined.Add, tint = MaterialTheme.colors.onSecondary)
                    }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            Box {
                content()
                if (loading) {
                    Surface(
                        Modifier.fillMaxSize().clickable(onClick = { }, indication = null),
                        color = Color(0x4F000000),
                        contentColor = Color(0x4F000000)
                    ) {}
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                if (snackMessage != null) snackMessage()
            }
        }
    }
}
