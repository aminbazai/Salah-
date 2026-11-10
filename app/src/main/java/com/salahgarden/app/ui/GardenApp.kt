package com.salahgarden.app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.Celebration
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.data.City
import com.salahgarden.app.data.Prayer
import com.salahgarden.app.ui.theme.Coral
import com.salahgarden.app.ui.theme.Cream
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.Sun
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun GardenApp(
    state: GardenUiState,
    learnRepository: com.salahgarden.app.learn.LearnSalahRepository,
    onLogPrayer: () -> Unit,
    onOpenSetup: () -> Unit,
    onOpenMissed: (Prayer?) -> Unit,
    onOpenToday: () -> Unit,
    onOpenWeek: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenLearn: () -> Unit,
    onNameChange: (String) -> Unit,
    onRequestNotifications: () -> Unit,
    onRequestLocation: () -> Unit,
    onFinishOnboarding: () -> Unit,
    onCloseSetup: () -> Unit,
    onSearch: (String) -> Unit,
    onSelectCity: (City) -> Unit,
    onUseCurrentLocation: () -> Unit,
    onToggleReminders: () -> Unit,
    onSaveLocation: () -> Unit,
    onCloseMissed: () -> Unit,
    onAnotherHadith: () -> Unit,
    onPrayMissedNow: () -> Unit,
    onDismissCelebration: () -> Unit,
    onClearToast: (Long) -> Unit,
    onTestNotification: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = state.screen != GardenScreen.TODAY) {
        when (state.screen) {
            GardenScreen.SETUP -> onCloseSetup()
            GardenScreen.MISSED -> onCloseMissed()
            GardenScreen.WEEK -> onOpenToday()
            GardenScreen.HISTORY -> onOpenToday()
            GardenScreen.LESSON, GardenScreen.LEARN -> onOpenToday()
            GardenScreen.ONBOARDING, GardenScreen.TODAY -> Unit
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        AppBackdrop(Modifier.fillMaxSize())

        Box(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Crossfade(
                targetState = state.screen,
                animationSpec = tween(260),
                label = "gardenScreen"
            ) { screen ->
                when (screen) {
                    GardenScreen.ONBOARDING -> OnboardingScreen(
                        state = state,
                        onNameChange = onNameChange,
                        onRequestNotifications = onRequestNotifications,
                        onRequestLocation = onRequestLocation,
                        onFinish = onFinishOnboarding
                    )

                    GardenScreen.TODAY -> TodayScreen(
                        state = state,
                        onOpenSetup = onOpenSetup,
                        onOpenMissed = onOpenMissed,
                        onLogPrayer = onLogPrayer,
                        onOpenToday = onOpenToday,
                        onOpenWeek = onOpenWeek,
                        onOpenHistory = onOpenHistory,
                        onOpenLearn = onOpenLearn
                    )

                    GardenScreen.SETUP -> SetupScreen(
                        state = state,
                        onBack = onCloseSetup,
                        onSearch = onSearch,
                        onSelectCity = onSelectCity,
                        onUseCurrentLocation = onUseCurrentLocation,
                        onToggleReminders = onToggleReminders,
                        onSave = onSaveLocation,
                        onTestNotification = onTestNotification
                    )

                    GardenScreen.MISSED -> MissedScreen(
                        state = state,
                        onClose = onCloseMissed,
                        onAnotherHadith = onAnotherHadith,
                        onPrayNow = onPrayMissedNow,
                        onKeepGoing = onCloseMissed
                    )

                    GardenScreen.WEEK -> WeekScreen(
                        state = state,
                        onOpenToday = onOpenToday,
                        onOpenWeek = onOpenWeek,
                        onOpenHistory = onOpenHistory
                    )

                    GardenScreen.HISTORY -> HistoryScreen(
                        state = state,
                        onOpenToday = onOpenToday,
                        onOpenHistory = onOpenHistory
                    )

                    GardenScreen.LEARN, GardenScreen.LESSON -> LearnSalahScreen(
                        state = state,
                        repository = learnRepository,
                        onOpenToday = onOpenToday,
                        onOpenWeek = onOpenWeek,
                        onOpenHistory = onOpenHistory,
                        onOpenLearn = onOpenLearn
                    )
                }
            }
        }

        CelebrationOverlay(
            celebration = state.celebration,
            onDismiss = onDismissCelebration
        )

        ToastHost(
            message = state.toast,
            id = state.toastId,
            onClear = onClearToast
        )
    }
}

@Composable
private fun CelebrationOverlay(
    celebration: Celebration?,
    onDismiss: () -> Unit
) {
    var shown by remember { mutableStateOf<Celebration?>(null) }
    LaunchedEffect(celebration) {
        if (celebration != null) shown = celebration
    }

    AnimatedVisibility(
        visible = celebration != null,
        enter = fadeIn(tween(280)),
        exit = fadeOut(tween(220))
    ) {
        val content = shown
        if (content != null) {
            val pop = remember { Animatable(0.45f) }
            LaunchedEffect(Unit) {
                pop.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 380f))
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Cream.copy(alpha = 0.9f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDismiss
                    ),
                contentAlignment = Alignment.Center
            ) {
                PetalRain()
                Column(
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .size(96.dp)
                            .scale(pop.value)
                            .rotate((1f - pop.value) * -16f)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFFFFF6D8), Sun)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BloomBadge(Modifier.size(51.dp))
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = content.title,
                        style = GardenText.celebrationTitle,
                        color = LeafDark,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(7.dp))
                    Text(
                        text = content.body,
                        style = GardenText.body.copy(fontSize = 12.5.sp, lineHeight = 18.5.sp),
                        color = Color(0xFF647B69),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.widthIn(max = 230.dp)
                    )
                    ChunkyButton(
                        text = "Back to the garden",
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 21.dp)
                    )
                }
            }
        }
    }
}

private data class PetalSeed(
    val x: Float,
    val start: Float,
    val speed: Float,
    val color: Color
)

@Composable
private fun PetalRain() {
    val transition = rememberInfiniteTransition(label = "petals")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2100, easing = LinearEasing)),
        label = "petalProgress"
    )
    val seeds = remember {
        val colors = listOf(Coral, Sun, Color(0xFF8FC47F), Color(0xFFA477B8), Color(0xFFF4C7A8))
        val random = Random(42)
        List(22) { index ->
            PetalSeed(
                x = 0.06f + random.nextFloat() * 0.88f,
                start = random.nextFloat(),
                speed = 0.8f + random.nextFloat() * 0.55f,
                color = colors[index % colors.size]
            )
        }
    }
    Canvas(Modifier.fillMaxSize()) {
        seeds.forEach { seed ->
            val phase = (progress * seed.speed + seed.start) % 1f
            val x = size.width * seed.x + sin(phase * 6.283f) * 10.dp.toPx()
            val y = -20.dp.toPx() + phase * (size.height + 40.dp.toPx())
            val alpha = if (phase > 0.8f) ((1f - phase) / 0.2f).coerceIn(0f, 1f) else 1f
            rotate(phase * 540f, pivot = Offset(x, y)) {
                drawOval(
                    color = seed.color.copy(alpha = 0.9f * alpha),
                    topLeft = Offset(x - 4.5.dp.toPx(), y),
                    size = Size(9.dp.toPx(), 13.dp.toPx())
                )
            }
        }
    }
}

@Composable
private fun ToastHost(
    message: String?,
    id: Long,
    onClear: (Long) -> Unit
) {
    LaunchedEffect(id, message) {
        if (message != null) {
            delay(3000)
            onClear(id)
        }
    }
    var shown by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(message) {
        if (message != null) shown = message
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = message != null,
            enter = fadeIn(tween(180)) + slideInVertically(tween(220)) { it / 3 },
            exit = fadeOut(tween(180))
        ) {
            Box(
                Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 100.dp, start = 20.dp, end = 20.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(LeafDark)
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Text(
                    text = shown ?: "",
                    style = GardenText.body.copy(
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 14.sp
                    ),
                    color = Paper,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
