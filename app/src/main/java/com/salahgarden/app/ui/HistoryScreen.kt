package com.salahgarden.app.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.data.DayGarden
import com.salahgarden.app.data.Prayer
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
fun HistoryScreen(
    state: GardenUiState,
    onOpenToday: () -> Unit,
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
                    text = "Every day you watered.",
                    style = GardenText.screenTitle.copy(fontSize = 28.sp),
                    color = LeafDark
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "The whole garden, newest first — never trimmed.",
                    style = GardenText.body.copy(fontSize = 11.5.sp, lineHeight = 16.sp),
                    color = Color(0xFF687F6E),
                    modifier = Modifier.fillMaxWidth(0.86f)
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
                contentPadding = PaddingValues(15.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HistoryStat(
                        value = state.historyTotal.toString(),
                        label = "prayers\nwatered",
                        modifier = Modifier.weight(1f)
                    )
                    HistoryStat(
                        value = state.historyPerfectDays.toString(),
                        label = "perfect\ndays",
                        modifier = Modifier.weight(1f)
                    )
                    HistoryStat(
                        value = state.streak.toString(),
                        label = "day\nstreak",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (state.history.isEmpty()) {
                GardenCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    corner = 20.dp,
                    contentPadding = PaddingValues(18.dp)
                ) {
                    Text(
                        text = "Nothing growing yet.",
                        style = GardenText.cardTitle,
                        color = LeafDark
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Log your first prayer today and it will appear here.",
                        style = GardenText.body.copy(fontSize = 11.sp, lineHeight = 16.sp),
                        color = Color(0xFF687F6E)
                    )
                }
            } else {
                HistoryList(
                    history = state.history,
                    today = state.date
                )
            }
        }

        GardenTabBar(
            active = state.screen,
            onToday = onOpenToday,
            onOpenWeek = onOpenToday,
            onHistory = onOpenHistory,
            onLearn = onOpenHistory,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HistoryStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Paper.copy(alpha = 0.13f))
            .padding(vertical = 10.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = GardenText.bigNumber,
            color = Paper
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            style = GardenText.body.copy(
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 11.sp
            ),
            color = Color(0xFFDCEBD8)
        )
    }
}

@Composable
private fun HistoryList(
    history: List<DayGarden>,
    today: java.time.LocalDate
) {
    val grouped = history.groupBy { it.date.monthValue to it.date.year }
    grouped.forEach { (key, days) ->
        val (_, year) = key
        Spacer(Modifier.height(14.dp))
        Text(
            text = monthLabel(days.first().date),
            style = GardenText.micro.copy(
                fontSize = 8.5.sp,
                letterSpacing = 1.sp,
                color = Color(0xFF8B9A8C)
            ),
            modifier = Modifier.padding(start = 2.dp, bottom = 5.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            days.forEach { day ->
                HistoryDayRow(
                    day = day,
                    isToday = day.date == today
                )
            }
        }
    }
}

@Composable
private fun HistoryDayRow(
    day: DayGarden,
    isToday: Boolean
) {
    val shape = RoundedCornerShape(15.dp)
    val missed = Prayer.ordered.count { !day.log.isLogged(it) }
    val madeUp = day.log.madeUp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Paper)
            .drawBehind {
                drawRoundRect(
                    color = if (isToday) Color(0xFFF2DFB4) else Line,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(15.dp.toPx()),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2.dp.toPx())
                )
            }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WeekFlower(
            filled = day.log.count,
            modifier = Modifier.size(38.dp, 43.dp)
        )
        Spacer(Modifier.width(11.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = if (isToday) "Today" else fullDateLine(day.date),
                style = GardenText.body.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                color = if (isToday) Color(0xFFB77B22) else LeafDark
            )
            Spacer(Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = LeafDark, fontWeight = FontWeight.Bold)) {
                            append("${day.log.count} of 5")
                        }
                        append(" watered")
                    },
                    style = GardenText.body.copy(fontSize = 9.5.sp),
                    color = Color(0xFF819183)
                )
                if (madeUp.isNotEmpty()) {
                    Spacer(Modifier.width(7.dp))
                    Text(
                        text = "· ${madeUp.size} made up",
                        style = GardenText.body.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Coral
                    )
                }
                if (missed > 0) {
                    Spacer(Modifier.width(7.dp))
                    Text(
                        text = "· $missed missed",
                        style = GardenText.body.copy(fontSize = 9.sp),
                        color = Color(0xFF9AA79A)
                    )
                }
            }
        }
        if (day.log.complete) {
            GardenIcon(GardenIconKind.CHECK, Modifier.size(15.dp), tint = Leaf)
        }
    }
}
