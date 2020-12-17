package com.weesnerdevelopment.serialcabinet.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.ripple.rememberRippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme

@Composable
fun SerialCabinetToolbar(
    icon: ImageVector? = null,
    title: String,
    up: (() -> Unit)? = null,
    editable: (() -> Unit)? = null,
    deletable: (() -> Unit)? = null
) {
    TopAppBar {
        if (up != null) {
            if (icon == null) throw IllegalArgumentException("if you want to use the up button you need an `toolbarIcon`")

            Icon(
                icon,
                Modifier.fillMaxHeight().padding(4.dp).aspectRatio(1f).clip(CircleShape).clickable(
                    onClick = up,
                    indication = rememberRippleIndication()
                )
            )
        }

        Text(
            title,
            Modifier
                .padding(if (icon != null) 0.dp else 16.dp, 0.dp)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.h6
        )

        Row(Modifier.fillMaxWidth().wrapContentWidth(Alignment.End)) {
            if (editable != null)
                Icon(
                    Icons.Outlined.Edit,
                    Modifier.fillMaxHeight().padding(4.dp).aspectRatio(1f).clip(CircleShape)
                        .clickable(
                            onClick = editable,
                            indication = rememberRippleIndication()
                        )
                )
            if (deletable != null)
                Icon(
                    Icons.Outlined.Delete,
                    Modifier.fillMaxHeight().padding(4.dp).aspectRatio(1f).clip(CircleShape)
                        .clickable(
                            onClick = deletable,
                            indication = rememberRippleIndication()
                        )
                )
        }
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewToolbar() {
    SerialCabinetTheme {
        SerialCabinetToolbar(
            icon = Icons.Outlined.ArrowBack,
            title = "Serial Cabinet",
            up = {

            },
            editable = {

            },
            deletable = {

            }
        )
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewToolbarDarkMode() {
    SerialCabinetTheme(darkTheme = true) {
        SerialCabinetToolbar(
            icon = Icons.Outlined.ArrowBack,
            title = "Serial Cabinet",
            up = {

            },
            editable = {

            },
            deletable = {

            }
        )
    }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewToolbarNoAdds() {
    SerialCabinetTheme {
        SerialCabinetToolbar(title = "Serial Cabinet")
    }
}
