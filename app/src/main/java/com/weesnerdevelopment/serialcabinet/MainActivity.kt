package com.weesnerdevelopment.serialcabinet

import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.view.WindowCompat
import com.weesnerdevelopment.frontendutils.AuthViewModel
import com.weesnerdevelopment.frontendutils.LoginLayout
import com.weesnerdevelopment.frontendutils.Navigator
import com.weesnerdevelopment.serialcabinet.views.MainView
import com.weesnerdevelopment.serialcabinet.views.ModifySerialItem
import com.weesnerdevelopment.serialcabinet.views.SerialItemsList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val authViewModel = viewModel<AuthViewModel>()

            SerialCabinetApp(
                onBackPressedDispatcher,
                authViewModel
            )
        }
    }
}

@Composable
fun SerialCabinetApp(
    backDispatcher: OnBackPressedDispatcher,
    authViewModel: AuthViewModel
) {
    //val (userError, setUserError) = remember { mutableStateOf(false) }

    val navigator: Navigator<Destination> = rememberSavedInstanceState(
        saver = Navigator.saver(backDispatcher)
    ) {
        Navigator(Destination.Items, backDispatcher)
    }

    val actions = remember(navigator) { Actions(navigator) }

    Providers(AmbientBackDispatcher provides backDispatcher) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destination.UserDetails -> TODO()
                Destination.Login -> LoginLayout(
                    authViewModel,
                    actions.createUser,
                    actions.createUser
                )
                Destination.CreateUser -> TODO()
                Destination.Items -> MainView(
                    fabClick = { actions.modifyItem() }) {
                    SerialItemsList(items = emptyList())
                }
                is Destination.ModifyItem -> MainView(
                    Icons.Outlined.ArrowBack,
                    up = actions.upPress
                ) {
                    ModifySerialItem(item = null)
                }
            }
        }
    }

    /*
    if (userError) {
        actions.login()
        setUserError(false)
    }
    */

    /*
    LaunchedEffect(subject = "auth") {
        authViewModel.apply {
            currentUserFlow.collect { currentUser ->
                if (currentUser == null) {
                    Kimchi.info("No user currently logged in.")
                    progress_loading.isVisible = false
                    actions.login()
                    return@collect
                }

                if (currentUser.name.isNullOrEmpty()) {
                    progress_loading.isVisible = false
                    navController.go { NavGraphDirections.actionGlobalAddUserNameDialogFragment() }
                }

                progress_loading.isVisible = false
            }
        }
    }
    */

    /*
    LaunchedEffect(subject = "auth") {
        authViewModel.getCurrentUser {
            Kimchi.warn("Failed to get current user", it)
            if (it is HttpException && it.code() == HttpStatus.Unauthorized.code)
                setUserError(true)

           if (it is ServerException)
               layout_bills.snackbar(getString(R.string.server_down_message))
        }
    }
    */
}
