package com.weesnerdevelopment.serialcabinet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource

@Composable
val Modifier.basePadding
    get() = padding(dimensionResource(R.dimen.space_default))

@Composable
val Modifier.smallHorizontal
    get() = padding(dimensionResource(R.dimen.space_half), dimensionResource(R.dimen.space_default))

@Composable
val Modifier.fullWidthWPadding
    get() = fillMaxWidth().basePadding
