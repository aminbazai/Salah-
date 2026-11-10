package com.salahgarden.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.data.PrayerTimeProvider
import com.salahgarden.app.ui.theme.Coral
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.InkSoft
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.LeafSoft
import com.salahgarden.app.ui.theme.Line
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.Sun

@Composable
fun WeekScreen(
    state: GardenUiState,
    onOpenToday: () -> Unit,
    onOpenWeek: () -> Unit,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 112.dp)
        ) {
            Column(Modifier.padding(top = 10.dp)) {
                Text(
                    text = "YOUR GARDEN",
                    style = GardenText.microUppercase,
                    color = Color(0xFF849587)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Your garden this week.",
                    style = GardenText.screenTitle.copy(fontSize = 30.sp),
                    color = LeafDark
                )
            }

            GardenCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 13.dp),
                color = Leaf,
                borderColor = LeafDark,
                borderWidth = 1.5.dp,
                corner = 22.dp,
                shadowColor = LeafDark.copy(alpha = 0.25f),
                shadowOffset = 5.dp,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(15.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(17.dp))
                            .background(Paper.copy(alpha = 0.13f))
                            .border(1.dp, Paper.copy(alpha = 0.17f), RoundedCornerShape(17.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        GardenIcon(GardenIconKind.WATER, Modifier.size(27.dp), tint = Paper)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = state.streak.toString(),
                                style = GardenText.bigNumber,
                                color = Paper
                            )
                            Spacer(Modifier.width(7.dp))
                            Text(
                                text = "days\nwatered",
                                style = GardenText.body.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 14.sp
                                ),
                                color = Color(0xFFDCEBD8)
                            )
                        }
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("Best run · ")
                                withStyle(SpanStyle(color = Paper, fontWeight = FontWeight.Bold)) {
                                    append("${state.bestRun} days")
                                }
                            },
                            style = GardenText.body.copy(fontSize = 9.5.sp, lineHeight = 13.sp),
                            color = Color(0xFFC7DFC6)
                        )
                    }
                }
            }

            GardenCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                color = Paper.copy(alpha = 0.8f),
                corner = 22.dp,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 9.dp,
                    end = 9.dp,
                    top = 14.dp,
                    bottom = 12.dp
                )
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    state.week.forEachIndexed { index, day ->
                        val isToday = index == state.week.lastIndex
                        WeekDayColumn(
                            label = if (isToday) "Today" else shortWeekday(day.date),
                            filled = day.log.count,
                            isToday = isToday,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 9.dp)
                        .drawBehind {
                            drawLine(
                                color = Color(0xFFE7E9DC),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                        .padding(start = 3.dp, end = 3.dp, top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    color = LeafDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            ) {
                                append("${state.weekTotal} of 35")
                            }
                            append(" prayers watered")
                        },
                        style = GardenText.body.copy(fontSize = 9.5.sp, lineHeight = 13.sp),
                        color = Color(0xFF819183)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Last 7 days",
                        style = GardenText.body.copy(fontSize = 9.5.sp, fontWeight = FontWeight.Bold, lineHeight = 13.sp),
                        color = Color(0xFF819183)
                    )
                }
            }

            Text(
                text = "View full history",
                style = GardenText.body.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                ),
                color = Leaf,
                modifier = Modifier
                    .padding(top = 10.dp, start = 3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Button,
                        onClick = onOpenHistory
                    )
                    .padding(horizontal = 4.dp, vertical = 4.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 11.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(LeafSoft.copy(alpha = 0.7f))
                    .border(1.dp, Color(0xFFCFE3CF), RoundedCornerShape(15.dp))
                    .padding(horizontal = 12.dp, vertical = 11.dp)
            ) {
                GardenIcon(GardenIconKind.LEAF, Modifier.size(15.dp), tint = Leaf)
                Spacer(Modifier.width(9.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = LeafDark, fontWeight = FontWeight.Bold)) {
                            append("A droopy day never kills the garden.")
                        }
                        append(" Water the next one. ${numberWord(state.streak)} good days are still growing.")
                    },
                    style = GardenText.body.copy(fontSize = 9.8.sp, lineHeight = 14.sp),
                    color = Color(0xFF647B69)
                )
            }

            GardenCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 11.dp),
                borderColor = Color(0xFFD5DFCA),
                borderWidth = 1.5.dp,
                corner = 18.dp,
                shadowColor = InkSoft.copy(alpha = 0.06f),
                shadowOffset = 2.dp,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(13.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "NEXT WATERING",
                            style = GardenText.micro.copy(
                                fontSize = 8.5.sp,
                                letterSpacing = 0.8.sp,
                                color = Color(0xFF8B9A8C)
                            )
                        )
                        Spacer(Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = state.nextPrayer.displayName,
                                style = GardenText.footerNumber,
                                color = LeafDark
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = PrayerTimeProvider.format(state.nextTime),
                                style = GardenText.body.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 17.sp
                                ),
                                color = Coral
                            )
                        }
                        Spacer(Modifier.height(1.dp))
                        Text(
                            text = "Tomorrow begins with Fajr at ${
                                PrayerTimeProvider.format(state.tomorrowFajr)
                            }",
                            style = GardenText.body.copy(fontSize = 9.sp, lineHeight = 12.sp),
                            color = Color(0xFF819183)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    SmallActionButton(
                        text = "Open today",
                        onClick = onOpenToday
                    )
                }
            }
        }

        GardenTabBar(
            active = state.screen,
            onToday = onOpenToday,
            onOpenWeek = onOpenWeek,
            onHistory = onOpenHistory,
            onLearn = onOpenHistory,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun WeekDayColumn(
    label: String,
    filled: Int,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = modifier
            .clip(shape)
            .background(if (isToday) Color(0xFFFFF5DF) else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isToday) Color(0xFFF2DFB4) else Color.Transparent,
                shape = shape
            )
            .padding(horizontal = 1.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = label.uppercase(),
            style = GardenText.micro.copy(
                fontSize = 7.5.sp,
                letterSpacing = 0.3.sp,
                color = if (isToday) Color(0xFFB77B22) else Color(0xFF8B9A8C)
            )
        )
        WeekFlower(
            filled = filled,
            modifier = Modifier.size(44.dp, 50.dp)
        )
    }
}

@Composable
private fun SmallActionButton(
    text: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(shape)
            .background(LeafSoft)
            .border(1.5.dp, Leaf, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 13.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = GardenText.body.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold, lineHeight = 13.sp),
            color = LeafDark
        )
    }
}
