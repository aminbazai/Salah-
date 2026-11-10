package com.salahgarden.app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.salahgarden.app.data.Prayer
import com.salahgarden.app.ui.GardenApp
import com.salahgarden.app.ui.theme.SalahGardenTheme

class MainActivity : ComponentActivity() {

    private var model: GardenViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                0xFFFFF9EC.toInt(),
                0xFFFFF9EC.toInt()
            )
        )

        val demo = intent?.getBooleanExtra(EXTRA_DEMO, false) == true && BuildConfig.DEBUG
        val requestedScreen = intent?.getStringExtra(EXTRA_OPEN_SCREEN)

        setContent {
            val viewModel: GardenViewModel = viewModel(
                factory = GardenViewModel.factory(application, demo)
            )
            model = viewModel
            val state by viewModel.state.collectAsStateWithLifecycle()
            val context = applicationContext

            LaunchedEffect(Unit) {
                when (requestedScreen) {
                    "setup" -> viewModel.openSetup()
                    "missed" -> viewModel.openMissed(Prayer.ASR)
                    "week" -> viewModel.openWeek()
                    "history" -> viewModel.openHistory()
                    "guide" -> viewModel.openLearn()
                    "learn" -> viewModel.openLearn()
                    "today" -> viewModel.openToday()
                }
            }

            val notificationPermission = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { }

            LaunchedEffect(state.notificationPrompt) {
                if (state.notificationPrompt > 0 &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            var pendingLocationAction by remember { mutableStateOf<(() -> Unit)?>(null) }
            val locationPermission = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { result ->
                val granted = result.values.any { it }
                val action = pendingLocationAction
                pendingLocationAction = null
                if (granted) action?.invoke() else viewModel.locationDenied()
            }

            val requestLocation: () -> Unit = {
                if (hasLocationPermission(context)) {
                    viewModel.useCurrentLocation()
                } else {
                    pendingLocationAction = { viewModel.useCurrentLocation() }
                    locationPermission.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                }
            }

            /** Used by the first-run onboarding: ask, then move on either way. */
            val onboardingLocationRequest: () -> Unit = {
                if (!hasLocationPermission(context)) {
                    locationPermission.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                }
            }

            val testNotification: (() -> Unit)? = if (BuildConfig.DEBUG) {
                { viewModel.showTestNotification() }
            } else {
                null
            }

            SalahGardenTheme {
                GardenApp(
                    state = state,
                    learnRepository = com.salahgarden.app.learn.LearnSalahRepository(context.applicationContext),
                    onLogPrayer = viewModel::logCurrentPrayer,
                    onOpenSetup = viewModel::openSetup,
                    onOpenMissed = { viewModel.openMissed(it) },
                    onOpenToday = viewModel::openToday,
                    onOpenWeek = viewModel::openWeek,
                    onOpenHistory = viewModel::openHistory,
                    onOpenLearn = viewModel::openLearn,
                    onNameChange = viewModel::updateName,
                    onRequestNotifications = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    },
                    onRequestLocation = onboardingLocationRequest,
                    onFinishOnboarding = viewModel::finishOnboarding,
                    onCloseSetup = viewModel::closeSetup,
                    onSearch = viewModel::updateSearch,
                    onSelectCity = viewModel::selectCity,
                    onUseCurrentLocation = requestLocation,
                    onToggleReminders = viewModel::toggleReminders,
                    onSaveLocation = viewModel::saveLocation,
                    onCloseMissed = viewModel::closeMissed,
                    onAnotherHadith = viewModel::nextHadith,
                    onPrayMissedNow = viewModel::prayMissedNow,
                    onDismissCelebration = viewModel::dismissCelebration,
                    onClearToast = viewModel::clearToast,
                    onTestNotification = testNotification
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        model?.refresh()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        model?.openToday()
    }

    private fun hasLocationPermission(context: Context): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

    companion object {
        const val EXTRA_OPEN_SCREEN = "screen"
        const val EXTRA_DEMO = "demo"
    }
}
