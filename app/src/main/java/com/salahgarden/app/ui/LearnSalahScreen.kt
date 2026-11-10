package com.salahgarden.app.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.GardenUiState
import com.salahgarden.app.learn.LearnSalahRepository
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.LeafSoft
import com.salahgarden.app.ui.theme.Line
import com.salahgarden.app.ui.theme.Mint
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.UrduFamily

/** All fifteen lessons, in order. Built once; the content tree is large. */
val AllLearnLessons: List<com.salahgarden.app.learn.Lesson> by lazy {
    com.salahgarden.app.learn.LearnLessons.lessons +
        com.salahgarden.app.learn.LearnLessonsPart2.lessons
}

@Composable
fun LearnSalahScreen(
    state: GardenUiState,
    repository: LearnSalahRepository,
    onOpenToday: () -> Unit,
    onOpenWeek: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLessonId by remember { mutableStateOf<String?>(null) }
    val selected = selectedLessonId?.let { id -> AllLearnLessons.firstOrNull { it.id == id } }

    Box(modifier.fillMaxSize()) {
        if (selected == null) {
            LearnHome(
                repository = repository,
                onOpenLesson = { selectedLessonId = it }
            )
        } else {
            LessonDetail(
                lesson = selected,
                repository = repository,
                onBack = { selectedLessonId = null }
            )
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
private fun LearnHome(
    repository: LearnSalahRepository,
    onOpenLesson: (String) -> Unit
) {
    val tapSource = remember { MutableInteractionSource() }
    // Read prefs once per entry, not on every recomposition.
    val completed = remember { repository.completedLessons() }
    val (done, total) = remember(completed) {
        repository.progressCounts(AllLearnLessons)
    }
    val nextLesson = remember(completed) {
        AllLearnLessons.firstOrNull { it.id !in completed }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 112.dp)
    ) {
        item(key = "header") {
            Column {
                Text(
                    text = "LEARN SALAH",
                    style = GardenText.microUppercase,
                    color = Color(0xFF849587)
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "Learn to pray, step by step.",
                    style = GardenText.screenTitle.copy(fontSize = 29.sp),
                    color = LeafDark
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "A beginner's guide that starts from zero. Come back any time and practise.",
                    style = GardenText.body.copy(fontSize = 11.5.sp, lineHeight = 16.5.sp),
                    color = Color(0xFF687F6E),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }
        }

        item(key = "progress") {
            GardenCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                color = Leaf,
                borderColor = LeafDark,
                borderWidth = 1.5.dp,
                corner = 22.dp,
                shadowColor = LeafDark.copy(alpha = 0.25f),
                shadowOffset = 5.dp,
                contentPadding = PaddingValues(15.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ProgressRing(
                        fraction = if (total == 0) 0f else done.toFloat() / total.toFloat(),
                        label = "$done of $total",
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = if (done == 0) "Start here" else "Keep going",
                            style = GardenText.cardTitle.copy(fontSize = 17.sp),
                            color = Paper
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = if (done == 0) {
                                "Begin with Lesson 1, why we pray."
                            } else {
                                "$done of $total lessons complete. ${nextLesson?.titleEn ?: "All done."}"
                            },
                            style = GardenText.body.copy(fontSize = 10.5.sp, lineHeight = 15.sp),
                            color = Color(0xFFDCEBD8)
                        )
                        if (nextLesson != null) {
                            Spacer(Modifier.height(7.dp))
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Paper)
                                    .clickable(
                                        interactionSource = tapSource,
                                        indication = null,
                                        role = Role.Button,
                                        onClick = { onOpenLesson(nextLesson.id) }
                                    )
                                    .padding(horizontal = 11.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Continue: ${nextLesson.titleEn}",
                                    style = GardenText.body.copy(
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = LeafDark
                                )
                            }
                        }
                    }
                }
            }
        }

        item(key = "shortcuts-header") {
            Text(
                text = "QUICK REFERENCE",
                style = GardenText.micro.copy(
                    fontSize = 8.5.sp,
                    letterSpacing = 1.sp,
                    color = Color(0xFF8B9A8C)
                ),
                modifier = Modifier.padding(top = 16.dp, start = 2.dp, bottom = 6.dp)
            )
        }
        item(key = "shortcuts-1") {
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                ShortcutCard(
                    title = "Rakat table",
                    urdu = "رکعتوں کا جدول",
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenLesson("rakat_table") }
                )
                ShortcutCard(
                    title = "Duas to memorise",
                    urdu = "یاد کرنے کی دعائیں",
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenLesson("recitations") }
                )
            }
        }
        item(key = "shortcuts-2") {
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                ShortcutCard(
                    title = "Wudu steps",
                    urdu = "وضو کے اقدام",
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenLesson("taharah") }
                )
                ShortcutCard(
                    title = "Mistakes & Sahw",
                    urdu = "غلطیاں اور سہو",
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenLesson("mistakes_sahw") }
                )
            }
        }

        item(key = "lessons-header") {
            Text(
                text = "LESSONS",
                style = GardenText.micro.copy(
                    fontSize = 8.5.sp,
                    letterSpacing = 1.sp,
                    color = Color(0xFF8B9A8C)
                ),
                modifier = Modifier.padding(top = 18.dp, start = 2.dp, bottom = 6.dp)
            )
        }

        items(AllLearnLessons, key = { it.id }) { lesson ->
            Column {
                LessonRow(
                    lesson = lesson,
                    completed = lesson.id in completed,
                    onClick = { onOpenLesson(lesson.id) }
                )
                Spacer(Modifier.height(6.dp))
            }
        }

        item(key = "review") {
            GardenCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                corner = 16.dp,
                contentPadding = PaddingValues(13.dp)
            ) {
                Column {
                    Text(
                        text = "Content reviewed by",
                        style = GardenText.micro.copy(
                            fontSize = 8.5.sp,
                            letterSpacing = 0.6.sp
                        ),
                        color = Color(0xFF8B9A8C)
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = repository.reviewedBy.ifBlank {
                            "Awaiting scholar review, not yet verified."
                        },
                        style = GardenText.body.copy(fontSize = 11.sp, lineHeight = 15.5.sp),
                        color = if (repository.reviewedBy.isBlank()) {
                            Color(0xFFB0712F)
                        } else {
                            LeafDark
                        }
                    )
                    Spacer(Modifier.height(7.dp))
                    Text(
                        text = "Arabic text must be proof-read against a printed Mushaf and reviewed by a qualified scholar before release.",
                        style = GardenText.body.copy(fontSize = 9.5.sp, lineHeight = 13.5.sp),
                        color = Color(0xFF819183)
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "Report a mistake",
                        style = GardenText.body.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Leaf,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable(
                                interactionSource = tapSource,
                                indication = null,
                                role = Role.Button,
                                onClick = { }
                            )
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ShortcutCard(
    title: String,
    urdu: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Paper)
            .border(1.dp, Line, RoundedCornerShape(15.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 11.dp)
    ) {
        Text(
            text = title,
            style = GardenText.body.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
            color = LeafDark
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = urdu,
            fontFamily = UrduFamily,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = Color(0xFF647B69)
        )
    }
}

@Composable
private fun LessonRow(
    lesson: com.salahgarden.app.learn.Lesson,
    completed: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (completed) Mint.copy(alpha = 0.6f) else Paper)
            .border(
                1.dp,
                if (completed) Color(0xFFCBE3CC) else Line,
                RoundedCornerShape(14.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(if (completed) Leaf else LeafSoft),
            contentAlignment = Alignment.Center
        ) {
            if (completed) {
                GardenIcon(GardenIconKind.CHECK, Modifier.size(15.dp), tint = Paper)
            } else {
                Text(
                    text = lesson.order.toString(),
                    style = GardenText.body.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                    color = LeafDark
                )
            }
        }
        Spacer(Modifier.width(11.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = lesson.titleEn,
                style = GardenText.body.copy(fontSize = 11.5.sp, fontWeight = FontWeight.Bold),
                color = LeafDark
            )
            Text(
                text = lesson.titleUr,
                fontFamily = UrduFamily,
                fontSize = 12.sp,
                lineHeight = 19.sp,
                color = Color(0xFF647B69)
            )
        }
        GardenIcon(GardenIconKind.CHEVRON, Modifier.size(14.dp), tint = Color(0xFF93A092))
    }
}

@Composable
private fun ProgressRing(
    fraction: Float,
    label: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val sweep = 360f * fraction.coerceIn(0f, 1f)
        Canvas(Modifier.fillMaxSize()) {
            val stroke = 7.dp.toPx()
            drawArc(
                color = Color(0x33FFFFFF),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Text(
            text = label,
            style = GardenText.body.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
            color = Paper,
            textAlign = TextAlign.Center
        )
    }
}
