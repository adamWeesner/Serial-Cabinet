package com.weesnerdevelopment.serialcabinet

import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.view.WindowCompat
import com.weesnerdevelopment.frontendutils.*
import com.weesnerdevelopment.serialcabinet.views.MainView
import com.weesnerdevelopment.serialcabinet.views.ModifySerialItem
import com.weesnerdevelopment.serialcabinet.views.SerialItemsList
import dagger.hilt.android.AndroidEntryPoint
import kimchi.Kimchi
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import shared.base.HttpStatus

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
    val (userError, setUserError) = remember { mutableStateOf(false) }

    val navigator: Navigator<Destination> = rememberSavedInstanceState(
        saver = Navigator.saver(backDispatcher)
    ) {
        Navigator(Destination.Items, backDispatcher)
    }

    val actions = remember(navigator) { Actions(navigator) }
    val (loading, setLoading) = remember { mutableStateOf(true) }
    var snackMessage: (@Composable () -> Unit)? = remember { null }

    Providers(AmbientBackDispatcher provides backDispatcher) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destination.UserDetails -> TODO()
                Destination.Login -> MainView(
                    loading = false,
                ) {
                    LoginLayout(
                        authViewModel,
                        actions.createUser,
                        actions.upPress,
                        TextInputType.Outlined
                    )
                }
                Destination.CreateUser -> MainView(
                    Icons.Outlined.ArrowBack,
                    loading = false,
                    up = actions.upPress
                ) {
                    CreateAccountLayout(
                        authViewModel,
                        actions.upPress,
                        TextInputType.Outlined
                    )
                }
                Destination.Items -> MainView(
                    loading = loading,
                    snackMessage = snackMessage,
                    fabClick = { actions.modifyItem() }) {
                    SerialItemsList(items = emptyList())
                }
                is Destination.ModifyItem -> MainView(
                    Icons.Outlined.ArrowBack,
                    loading = loading,
                    snackMessage = snackMessage,
                    up = actions.upPress
                ) {
                    ModifySerialItem(item = null)
                }
            }
        }
    }

    if (userError) {
        actions.login()
        setUserError(false)
    }

    LaunchedEffect(subject = "auth") {
        authViewModel.apply {
            currentUserFlow.collect { currentUser ->
                if (currentUser == null) {
                    Kimchi.info("No user currently logged in.")
                    setLoading(false)
                    actions.login()
                    return@collect
                }

                if (currentUser.name.isNullOrEmpty()) {
                    setLoading(false)
//                    navController.go { NavGraphDirections.actionGlobalAddUserNameDialogFragment() }
                }

                setLoading(false)
            }
        }
    }

    LaunchedEffect(subject = "auth") {
        authViewModel.getCurrentUser {
            Kimchi.warn("Failed to get current user", it)
            if (it is HttpException && it.code() == HttpStatus.Unauthorized.code)
                setUserError(true)

            if (it is ServerException)
                snackMessage = {
                    Snackbar {
                        Text(stringResource(R.string.server_down_message))
                    }
                }
        }
    }
}
