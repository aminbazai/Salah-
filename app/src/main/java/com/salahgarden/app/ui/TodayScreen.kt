package com.salahgarden.app.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.data.Prayer
import com.salahgarden.app.data.PrayerTimeProvider
import com.salahgarden.app.ui.theme.Coral
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.Ink
import com.salahgarden.app.ui.theme.InkFaint
import com.salahgarden.app.ui.theme.InkSoft
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.LeafSoft
import com.salahgarden.app.ui.theme.Mint
import com.salahgarden.app.ui.theme.Paper
import java.time.LocalTime

@Composable
fun TodayScreen(
    state: GardenUiState,
    onOpenSetup: () -> Unit,
    onOpenMissed: (Prayer?) -> Unit,
    onLogPrayer: () -> Unit,
    onOpenToday: () -> Unit,
    onOpenWeek: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val logged = state.log.isLogged(state.currentPrayer)
    val madeUp = state.log.isMadeUp(state.currentPrayer)
    val fill by animateFloatAsState(
        targetValue = state.fillFraction,
        animationSpec = tween(500),
        label = "waterFill"
    )

    Box(modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 112.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 9.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = dateLine(state.date),
                        style = GardenText.microUppercase,
                        color = Color(0xFF849587)
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Salam")
                            if (state.userName.isNotBlank()) {
                                append(", ")
                                withStyle(SpanStyle(color = Coral)) { append(state.userName.trim()) }
                            }
                            append("!")
                        },
                        style = GardenText.screenTitle,
                        color = LeafDark
                    )
                }
                StreakPill(state.streak)
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 9.dp, bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Button,
                            onClick = onOpenSetup
                        )
                        .padding(vertical = 6.dp, horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GardenIcon(GardenIconKind.PIN, Modifier.size(13.dp), tint = Leaf)
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "${state.city.name}, ${state.city.country}".trimEnd(',', ' '),
                        style = GardenText.body.copy(fontSize = 11.5.sp, fontWeight = FontWeight.Bold),
                        color = InkSoft
                    )
                    Spacer(Modifier.width(3.dp))
                    GardenIcon(GardenIconKind.CHEVRON, Modifier.size(11.dp), tint = Color(0xFF93A092))
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = state.city.methodLabel,
                    style = GardenText.caption.copy(fontSize = 9.sp),
                    color = Color(0xFF93A092)
                )
            }

            GardenCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = Color(0xFF355F47),
                borderWidth = 1.5.dp,
                corner = 26.dp,
                shadowColor = LeafDark.copy(alpha = 0.16f),
                shadowOffset = 5.dp
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(128.dp)
                        .clipToBounds()
                ) {
                    SkyArt(Modifier.fillMaxSize()) {
                        BudSprout(
                            Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 31.dp, bottom = 4.dp)
                                .size(73.dp, 88.dp)
                        )
                    }
                    SkyChip(
                        text = when {
                            madeUp -> "Made up · ${state.currentPrayer.displayName}"
                            logged -> "Watered · ${state.currentPrayer.displayName}"
                            else -> "Now · ${state.currentPrayer.displayName}"
                        },
                        done = logged,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(13.dp)
                    )
                }

                Column(Modifier.padding(start = 15.dp, end = 15.dp, top = 13.dp, bottom = 15.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = state.currentPrayer.displayName,
                            style = GardenText.hero,
                            color = LeafDark
                        )
                        Spacer(Modifier.weight(1f))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${state.nextPrayer.displayName} in",
                                style = GardenText.body.copy(fontSize = 11.5.sp, lineHeight = 15.sp),
                                color = Color(0xFF6D8172)
                            )
                            Text(
                                text = if (logged) "done today" else formatDuration(state.minutesUntilNext),
                                style = GardenText.body.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 19.sp
                                ),
                                color = LeafDark
                            )
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE7EDDB))
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth(fill)
                                .height(6.dp)
                                .clip(CircleShape)
                                .background(
                                    androidx.compose.ui.graphics.Brush.horizontalGradient(
                                        listOf(Color(0xFF92C8DF), Leaf)
                                    )
                                )
                        )
                    }

                    ChunkyButton(
                        text = when {
                            madeUp -> "${state.currentPrayer.displayName} made up"
                            logged -> "${state.currentPrayer.displayName} watered"
                            else -> "I prayed ${state.currentPrayer.displayName}"
                        },
                        onClick = onLogPrayer,
                        style = if (logged) ChunkyStyle.SOFT else ChunkyStyle.PRIMARY,
                        leadingIcon = if (logged) GardenIconKind.CHECK else GardenIconKind.WATER,
                        enabled = !logged,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
            }

            GardenCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                corner = 22.dp,
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 14.dp, bottom = 12.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's garden",
                        style = GardenText.cardTitle,
                        color = LeafDark
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "${state.log.count} of 5 watered",
                        style = GardenText.caption.copy(fontSize = 9.5.sp),
                        color = Leaf
                    )
                }
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth()) {
                    Prayer.ordered.forEachIndexed { index, prayer ->
                        val status = when {
                            state.log.isLogged(prayer) -> PlantStatus.DONE
                            prayer == state.currentPrayer -> PlantStatus.CURRENT
                            state.schedule.timeOf(prayer).isBefore(state.schedule.timeOf(state.currentPrayer)) ->
                                PlantStatus.MISSED
                            else -> PlantStatus.UPCOMING
                        }
                        PlantCell(
                            prayer = prayer,
                            time = state.schedule.timeOf(prayer),
                            status = status,
                            color = BloomColors[index],
                            onMissedClick = if (status == PlantStatus.MISSED) {
                                { onOpenMissed(prayer) }
                            } else {
                                null
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            val firstMissed = Prayer.ordered.firstOrNull { prayer ->
                !state.log.isLogged(prayer) &&
                    state.schedule.timeOf(prayer).isBefore(state.schedule.timeOf(state.currentPrayer))
            }
            val noteTitle: String
            val noteText: String
            val noteAction: (() -> Unit)?
            when {
                firstMissed != null -> {
                    noteTitle = "A sprout is thirsty."
                    noteText = "Tap the droopy plant for a gentle check-in. No streak lost."
                    noteAction = { onOpenMissed(firstMissed) }
                }

                logged -> {
                    noteTitle = "${state.currentPrayer.displayName} is watered."
                    noteText = "${state.log.count} of 5 today · ${state.nextPrayer.displayName} at ${
                        PrayerTimeProvider.format(state.nextTime)
                    }."
                    noteAction = null
                }

                else -> {
                    noteTitle = "${state.currentPrayer.displayName} is ready for water."
                    noteText = "Before ${PrayerTimeProvider.format(state.nextTime)} · your next nudge is 20 min before."
                    noteAction = null
                }
            }
            BudNote(
                title = noteTitle,
                text = noteText,
                onClick = noteAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 11.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(LeafSoft.copy(alpha = 0.75f))
                    .border(1.dp, Color(0xFFCFE3CF), RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Button,
                        onClick = onOpenLearn
                    )
                    .padding(horizontal = 13.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Paper),
                    contentAlignment = Alignment.Center
                ) {
                    GardenIcon(GardenIconKind.FLOWER_FILLED, Modifier.size(19.dp), tint = Leaf)
                }
                Spacer(Modifier.width(11.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "How to pray",
                        style = GardenText.cardTitle.copy(fontSize = 13.5.sp),
                        color = LeafDark
                    )
                    Spacer(Modifier.height(1.dp))
                    Text(
                        text = "A beginner's guide — step by step.",
                        style = GardenText.body.copy(fontSize = 10.sp, lineHeight = 14.sp),
                        color = Color(0xFF647B69)
                    )
                }
                GardenIcon(GardenIconKind.CHEVRON, Modifier.size(15.dp), tint = Color(0xFF93A092))
            }
        }

        GardenTabBar(
            active = state.screen,
            onToday = onOpenToday,
            onOpenWeek = onOpenWeek,
            onHistory = onOpenHistory,
            onLearn = onOpenLearn,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SkyChip(
    text: String,
    done: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(Paper.copy(alpha = 0.86f))
            .border(1.dp, Ink.copy(alpha = 0.15f), CircleShape)
            .padding(start = 9.dp, end = 9.dp, top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(if (done) Leaf else Coral)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text.uppercase(),
            style = GardenText.micro.copy(fontSize = 8.5.sp, letterSpacing = 0.6.sp),
            color = Color(0xFF4B6A55)
        )
    }
}

@Composable
private fun PlantCell(
    prayer: Prayer,
    time: LocalTime,
    status: PlantStatus,
    color: Color,
    onMissedClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    val interaction = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = modifier
            .clip(shape)
            .then(
                if (status == PlantStatus.CURRENT) {
                    Modifier.background(Coral.copy(alpha = 0.07f))
                } else {
                    Modifier
                }
            )
            .then(
                if (onMissedClick != null) {
                    Modifier.clickable(
                        interactionSource = interaction,
                        indication = null,
                        role = Role.Button,
                        onClick = onMissedClick
                    )
                } else {
                    Modifier
                }
            )
            .semantics {
                contentDescription = "${prayer.displayName}, ${PrayerTimeProvider.format(time)}, " +
                    status.name.lowercase()
            }
            .padding(top = 3.dp, bottom = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlantArt(
            status = status,
            color = color,
            modifier = Modifier.size(54.dp, 66.dp)
        )
        Text(
            text = prayer.displayName,
            style = GardenText.plantLabel,
            color = if (status == PlantStatus.CURRENT) Coral else InkSoft
        )
        Text(
            text = PrayerTimeProvider.format(time),
            style = GardenText.plantTime
        )
    }
}

@Composable
private fun BudNote(
    title: String,
    text: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(15.dp)
    Row(
        modifier = modifier
            .clip(shape)
            .background(LeafSoft.copy(alpha = 0.72f))
            .gardenDashedBorder(Color(0xFFB9D5BD), 1.dp, 15.dp)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Button,
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Paper),
            contentAlignment = Alignment.Center
        ) {
            GardenIcon(GardenIconKind.LEAF, Modifier.size(16.dp), tint = Leaf)
        }
        Spacer(Modifier.width(9.dp))
        Column {
            Text(
                text = title,
                style = GardenText.body.copy(fontSize = 10.5.sp, fontWeight = FontWeight.Bold, lineHeight = 14.sp),
                color = LeafDark
            )
            Spacer(Modifier.height(1.dp))
            Text(
                text = text,
                style = GardenText.body.copy(fontSize = 9.5.sp, lineHeight = 13.sp),
                color = Color(0xFF6D8172),
                textAlign = TextAlign.Start
            )
        }
    }
}
