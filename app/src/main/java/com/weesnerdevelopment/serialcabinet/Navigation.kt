package com.weesnerdevelopment.serialcabinet

import android.os.Parcelable
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.staticAmbientOf
import com.weesnerdevelopment.frontendutils.Navigator
import kotlinx.android.parcel.Parcelize

sealed class Destination : Parcelable {
    // user
    @Parcelize
    object UserDetails : Destination()

    @Parcelize
    object Login : Destination()

    @Parcelize
    object CreateUser : Destination()

    // Cabinet Items
    @Parcelize
    object Items : Destination()

    @Parcelize
    class ModifyItem(val id: Int? = null) : Destination()
}

class Actions(private val navigator: Navigator<Destination>) {
    // user
    val userDetails = { navigator.navigate(Destination.UserDetails) }
    val login = {
        navigator.back()
        navigator.navigate(Destination.Login)
    }
    val createUser = { navigator.navigate(Destination.CreateUser) }

    val items = { navigator.navigate(Destination.Items) }
    fun modifyItem(id: Int? = null) = navigator.navigate(Destination.ModifyItem(id))

    val upPress = { navigator.back() }
}

internal val AmbientBackDispatcher = staticAmbientOf<OnBackPressedDispatcher> {
    error("No back dispatcher provided")
}