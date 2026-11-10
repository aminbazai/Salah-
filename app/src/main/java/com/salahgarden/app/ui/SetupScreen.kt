package com.salahgarden.app.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.data.Cities
import com.salahgarden.app.data.City
import com.salahgarden.app.data.PrayerTimeProvider
import com.salahgarden.app.ui.theme.Coral
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.Ink
import com.salahgarden.app.ui.theme.InkSoft
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.LeafSoft
import com.salahgarden.app.ui.theme.Line
import com.salahgarden.app.ui.theme.Mint
import com.salahgarden.app.ui.theme.Paper

@Composable
fun SetupScreen(
    state: GardenUiState,
    onBack: () -> Unit,
    onSearch: (String) -> Unit,
    onSelectCity: (City) -> Unit,
    onUseCurrentLocation: () -> Unit,
    onToggleReminders: () -> Unit,
    onSave: () -> Unit,
    onTestNotification: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val query = state.searchQuery.trim().lowercase()
    val cities = buildList {
        if (state.setupCity.id == "current") add(state.setupCity)
        addAll(
            Cities.all.filter { city ->
                query.isEmpty() || "${city.name} ${city.country}".lowercase().contains(query)
            }
        )
    }

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
                .padding(top = 9.dp, bottom = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onClick = onBack, description = "Back to today")
            Spacer(Modifier.weight(1f))
            Text(
                text = "STEP 1 OF 1",
                style = GardenText.microUppercase,
                color = Color(0xFF859587)
            )
        }

        Text(
            text = buildAnnotatedString {
                append("Where does your\n")
                withStyle(SpanStyle(color = Coral)) { append("garden grow?") }
            },
            style = GardenText.screenTitle,
            color = LeafDark
        )
        Spacer(Modifier.height(7.dp))
        Text(
            text = "Choose a place once. We'll find its five prayer times and keep today's sky ready for you.",
            style = GardenText.body.copy(fontSize = 12.sp, lineHeight = 17.5.sp),
            color = Color(0xFF687F6E),
            modifier = Modifier.fillMaxWidth(0.84f)
        )

        SearchField(
            value = state.searchQuery,
            onValueChange = onSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        )

        GpsButton(
            locating = state.locating,
            onClick = {
                focusManager.clearFocus()
                onUseCurrentLocation()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your sky · ",
                style = GardenText.caption.copy(fontSize = 9.3.sp),
                color = Color(0xFF819183)
            )
            Text(
                text = state.setupCity.name,
                style = GardenText.caption.copy(fontSize = 9.3.sp),
                color = Color(0xFF4F6B58)
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Fajr ${PrayerTimeProvider.format(state.setupFajr)} · Maghrib ${
                    PrayerTimeProvider.format(state.setupMaghrib)
                }",
                style = GardenText.caption.copy(fontSize = 9.3.sp),
                color = Color(0xFF819183)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            if (cities.isEmpty()) {
                Text(
                    text = "No match yet. Try another city, or use your current location above.",
                    style = GardenText.body.copy(fontSize = 11.sp, lineHeight = 16.5.sp),
                    color = Color(0xFF819183),
                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 10.dp)
                )
            } else {
                cities.forEach { city ->
                    CityRow(
                        city = city,
                        selected = state.setupCity.id == city.id,
                        onClick = {
                            focusManager.clearFocus()
                            onSelectCity(city)
                        }
                    )
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Paper.copy(alpha = 0.7f))
                .border(1.dp, Line, RoundedCornerShape(15.dp))
                .padding(horizontal = 12.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Nudge me at each adhan",
                    style = GardenText.body.copy(fontSize = 11.5.sp, fontWeight = FontWeight.Bold, lineHeight = 15.sp),
                    color = LeafDark
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "One reminder per prayer. Quiet after Isha.",
                    style = GardenText.body.copy(fontSize = 9.5.sp, lineHeight = 13.sp),
                    color = Color(0xFF7B8E7E)
                )
            }
            GardenSwitch(
                checked = state.reminders,
                onCheckedChange = { onToggleReminders() }
            )
        }

        if (onTestNotification != null) {
            Text(
                text = "Preview a gentle nudge",
                style = GardenText.caption.copy(fontSize = 9.sp),
                color = Leaf,
                modifier = Modifier
                    .padding(top = 8.dp, start = 3.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onTestNotification
                    )
                    .padding(3.dp)
            )
        }

        ChunkyButton(
            text = "Use ${state.setupCity.name}",
            onClick = {
                focusManager.clearFocus()
                onSave()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var focused by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .height(46.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Paper)
            .border(
                width = 1.5.dp,
                color = if (focused) Leaf else Color(0xFFD4DDCA),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(start = 14.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GardenIcon(GardenIconKind.SEARCH, Modifier.size(16.dp), tint = Color(0xFF86988A))
        Spacer(Modifier.width(10.dp))
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(
                    text = "Search a city",
                    style = GardenText.body.copy(fontSize = 12.5.sp),
                    color = Color(0xFF9AA89A)
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = GardenText.body.copy(fontSize = 12.5.sp, color = Ink),
                cursorBrush = SolidColor(Leaf),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focused = it.isFocused }
            )
        }
    }
}

@Composable
private fun GpsButton(
    locating: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(LeafSoft.copy(alpha = 0.6f))
            .gardenDashedBorder(Color(0xFF9FC7A6), 1.5.dp, 14.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(31.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Paper),
            contentAlignment = Alignment.Center
        ) {
            GardenIcon(GardenIconKind.TARGET, Modifier.size(16.dp), tint = Leaf)
        }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = if (locating) "Finding your garden…" else "Use my current location",
                style = GardenText.body.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 15.sp),
                color = LeafDark
            )
            Text(
                text = if (locating) "One moment · using your device" else "Fastest · asks once",
                style = GardenText.body.copy(fontSize = 9.sp, lineHeight = 12.sp),
                color = Color(0xFF75897A)
            )
        }
        if (locating) {
            CircularProgressIndicator(
                Modifier.size(16.dp),
                color = Leaf,
                strokeWidth = 2.dp
            )
        }
    }
}

@Composable
private fun CityRow(
    city: City,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(13.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp)
            .clip(shape)
            .background(if (selected) Color(0xFFF0F8EA) else Paper)
            .border(
                width = 1.5.dp,
                color = if (selected) Leaf else Color(0xFFDBE0CF),
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(31.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (selected) Mint else Color(0xFFF0F4E8)),
            contentAlignment = Alignment.Center
        ) {
            GardenIcon(
                GardenIconKind.PIN,
                Modifier.size(14.dp),
                tint = if (selected) LeafDark else Color(0xFF7B917F)
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = city.name,
                style = GardenText.body.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 15.sp),
                color = Color(0xFF3D5B49)
            )
            Text(
                text = "${city.country} · ${city.method.label}",
                style = GardenText.body.copy(fontSize = 9.3.sp, lineHeight = 12.sp),
                color = Color(0xFF8B998C)
            )
        }
        Box(
            Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(if (selected) Leaf else Color.Transparent)
                .border(1.5.dp, if (selected) Leaf else Color(0xFFD4DDCA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                GardenIcon(GardenIconKind.CHECK, Modifier.size(10.dp), tint = Paper, strokeWidth = 2.6f)
            }
        }
    }
}
