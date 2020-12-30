package com.weesnerdevelopment.serialcabinet.views

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.AmbientLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.weesnerdevelopment.serialcabinet.R
import com.weesnerdevelopment.serialcabinet.captureImage
import com.weesnerdevelopment.serialcabinet.viewmodels.ModifyCabinetItemViewModel
import kimchi.Kimchi


@Composable
fun CameraPreviewLayout(
    viewModel: ModifyCabinetItemViewModel,
    done: () -> Unit
) {
    val size = Size(480, 640)
    val lifecycleOwner = AmbientLifecycleOwner.current
    val context = AmbientContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val imageCapture = ImageCapture
        .Builder()
        .setTargetResolution(size)
        .build()

    Box {
        AndroidView(viewBlock = { previewView }, Modifier.fillMaxSize()) {
            cameraProviderFuture.addListener({
                val provider = cameraProviderFuture.get()
                val preview = Preview
                    .Builder()
                    .setTargetResolution(size)
                    .build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                preview.setSurfaceProvider(it.surfaceProvider)
                try {
                    provider.unbindAll()
                    provider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )
                } catch (exc: Exception) {
                    Kimchi.error("Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(context))
        }

        Button(
            onClick = { captureImage(imageCapture, viewModel, context, done) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(R.dimen.space_default))
        ) {
            Text(stringResource(R.string.take_picture))
        }
    }
}
