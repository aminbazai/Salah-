package com.salahgarden.app.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.salahgarden.app.ui.theme.Cream
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.Ink
import com.salahgarden.app.ui.theme.InkFaint
import com.salahgarden.app.ui.theme.InkSoft
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.LeafSoft
import com.salahgarden.app.ui.theme.Line
import com.salahgarden.app.ui.theme.Mint
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.Sun

enum class ChunkyStyle { PRIMARY, SOFT, OUTLINE, DARK }

@Composable
fun GardenCard(
    modifier: Modifier = Modifier,
    color: Color = Paper,
    borderColor: Color = Line,
    borderWidth: Dp = 1.dp,
    corner: Dp = 22.dp,
    shadowColor: Color = Ink.copy(alpha = 0.05f),
    shadowOffset: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(corner)
    Box(modifier) {
        if (shadowOffset > 0.dp) {
            Box(
                Modifier
                    .matchParentSize()
                    .offset(y = shadowOffset)
                    .background(shadowColor, shape)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(color)
                .border(borderWidth, borderColor, shape)
                .padding(contentPadding),
            content = content
        )
    }
}

@Composable
fun ChunkyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ChunkyStyle = ChunkyStyle.PRIMARY,
    leadingIcon: GardenIconKind? = null,
    enabled: Boolean = true,
    height: Dp = when (style) {
        ChunkyStyle.OUTLINE -> 43.dp
        else -> 50.dp
    }
) {
    val shape = RoundedCornerShape(15.dp)
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val pressOffset by animateDpAsState(
        targetValue = if (pressed && enabled) 3.dp else 0.dp,
        animationSpec = tween(90),
        label = "pressOffset"
    )

    val background = when (style) {
        ChunkyStyle.PRIMARY -> Leaf
        ChunkyStyle.SOFT -> LeafSoft
        ChunkyStyle.OUTLINE -> Paper.copy(alpha = 0.65f)
        ChunkyStyle.DARK -> LeafDark
    }
    val border = when (style) {
        ChunkyStyle.PRIMARY -> LeafDark
        ChunkyStyle.SOFT -> Color(0xFFB9D5B8)
        ChunkyStyle.OUTLINE -> Color(0xFFCFD9C4)
        ChunkyStyle.DARK -> LeafDark
    }
    val shadow = when (style) {
        ChunkyStyle.PRIMARY -> LeafDark
        ChunkyStyle.SOFT -> Color(0xFFC5DCC5)
        ChunkyStyle.OUTLINE -> Color.Transparent
        ChunkyStyle.DARK -> Color(0xFF143924).copy(alpha = 0.2f)
    }
    val foreground = when (style) {
        ChunkyStyle.PRIMARY, ChunkyStyle.DARK -> Paper
        ChunkyStyle.SOFT -> LeafDark
        ChunkyStyle.OUTLINE -> InkSoft
    }
    val shadowOffset = when (style) {
        ChunkyStyle.PRIMARY -> 4.dp
        ChunkyStyle.SOFT -> 3.dp
        ChunkyStyle.OUTLINE -> 0.dp
        ChunkyStyle.DARK -> 5.dp
    }

    Box(modifier.height(height)) {
        if (shadowOffset > 0.dp) {
            Box(
                Modifier
                    .matchParentSize()
                    .offset(y = shadowOffset)
                    .background(shadow, shape)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = pressOffset)
                .clip(shape)
                .background(background)
                .border(1.5.dp, border, shape)
                .clickable(
                    interactionSource = interaction,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick
                )
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                GardenIcon(leadingIcon, Modifier.size(17.dp), tint = foreground)
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = GardenText.button,
                color = foreground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun GardenSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val background by animateColorAsState(
        targetValue = if (checked) Leaf else Color(0xFFCBD8C3),
        animationSpec = tween(200),
        label = "switchBackground"
    )
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 17.dp else 0.dp,
        animationSpec = tween(200),
        label = "switchThumb"
    )
    Box(
        modifier = modifier
            .width(42.dp)
            .height(25.dp)
            .clip(CircleShape)
            .background(background)
            .toggleable(
                value = checked,
                role = Role.Switch,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onValueChange = onCheckedChange
            )
    ) {
        Box(
            Modifier
                .padding(start = 3.dp, top = 3.dp)
                .offset(x = thumbOffset)
                .size(19.dp)
                .clip(CircleShape)
                .background(Paper)
        )
    }
}

@Composable
fun StreakPill(
    days: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(42.dp)
            .clip(CircleShape)
            .background(Paper)
            .border(1.5.dp, Color(0xFFD8E5CA), CircleShape)
            .padding(start = 8.dp, end = 11.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GardenIcon(
            GardenIconKind.FLOWER_FILLED,
            Modifier.size(20.dp),
            tint = Sun
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = days.toString(),
            style = GardenText.streakNumber,
            color = LeafDark
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "days",
            style = GardenText.micro,
            color = Color(0xFF7F917F)
        )
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: GardenIconKind = GardenIconKind.BACK,
    description: String = "Back"
) {
    val shape = RoundedCornerShape(12.dp)
    Box(modifier.size(38.dp)) {
        Box(
            Modifier
                .matchParentSize()
                .offset(y = 2.dp)
                .background(Ink.copy(alpha = 0.07f), shape)
        )
        Box(
            Modifier
                .fillMaxSize()
                .clip(shape)
                .background(Paper)
                .border(1.5.dp, Color(0xFFCFD9C4), shape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Button,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            GardenIcon(icon, Modifier.size(17.dp), tint = LeafDark)
        }
    }
}

@Composable
fun GardenTabBar(
    active: GardenScreen,
    onToday: () -> Unit,
    onOpenWeek: () -> Unit,
    onHistory: () -> Unit,
    onLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Cream.copy(alpha = 0.95f),
                        Cream
                    )
                )
            )
            .navigationBarsPadding()
            .padding(start = 30.dp, end = 30.dp, top = 14.dp, bottom = 12.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            GardenTab(
                label = "Today",
                icon = GardenIconKind.SUN,
                selected = active == GardenScreen.TODAY,
                onClick = onToday,
                modifier = Modifier.weight(1f)
            )
            GardenTab(
                label = "Learn",
                icon = GardenIconKind.FLOWER_FILLED,
                selected = active == GardenScreen.LEARN || active == GardenScreen.LESSON,
                onClick = onLearn,
                modifier = Modifier.weight(1f)
            )
            GardenTab(
                label = "Week",
                icon = GardenIconKind.FLOWER,
                selected = active == GardenScreen.WEEK,
                onClick = onOpenWeek,
                modifier = Modifier.weight(1f)
            )
            GardenTab(
                label = "History",
                icon = GardenIconKind.LEAF,
                selected = active == GardenScreen.HISTORY,
                onClick = onHistory,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun GardenTab(
    label: String,
    icon: GardenIconKind,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    val background by animateColorAsState(
        targetValue = if (selected) Color(0xFFEEF6E8) else Color.Transparent,
        animationSpec = tween(180),
        label = "tabBackground"
    )
    val foreground = if (selected) LeafDark else Color(0xFF90A092)
    Column(
        modifier = modifier
            .height(52.dp)
            .clip(shape)
            .background(background)
            .border(
                width = 1.dp,
                color = if (selected) Color(0xFFD6E6CD) else Color.Transparent,
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Tab,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GardenIcon(icon, Modifier.size(19.dp), tint = foreground, strokeWidth = 1.8f)
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            style = GardenText.tabLabel,
            color = foreground
        )
    }
}

enum class GardenScreen { ONBOARDING, TODAY, LEARN, SETUP, MISSED, WEEK, HISTORY, LESSON }

fun Modifier.gardenDashedBorder(
    color: Color,
    width: Dp = 1.5.dp,
    radius: Dp = 14.dp
): Modifier = this.drawBehind {
    val inset = width.toPx() / 2f
    drawRoundRect(
        color = color,
        topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
        size = androidx.compose.ui.geometry.Size(size.width - width.toPx(), size.height - width.toPx()),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(radius.toPx(), radius.toPx()),
        style = androidx.compose.ui.graphics.drawscope.Stroke(
            width = width.toPx(),
            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                floatArrayOf(6.dp.toPx(), 4.dp.toPx())
            )
        )
    )
}
