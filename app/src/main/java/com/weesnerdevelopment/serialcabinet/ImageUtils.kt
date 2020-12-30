package com.weesnerdevelopment.serialcabinet

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.weesnerdevelopment.serialcabinet.viewmodels.ModifyCabinetItemViewModel
import kimchi.Kimchi
import java.io.ByteArrayOutputStream
import kotlin.math.max
import kotlin.math.roundToInt

fun captureImage(
    imageCapture: ImageCapture,
    viewModel: ModifyCabinetItemViewModel,
    context: Context,
    done: () -> Unit
) {
    imageCapture.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                findBarcode(image, viewModel)
                done()
            }

            override fun onError(exception: ImageCaptureException) {
                Kimchi.error("Error occurred taking a picture", exception)
            }
        }
    )
}

private fun findBarcode(imageProxy: ImageProxy, viewModel: ModifyCabinetItemViewModel) {
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    imageProxy.image?.let {
        val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)

        val scanner = BarcodeScanning.getClient(options)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isEmpty()) {
                    imageProxy.close()
                    return@addOnSuccessListener
                }

                barcodes.first()?.also { barcode ->
                    viewModel.barcode.setValue(barcode.displayValue ?: barcode.rawValue)
                    val imageBytes = imageProxy.adjustImage(barcode)
                    viewModel.barcodeImage.setValue(imageBytes)
                }
                imageProxy.close()
            }
            .addOnFailureListener {
                Kimchi.error("Error occurred parsing barcode", it)
                imageProxy.close()
            }
    }
}

fun Bitmap.rotate(angle: Double = 90.0): Bitmap? {
    val matrix = Matrix().apply { postRotate(angle.toFloat()) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

private fun ImageProxy.adjustImage(barcode: Barcode): ByteArray {
    val topLeft = barcode.cornerPoints?.get(0) ?: Point(0, 0)
    val topRight = barcode.cornerPoints?.get(1) ?: Point(0, 0)
    val bottomRight = barcode.cornerPoints?.get(2)
    val bottomLeft = barcode.cornerPoints?.get(3)

    val width = longestWidth(topLeft, topRight, bottomLeft, bottomRight)
    val height = longestHeight(topLeft, topRight, bottomLeft, bottomRight)

    val verticalPadding = max(width * .1, 16.0).roundToInt()
    val horizontalPadding = max(height * .1, 16.0).roundToInt()
    val start = Point(topLeft.x, topLeft.y)
    start.offset(-horizontalPadding, -verticalPadding)

    val bitmap = toBitmap()?.rotate()
    val resized = bitmap?.let { bitmap ->
        Bitmap.createBitmap(
            bitmap,
            start.x,
            start.y,
            width + (horizontalPadding * 2),
            height + (verticalPadding * 2)
        )
    }

    val baos = ByteArrayOutputStream()
    resized?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}

private fun longestHeight(
    topLeft: Point?,
    topRight: Point?,
    bottomLeft: Point?,
    bottomRight: Point?
): Int {
    val leftHeight = (bottomLeft?.y ?: 0) - (topLeft?.y ?: 0)
    val rightHeight = (bottomRight?.y ?: 0) - (topRight?.y ?: 0)
    val topToBottomHeight = (bottomRight?.y ?: 0) - (topLeft?.y ?: 0)
    val bottomToTopHeight = (topRight?.y ?: 0) - (bottomLeft?.y ?: 0)

    var longestHeight = leftHeight
    if (rightHeight > longestHeight) longestHeight = rightHeight
    if (topToBottomHeight > longestHeight) longestHeight = topToBottomHeight
    if (bottomToTopHeight > longestHeight) longestHeight = bottomToTopHeight

    return longestHeight
}

private fun longestWidth(
    topLeft: Point?,
    topRight: Point?,
    bottomLeft: Point?,
    bottomRight: Point?
): Int {
    val topWidth = (topRight?.x ?: 0) - (topLeft?.x ?: 0)
    val bottomWidth = (bottomRight?.x ?: 0) - (bottomLeft?.x ?: 0)
    val topToBottomWidth = (bottomRight?.x ?: 0) - (topLeft?.x ?: 0)
    val bottomToTopWidth = (topRight?.x ?: 0) - (bottomLeft?.x ?: 0)

    var longestWidth = topWidth
    if (bottomWidth > longestWidth) longestWidth = bottomWidth
    if (topToBottomWidth > longestWidth) longestWidth = topToBottomWidth
    if (bottomToTopWidth > longestWidth) longestWidth = bottomToTopWidth

    return longestWidth
}

private fun ImageProxy.toBitmap(): Bitmap? {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer[bytes]
    val clonedBytes = bytes.clone()
    return BitmapFactory.decodeByteArray(clonedBytes, 0, clonedBytes.size)
}
