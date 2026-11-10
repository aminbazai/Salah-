package com.salahgarden.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.salahgarden.app.R

val Cream = Color(0xFFFFF9EC)
val Cream2 = Color(0xFFF6EFDF)
val Paper = Color(0xFFFFFDF7)
val Ink = Color(0xFF1F3D2C)
val InkSoft = Color(0xFF5E7466)
val InkFaint = Color(0xFF8E9B8D)
val Line = Color(0xFFDCE0CD)
val Leaf = Color(0xFF2F7D50)
val LeafDark = Color(0xFF1F5B3A)
val LeafSoft = Color(0xFFE5F2E6)
val Mint = Color(0xFFD9EFDF)
val Sun = Color(0xFFF5B335)
val SunSoft = Color(0xFFFFE7AD)
val Sky = Color(0xFFCFEAF4)
val Sky2 = Color(0xFFE9F7FB)
val Coral = Color(0xFFE87358)
val Plum = Color(0xFF8A6A9D)
val Pot = Color(0xFFD8835F)
val PotRim = Color(0xFFE99A74)

val DisplayFamily = FontFamily(
    Font(R.font.baloo2_regular, FontWeight.Normal),
    Font(R.font.baloo2_semibold, FontWeight.SemiBold),
    Font(R.font.baloo2_bold, FontWeight.Bold),
    Font(R.font.baloo2_extrabold, FontWeight.ExtraBold)
)

val BodyFamily = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal),
    Font(R.font.karla_medium, FontWeight.Medium),
    Font(R.font.karla_semibold, FontWeight.SemiBold),
    Font(R.font.karla_bold, FontWeight.Bold)
)

/** Arabic (Naskh) for Quran and duas. Open-licensed Noto Naskh Arabic. */
val ArabicFamily = FontFamily(
    Font(R.font.noto_naskh_arabic, FontWeight.Normal),
    Font(R.font.noto_naskh_arabic, FontWeight.Medium),
    Font(R.font.noto_naskh_arabic, FontWeight.SemiBold),
    Font(R.font.noto_naskh_arabic, FontWeight.Bold)
)

/** Urdu (Nastaliq) for translations. Open-licensed Noto Nastaliq Urdu. */
val UrduFamily = FontFamily(
    Font(R.font.noto_nastaliq_urdu, FontWeight.Normal),
    Font(R.font.noto_nastaliq_urdu, FontWeight.Medium),
    Font(R.font.noto_nastaliq_urdu, FontWeight.SemiBold),
    Font(R.font.noto_nastaliq_urdu, FontWeight.Bold)
)

object GardenText {
    val hero = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 33.sp,
        lineHeight = 33.sp,
        letterSpacing = (-1).sp,
        color = Ink
    )
    val screenTitle = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 31.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.9).sp,
        color = Ink
    )
    val celebrationTitle = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 33.sp,
        lineHeight = 34.sp,
        letterSpacing = (-1).sp,
        color = Ink
    )
    val quote = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 25.sp,
        letterSpacing = (-0.4).sp,
        color = Paper
    )
    val cardTitle = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 18.sp,
        color = Ink
    )
    val streakNumber = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
        lineHeight = 20.sp,
        color = Ink
    )
    val bigNumber = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 36.sp,
        letterSpacing = (-1.2).sp,
        color = Paper
    )
    val footerNumber = TextStyle(
        fontFamily = DisplayFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.4).sp,
        color = Ink
    )
    val button = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.5.sp,
        lineHeight = 18.sp
    )
    val buttonSmall = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.5.sp,
        lineHeight = 15.sp
    )
    val body = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 17.5.sp,
        color = Ink
    )
    val bodySmall = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.5.sp,
        lineHeight = 16.5.sp,
        color = InkSoft
    )
    val bodyTiny = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        color = InkSoft
    )
    val caption = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 9.5.sp,
        lineHeight = 13.sp,
        color = InkFaint
    )
    val micro = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 8.5.sp,
        lineHeight = 11.sp,
        color = InkFaint
    )
    val microUppercase = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 9.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.9.sp,
        color = InkFaint
    )
    val plantLabel = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 9.5.sp,
        lineHeight = 12.sp,
        color = InkSoft
    )
    val plantTime = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 8.sp,
        lineHeight = 10.sp,
        color = Color(0xFF9AA79A)
    )
    val tabLabel = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 9.5.sp,
        lineHeight = 12.sp
    )
}

private val colorScheme = lightColorScheme(
    primary = Leaf,
    onPrimary = Paper,
    primaryContainer = LeafSoft,
    onPrimaryContainer = LeafDark,
    secondary = Sun,
    onSecondary = Ink,
    secondaryContainer = SunSoft,
    tertiary = Coral,
    background = Cream,
    onBackground = Ink,
    surface = Paper,
    onSurface = Ink,
    surfaceVariant = LeafSoft,
    onSurfaceVariant = InkSoft,
    outline = Line,
    error = Coral
)

private val typography = Typography(
    headlineLarge = GardenText.screenTitle,
    headlineMedium = GardenText.hero,
    titleLarge = GardenText.cardTitle,
    titleMedium = GardenText.footerNumber,
    bodyLarge = GardenText.body,
    bodyMedium = GardenText.bodySmall,
    labelLarge = GardenText.button,
    labelMedium = GardenText.caption
)

@Composable
fun SalahGardenTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
