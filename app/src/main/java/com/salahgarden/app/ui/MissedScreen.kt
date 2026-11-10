package com.salahgarden.app.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.Ink
import com.salahgarden.app.ui.theme.InkSoft
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.Sun
import com.salahgarden.app.ui.theme.SunSoft

@Composable
fun MissedScreen(
    state: GardenUiState,
    onClose: () -> Unit,
    onAnotherHadith: () -> Unit,
    onPrayNow: () -> Unit,
    onKeepGoing: () -> Unit,
    modifier: Modifier = Modifier
) {
    val prayer = state.missedPrayer
    val hadith = SalahHadiths[state.hadithIndex % SalahHadiths.size]

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 30.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 9.dp, bottom = 1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "GENTLE CHECK-IN · ${dateLine(state.date)}",
                style = GardenText.microUppercase,
                color = Color(0xFF859587)
            )
            Spacer(Modifier.weight(1f))
            BackButton(
                onClick = onClose,
                icon = GardenIconKind.CLOSE,
                description = "Close check-in"
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 0.dp),
            contentAlignment = Alignment.Center
        ) {
            WiltedPlant(Modifier.size(158.dp, 156.dp))
        }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${prayer.displayName}'s sprout\nis thirsty.",
                style = GardenText.screenTitle,
                color = LeafDark,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "A missed prayer doesn't ruin the garden. There's still water for today — and Bud is still here.",
                style = GardenText.body.copy(fontSize = 11.5.sp, lineHeight = 16.5.sp),
                color = Color(0xFF687F6E),
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 280.dp)
            )
        }

        HadithCard(
            hadith = hadith,
            index = state.hadithIndex,
            onAnotherHadith = onAnotherHadith,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
        )

        ChunkyButton(
            text = "Pray ${prayer.displayName} now",
            onClick = onPrayNow,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
        ChunkyButton(
            text = "Not now · keep going",
            onClick = onKeepGoing,
            style = ChunkyStyle.OUTLINE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 11.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GardenIcon(GardenIconKind.LEAF, Modifier.size(13.dp), tint = Leaf)
            Spacer(Modifier.width(6.dp))
            Text(
                text = if (state.streak > 0) {
                    "${numberWord(state.streak)} good days are still growing. Come back to the next one."
                } else {
                    "Today is a fresh start. Come back to the next one."
                },
                style = GardenText.body.copy(fontSize = 9.3.sp, lineHeight = 13.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFF7B8E7E),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun HadithCard(
    hadith: SalahHadith,
    index: Int,
    onAnotherHadith: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(22.dp)
    Box(modifier) {
        Box(
            Modifier
                .matchParentSize()
                .offset(y = 5.dp)
                .background(Color(0xFF143924).copy(alpha = 0.2f), shape)
        )
        Box(
            Modifier
                .fillMaxWidth()
                .clip(shape)
                .drawBehind {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF456B39), LeafDark),
                            center = Offset(size.width, 0f),
                            radius = size.width * 0.8f
                        )
                    )
                }
        ) {
            GardenIcon(
                GardenIconKind.LEAF,
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 30.dp, y = 25.dp)
                    .size(105.dp),
                tint = Paper.copy(alpha = 0.07f),
                strokeWidth = 1.2f
            )
            Column(Modifier.padding(16.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "HADITH · ${hadith.theme.uppercase()}",
                        style = GardenText.micro.copy(fontSize = 8.5.sp, letterSpacing = 0.9.sp),
                        color = Color(0xFFB9D6B9)
                    )
                    Spacer(Modifier.weight(1f))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        SalahHadiths.indices.forEach { dot ->
                            Box(
                                Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (dot == index % SalahHadiths.size) Sun
                                        else Paper.copy(alpha = 0.22f)
                                    )
                            )
                        }
                    }
                }
                Text(
                    text = hadith.text,
                    style = GardenText.quote,
                    color = Paper,
                    modifier = Modifier.padding(top = 11.dp, bottom = 9.dp)
                )
                Text(
                    text = hadith.source,
                    style = GardenText.body.copy(
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 13.sp
                    ),
                    color = Color(0xFFA9C4AD)
                )
                Row(
                    Modifier
                        .padding(top = 11.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Button,
                            onClick = onAnotherHadith
                        )
                        .padding(vertical = 1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GardenIcon(GardenIconKind.REFRESH, Modifier.size(11.dp), tint = SunSoft)
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "Another hadith",
                        style = GardenText.body.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 13.sp
                        ),
                        color = SunSoft
                    )
                }
            }
        }
    }
}
