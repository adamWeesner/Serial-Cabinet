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
import com.weesnerdevelopment.serialcabinet.viewmodels.CategoriesViewModel
import com.weesnerdevelopment.serialcabinet.viewmodels.ElectronicsViewModel
import com.weesnerdevelopment.serialcabinet.viewmodels.ManufacturersViewModel
import com.weesnerdevelopment.serialcabinet.viewmodels.ModifyCabinetItemViewModel
import com.weesnerdevelopment.serialcabinet.views.*
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
            val modifiedItemViewModel = viewModel<ModifyCabinetItemViewModel>()
            val categoriesViewModel = viewModel<CategoriesViewModel>()
            val electronicsViewModel = viewModel<ElectronicsViewModel>()
            val manufacturersViewModel = viewModel<ManufacturersViewModel>()

            SerialCabinetApp(
                onBackPressedDispatcher,
                authViewModel,
                modifiedItemViewModel,
                categoriesViewModel,
                electronicsViewModel,
                manufacturersViewModel
            )
        }
    }
}

@Composable
fun SerialCabinetApp(
    backDispatcher: OnBackPressedDispatcher,
    authViewModel: AuthViewModel,
    modifyCabinetItemViewModel: ModifyCabinetItemViewModel,
    categoriesViewModel: CategoriesViewModel,
    electronicsViewModel: ElectronicsViewModel,
    manufacturersViewModel: ManufacturersViewModel
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
                        actions.items,
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
                    fabClick = { actions.modifyItem() }
                ) {
                    SerialItemsList(electronicsViewModel) {
                        actions.modifyItem(it)
                    }
                }
                is Destination.ModifyItem -> MainView(
                    Icons.Outlined.ArrowBack,
                    loading = loading,
                    snackMessage = snackMessage,
                    up = {
                        modifyCabinetItemViewModel.clear()
                        actions.upPress()
                    }
                ) {
                    ModifySerialItem(
                        authViewModel = authViewModel,
                        itemViewModel = modifyCabinetItemViewModel,
                        categoriesViewModel = categoriesViewModel,
                        electronicsViewModel = electronicsViewModel,
                        manufacturersViewModel = manufacturersViewModel,
                        item = electronicsViewModel.allElectronics.value.firstOrNull { it.id == destination.id },
                        back = {
                            modifyCabinetItemViewModel.clear()
                            actions.upPress()
                        },
                        barcodeCamera = actions.camera
                    )
                }
                is Destination.CameraPreview -> MainView(
                    Icons.Outlined.ArrowBack,
                    loading = false,
                    up = actions.upPress
                ) {
                    CameraPreviewLayout(
                        viewModel = modifyCabinetItemViewModel,
                        done = actions.upPress
                    )
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

                electronicsViewModel.getElectronics()
                categoriesViewModel.getCategories()
                manufacturersViewModel.getManufacturers()

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
