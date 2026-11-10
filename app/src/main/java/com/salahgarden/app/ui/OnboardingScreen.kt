package com.salahgarden.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.ui.theme.Coral
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.Ink
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.LeafSoft
import com.salahgarden.app.ui.theme.Line
import com.salahgarden.app.ui.theme.Mint
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.Sun

/**
 * First run: ask for a name, then for the two permissions the app needs —
 * notifications and location. Both are optional; the app explains what each
 * one is for and the user can keep going without granting.
 */
@Composable
fun OnboardingScreen(
    state: GardenUiState,
    onNameChange: (String) -> Unit,
    onRequestNotifications: () -> Unit,
    onRequestLocation: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableIntStateOf(0) }
    val totalSteps = 3

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 24.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SALAH GARDEN",
                style = GardenText.microUppercase,
                color = Color(0xFF859587)
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "STEP ${step + 1} OF $totalSteps",
                style = GardenText.microUppercase,
                color = Color(0xFF859587)
            )
        }

        Spacer(Modifier.height(14.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            repeat(totalSteps) { position ->
                val filled = position <= step
                Box(
                    Modifier
                        .weight(1f)
                        .height(5.dp)
                        .clip(CircleShape)
                        .background(if (filled) Leaf else Color(0xFFE7E9DC))
                )
            }
        }

        when (step) {
            0 -> WelcomeStep(
                name = state.userName,
                onNameChange = onNameChange,
                onNext = { step = 1 }
            )

            1 -> PermissionStep(
                icon = GardenIconKind.SUN,
                title = "Nudges at prayer time",
                subtitle = "Notifications",
                body = "We send one gentle reminder at each adhan, and another 20 minutes before. Quiet after Isha, and you can turn them off any time.",
                actionLabel = "Allow notifications",
                grantedLabel = "Notifications are on",
                onAction = onRequestNotifications,
                onNext = { step = 2 },
                onSkip = { step = 2 }
            )

            else -> PermissionStep(
                icon = GardenIconKind.PIN,
                title = "Find your prayer times",
                subtitle = "Location",
                body = "Your city decides the five prayer times. Allow location and we'll set it for you — or pick a city by hand on the next screen.",
                actionLabel = "Allow location",
                grantedLabel = "Location is on",
                onAction = onRequestLocation,
                onNext = onFinish,
                onSkip = onFinish
            )
        }
    }
}

@Composable
private fun WelcomeStep(
    name: String,
    onNameChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Spacer(Modifier.height(18.dp))
    Text(
        text = "Assalamu\nAlaikum!",
        style = GardenText.screenTitle,
        color = LeafDark
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = "A small garden that grows every time you pray. What should we call you?",
        style = GardenText.body.copy(fontSize = 12.sp, lineHeight = 17.5.sp),
        color = Color(0xFF687F6E),
        modifier = Modifier.fillMaxWidth(0.92f)
    )

    NameField(
        value = name,
        onValueChange = onNameChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )

    Spacer(Modifier.height(10.dp))
    Text(
        text = "Your name stays on this phone. Nothing is sent anywhere.",
        style = GardenText.body.copy(fontSize = 9.5.sp, lineHeight = 13.5.sp),
        color = Color(0xFF819183)
    )

    ChunkyButton(
        text = if (name.isBlank()) "Continue" else "Continue as ${name.trim()}",
        onClick = onNext,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )

    GardenCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp),
        color = LeafSoft.copy(alpha = 0.6f),
        corner = 18.dp,
        contentPadding = PaddingValues(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GardenIcon(GardenIconKind.LEAF, Modifier.size(18.dp), tint = Leaf)
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Five prayers a day. One tap each. The garden remembers for you.",
                style = GardenText.body.copy(fontSize = 10.5.sp, lineHeight = 15.sp),
                color = Color(0xFF4F6B58)
            )
        }
    }
}

@Composable
private fun NameField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var focused by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Paper)
            .border(
                width = 1.5.dp,
                color = if (focused) Leaf else Color(0xFFD4DDCA),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(start = 15.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GardenIcon(GardenIconKind.FLOWER_FILLED, Modifier.size(17.dp), tint = Sun)
        Spacer(Modifier.width(10.dp))
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(
                    text = "Your name",
                    style = GardenText.body.copy(fontSize = 13.sp),
                    color = Color(0xFF9AA89A)
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = GardenText.body.copy(fontSize = 13.sp, color = Ink),
                cursorBrush = SolidColor(Leaf),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focused = it.isFocused }
            )
        }
    }
}

@Composable
private fun PermissionStep(
    icon: GardenIconKind,
    title: String,
    subtitle: String,
    body: String,
    actionLabel: String,
    grantedLabel: String,
    onAction: () -> Unit,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    Spacer(Modifier.height(18.dp))
    Box(
        Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(LeafSoft)
            .border(1.dp, Color(0xFFCFE3CF), RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        GardenIcon(icon, Modifier.size(36.dp), tint = Leaf)
    }

    Spacer(Modifier.height(14.dp))
    Text(
        text = subtitle.uppercase(),
        style = GardenText.micro.copy(
            fontSize = 8.5.sp,
            letterSpacing = 1.sp,
            color = Color(0xFF8B9A8C)
        )
    )
    Spacer(Modifier.height(3.dp))
    Text(
        text = title,
        style = GardenText.screenTitle.copy(fontSize = 28.sp),
        color = LeafDark
    )
    Spacer(Modifier.height(7.dp))
    Text(
        text = body,
        style = GardenText.body.copy(fontSize = 12.sp, lineHeight = 17.5.sp),
        color = Color(0xFF687F6E),
        modifier = Modifier.fillMaxWidth(0.94f)
    )

    Spacer(Modifier.height(18.dp))
    ChunkyButton(
        text = actionLabel,
        onClick = onAction,
        leadingIcon = icon,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(9.dp))
    ChunkyButton(
        text = "Not now",
        onClick = onSkip,
        style = ChunkyStyle.OUTLINE,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(12.dp))
    GardenCard(
        modifier = Modifier.fillMaxWidth(),
        corner = 16.dp,
        contentPadding = PaddingValues(13.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GardenIcon(GardenIconKind.LEAF, Modifier.size(16.dp), tint = Leaf)
            Spacer(Modifier.width(9.dp))
            Text(
                text = "You can change this any time from the garden's setup screen.",
                style = GardenText.body.copy(fontSize = 10.sp, lineHeight = 14.5.sp),
                color = Color(0xFF647B69)
            )
        }
    }
}
