package com.weesnerdevelopment.serialcabinet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weesnerdevelopment.serialcabinet.ui.SerialCabinetTheme
import shared.auth.User
import shared.serialCabinet.CabinetItem
import shared.serialCabinet.Category
import shared.serialCabinet.Electronic

@Composable
fun SerialItem(item: CabinetItem) {
    Column(
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    ) {
        val categoryText = remember {
            item.categories.first().name + if (item.categories.size > 1) " + ${item.categories.size - 1} other(s)" else ""
        }

        Text(text = item.name, fontWeight = FontWeight.Bold)
        Text(text = categoryText, style = MaterialTheme.typography.subtitle2)
    }
}

val mockCabinetItem = object : CabinetItem {
    override val name: String = "Chromecast with Google TV"
    override val description: String = "Snow colored chromecast with google tv"
    override val categories: List<Category> = listOf(
        Category(name = "Random", description = "category description"),
        Category(name = "Random2", description = "category description"),
        Category(name = "Random3", description = "category description"),
    )
    override val image: ByteArray? = null
}

val mockElectronicCabinetItem = Electronic(
    name = "Chromecast with Google TV",
    description = "Snow colored chromecast with google tv",
    image = "randombytearray".toByteArray(),
    modelNumber = "1234",
    serialNumber = "1234567890",
    categories = listOf(
        Category(name = "Random", description = "category description"),
        Category(name = "Random2", description = "category description"),
        Category(name = "Random3", description = "category description"),
    ),
    barcode = "12345768905673",
    barcodeImage = "randombytearray".toByteArray(),
    manufactureDate = null,
    manufacturer = null,
    purchaseDate = null,
    owner = User(name = "Adam", email = "test@test.com")
)

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun previewSerialItem() {
    SerialCabinetTheme {
        SerialItem(item = mockCabinetItem)
    }
}
