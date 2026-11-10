package com.salahgarden.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salahgarden.app.learn.Block
import com.salahgarden.app.learn.LearnRecitations
import com.salahgarden.app.learn.LearnSalahRepository
import com.salahgarden.app.learn.Lesson
import com.salahgarden.app.learn.QuizQuestion
import com.salahgarden.app.ui.theme.ArabicFamily
import com.salahgarden.app.ui.theme.Coral
import com.salahgarden.app.ui.theme.GardenText
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.LeafSoft
import com.salahgarden.app.ui.theme.Line
import com.salahgarden.app.ui.theme.Mint
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.Sun
import com.salahgarden.app.ui.theme.UrduFamily

@Composable
fun LessonDetail(
    lesson: Lesson,
    repository: LearnSalahRepository,
    onBack: () -> Unit
) {
    val completed = repository.isCompleted(lesson.id)
    val quizScore = repository.quizScore(lesson.id)
    var showQuiz by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 116.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onClick = onBack, description = "Back to lessons")
            Spacer(Modifier.weight(1f))
            Text(
                text = "LESSON ${lesson.order} OF ${AllLearnLessons.size}",
                style = GardenText.microUppercase,
                color = Color(0xFF859587)
            )
        }

        Text(
            text = lesson.titleEn,
            style = GardenText.screenTitle.copy(fontSize = 27.sp),
            color = LeafDark
        )
        Spacer(Modifier.height(3.dp))
        Text(
            text = lesson.titleUr,
            fontFamily = UrduFamily,
            fontSize = 24.sp,
            lineHeight = 34.sp,
            color = Color(0xFF4F6B58)
        )
        Spacer(Modifier.height(7.dp))
        Text(
            text = lesson.summaryEn,
            style = GardenText.body.copy(fontSize = 11.8.sp, lineHeight = 17.5.sp),
            color = Color(0xFF687F6E)
        )
        if (lesson.summaryUr.isNotBlank()) {
            Spacer(Modifier.height(5.dp))
            Text(
                text = lesson.summaryUr,
                fontFamily = UrduFamily,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = Color(0xFF5B7563)
            )
        }

        Spacer(Modifier.height(14.dp))
        lesson.blocks.forEach { block ->
            BlockView(block = block, repository = repository)
            Spacer(Modifier.height(11.dp))
        }

        // Quiz
        if (lesson.quiz.isNotEmpty()) {
            Spacer(Modifier.height(4.dp))
            GardenCard(
                modifier = Modifier.fillMaxWidth(),
                corner = 20.dp,
                contentPadding = PaddingValues(15.dp)
            ) {
                Column {
                    Text(
                        text = "Check your understanding",
                        style = GardenText.cardTitle,
                        color = LeafDark
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = "${lesson.quiz.size} short questions.",
                        style = GardenText.body.copy(fontSize = 10.5.sp, lineHeight = 15.sp),
                        color = Color(0xFF819183)
                    )
                    Spacer(Modifier.height(10.dp))
                    if (quizScore >= 0) {
                        Text(
                            text = "Your last score: $quizScore / ${lesson.quiz.size}",
                            style = GardenText.body.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 15.sp
                            ),
                            color = if (quizScore == lesson.quiz.size) Leaf else Color(0xFFB0712F)
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    ChunkyButton(
                        text = if (showQuiz) "Hide quiz" else "Take the quiz",
                        onClick = { showQuiz = !showQuiz },
                        style = if (showQuiz) ChunkyStyle.OUTLINE else ChunkyStyle.PRIMARY,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (showQuiz) {
                        Spacer(Modifier.height(12.dp))
                        QuizView(
                            questions = lesson.quiz,
                            onFinished = { score -> repository.setQuizScore(lesson.id, score) }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        ChunkyButton(
            text = if (completed) "Marked complete ✓" else "Mark lesson complete",
            onClick = { repository.setCompleted(lesson.id, !completed) },
            style = if (completed) ChunkyStyle.SOFT else ChunkyStyle.PRIMARY,
            leadingIcon = if (completed) GardenIconKind.CHECK else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))
        Text(
            text = "Arabic text must be proof-read against a printed Mushaf and reviewed by a qualified scholar before release.",
            style = GardenText.body.copy(fontSize = 9.5.sp, lineHeight = 13.5.sp),
            color = Color(0xFF819183),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun BlockView(
    block: Block,
    repository: LearnSalahRepository
) {
    when (block) {
        is Block.Heading -> {
            Text(
                text = block.textEn,
                style = GardenText.cardTitle.copy(fontSize = 17.sp, lineHeight = 21.sp),
                color = LeafDark
            )
            if (block.textUr.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = block.textUr,
                    fontFamily = UrduFamily,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF647B69)
                )
            }
        }

        is Block.Text -> {
            Text(
                text = block.textEn,
                style = GardenText.body.copy(fontSize = 11.8.sp, lineHeight = 18.sp),
                color = Color(0xFF3C5545)
            )
            if (block.textUr.isNotBlank()) {
                Spacer(Modifier.height(5.dp))
                Text(
                    text = block.textUr,
                    fontFamily = UrduFamily,
                    fontSize = 14.sp,
                    lineHeight = 23.sp,
                    color = Color(0xFF5B7563)
                )
            }
        }

        is Block.Steps -> {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                block.items.forEachIndexed { index, item ->
                    Row {
                        Box(
                            Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(LeafSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (index + 1).toString(),
                                style = GardenText.body.copy(
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = LeafDark
                            )
                        }
                        Spacer(Modifier.width(9.dp))
                        Text(
                            text = item,
                            style = GardenText.body.copy(fontSize = 11.5.sp, lineHeight = 17.sp),
                            color = Color(0xFF3C5545),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (block.itemsUr.isNotEmpty() && index < block.itemsUr.size) {
                        Text(
                            text = block.itemsUr[index],
                            fontFamily = UrduFamily,
                            fontSize = 13.5.sp,
                            lineHeight = 22.sp,
                            color = Color(0xFF647B69),
                            modifier = Modifier.padding(start = 31.dp, top = 3.dp)
                        )
                    }
                }
            }
        }

        is Block.Callout -> {
            GardenCard(
                modifier = Modifier.fillMaxWidth(),
                color = LeafSoft.copy(alpha = 0.65f),
                corner = 16.dp,
                contentPadding = PaddingValues(13.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    GardenIcon(GardenIconKind.LEAF, Modifier.size(16.dp), tint = Leaf)
                    Spacer(Modifier.width(9.dp))
                    Column {
                        Text(
                            text = block.titleEn,
                            style = GardenText.body.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 15.sp
                            ),
                            color = LeafDark
                        )
                        if (block.titleUr.isNotBlank()) {
                            Text(
                                text = block.titleUr,
                                fontFamily = UrduFamily,
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                color = Color(0xFF4F6B58)
                            )
                        }
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = block.textEn,
                            style = GardenText.body.copy(fontSize = 11.sp, lineHeight = 16.sp),
                            color = Color(0xFF4F6B58)
                        )
                        if (block.textUr.isNotBlank()) {
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = block.textUr,
                                fontFamily = UrduFamily,
                                fontSize = 13.sp,
                                lineHeight = 22.sp,
                                color = Color(0xFF5B7563)
                            )
                        }
                    }
                }
            }
        }

        is Block.RecitationBlock -> {
            RecitationCard(recitation = block.recitation, repository = repository)
        }

        is Block.DifferenceNote -> {
            GardenCard(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFFF5DF),
                borderColor = Color(0xFFF2DFB4),
                corner = 16.dp,
                contentPadding = PaddingValues(13.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    GardenIcon(GardenIconKind.LEAF, Modifier.size(16.dp), tint = Color(0xFFB77B22))
                    Spacer(Modifier.width(9.dp))
                    Column {
                        Text(
                            text = block.titleEn,
                            style = GardenText.body.copy(
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 14.sp
                            ),
                            color = Color(0xFF8A5E14)
                        )
                        if (block.titleUr.isNotBlank()) {
                            Text(
                                text = block.titleUr,
                                fontFamily = UrduFamily,
                                fontSize = 12.5.sp,
                                lineHeight = 19.sp,
                                color = Color(0xFF8A5E14)
                            )
                        }
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = block.textEn,
                            style = GardenText.body.copy(fontSize = 10.8.sp, lineHeight = 16.sp),
                            color = Color(0xFF6B5320)
                        )
                        if (block.textUr.isNotBlank()) {
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = block.textUr,
                                fontFamily = UrduFamily,
                                fontSize = 13.sp,
                                lineHeight = 22.sp,
                                color = Color(0xFF6B5320)
                            )
                        }
                    }
                }
            }
        }

        is Block.Table -> {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, Line, RoundedCornerShape(14.dp))
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(LeafSoft)
                        .padding(horizontal = 10.dp, vertical = 9.dp)
                ) {
                    block.headers.forEachIndexed { index, header ->
                        Text(
                            text = header,
                            style = GardenText.body.copy(
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 13.sp
                            ),
                            color = LeafDark,
                            modifier = Modifier.weight(if (index == 0) 1.1f else 1f)
                        )
                    }
                }
                block.rows.forEachIndexed { rowIndex, row ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(if (rowIndex % 2 == 0) Paper else Color(0xFFFAF7EC))
                            .padding(horizontal = 10.dp, vertical = 9.dp)
                    ) {
                        row.forEachIndexed { index, cell ->
                            Text(
                                text = cell,
                                style = GardenText.body.copy(fontSize = 10.sp, lineHeight = 14.5.sp),
                                color = Color(0xFF3C5545),
                                modifier = Modifier.weight(if (index == 0) 1.1f else 1f)
                            )
                        }
                    }
                }
            }
        }

        is Block.Image -> {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(LeafSoft.copy(alpha = 0.5f))
                    .border(1.dp, Color(0xFFCFE3CF), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    GardenIcon(GardenIconKind.FLOWER, Modifier.size(26.dp), tint = Color(0xFF9BB79F))
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = block.slot.ifBlank { "illustration placeholder" },
                        style = GardenText.body.copy(fontSize = 9.5.sp),
                        color = Color(0xFF819183)
                    )
                    if (block.captionEn.isNotBlank()) {
                        Text(
                            text = block.captionEn,
                            style = GardenText.body.copy(fontSize = 9.sp),
                            color = Color(0xFF9AA79A)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecitationCard(
    recitation: com.salahgarden.app.learn.Recitation,
    repository: LearnSalahRepository
) {
    var showArabic by remember { mutableStateOf(repository.showArabic) }
    var showTranslit by remember { mutableStateOf(repository.showTranslit) }
    var showMeaning by remember { mutableStateOf(repository.showMeaning) }

    GardenCard(
        modifier = Modifier.fillMaxWidth(),
        color = Leaf,
        borderColor = LeafDark,
        borderWidth = 1.5.dp,
        corner = 20.dp,
        shadowColor = LeafDark.copy(alpha = 0.18f),
        shadowOffset = 4.dp,
        contentPadding = PaddingValues(14.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "RECITATION",
                    style = GardenText.micro.copy(
                        fontSize = 8.sp,
                        letterSpacing = 0.8.sp,
                        color = Color(0xFFC7DFC6)
                    ),
                    modifier = Modifier.weight(1f)
                )
                if (recitation.source != null) {
                    Text(
                        text = recitation.source,
                        style = GardenText.body.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                        color = Paper
                    )
                }
            }

            Spacer(Modifier.height(9.dp))

            if (showArabic && recitation.arabic.isNotBlank()) {
                Text(
                    text = recitation.arabic,
                    fontFamily = ArabicFamily,
                    fontSize = 22.sp,
                    lineHeight = 38.sp,
                    color = Paper,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
            }

            if (showTranslit && recitation.translit.isNotBlank()) {
                Text(
                    text = recitation.translit,
                    style = GardenText.body.copy(
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 17.sp
                    ),
                    color = Color(0xFFE8F3E4)
                )
                Spacer(Modifier.height(7.dp))
            }

            if (showMeaning && (recitation.meaningEn.isNotBlank() || recitation.meaningUr.isNotBlank())) {
                if (recitation.meaningEn.isNotBlank()) {
                    Text(
                        text = recitation.meaningEn,
                        style = GardenText.body.copy(fontSize = 10.8.sp, lineHeight = 16.sp),
                        color = Color(0xFFDCEBD8)
                    )
                }
                if (recitation.meaningUr.isNotBlank()) {
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = recitation.meaningUr,
                        fontFamily = UrduFamily,
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        color = Color(0xFFDCEBD8)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                LayerToggle(
                    label = "Arabic",
                    active = showArabic,
                    onClick = {
                        showArabic = !showArabic
                        repository.showArabic = showArabic
                    }
                )
                LayerToggle(
                    label = "Translit",
                    active = showTranslit,
                    onClick = {
                        showTranslit = !showTranslit
                        repository.showTranslit = showTranslit
                    }
                )
                LayerToggle(
                    label = "Meaning",
                    active = showMeaning,
                    onClick = {
                        showMeaning = !showMeaning
                        repository.showMeaning = showMeaning
                    }
                )
            }
        }
    }
}

@Composable
private fun LayerToggle(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (active) Paper.copy(alpha = 0.22f) else Color.Transparent)
            .border(
                1.dp,
                if (active) Paper.copy(alpha = 0.6f) else Color(0x33FFFFFF),
                RoundedCornerShape(999.dp)
            )
            .clickable(
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            style = GardenText.body.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
            color = if (active) Paper else Color(0xFFC7DFC6)
        )
    }
}

@Composable
private fun QuizView(
    questions: List<QuizQuestion>,
    onFinished: (Int) -> Unit
) {
    var index by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var picked by remember { mutableStateOf<Int?>(null) }
    val question = questions[index]

    Column {
        Text(
            text = "Question ${index + 1} of ${questions.size}",
            style = GardenText.micro.copy(fontSize = 8.5.sp, letterSpacing = 0.6.sp),
            color = Color(0xFF8B9A8C)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = question.questionEn,
            style = GardenText.cardTitle.copy(fontSize = 14.5.sp, lineHeight = 19.sp),
            color = LeafDark
        )
        if (question.questionUr.isNotBlank()) {
            Spacer(Modifier.height(2.dp))
            Text(
                text = question.questionUr,
                fontFamily = UrduFamily,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = Color(0xFF647B69)
            )
        }
        Spacer(Modifier.height(10.dp))
        question.optionsEn.forEachIndexed { optionIndex, option ->
            val selected = picked == optionIndex
            val correct = picked != null && optionIndex == question.correctIndex
            val wrong = picked != null && selected && optionIndex != question.correctIndex
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        when {
                            correct -> Mint
                            wrong -> Color(0xFFFBE3DB)
                            selected -> LeafSoft
                            else -> Paper
                        }
                    )
                    .border(
                        1.dp,
                        when {
                            correct -> Leaf
                            wrong -> Coral
                            selected -> Leaf
                            else -> Line
                        },
                        RoundedCornerShape(12.dp)
                    )
                    .clickable(
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                        indication = null,
                        role = Role.Button,
                        onClick = { picked = optionIndex }
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = option,
                    style = GardenText.body.copy(fontSize = 11.sp, lineHeight = 15.5.sp),
                    color = Color(0xFF3C5545),
                    modifier = Modifier.weight(1f)
                )
                if (correct) {
                    GardenIcon(GardenIconKind.CHECK, Modifier.size(15.dp), tint = Leaf)
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        ChunkyButton(
            text = when {
                picked == null -> "Choose an answer"
                index < questions.lastIndex -> "Next question"
                else -> "Finish"
            },
            onClick = {
                val gained = if (picked == question.correctIndex) 1 else 0
                val newScore = score + gained
                if (index < questions.lastIndex) {
                    index += 1
                    score = newScore
                    picked = null
                } else {
                    onFinished(newScore)
                }
            },
            enabled = picked != null,
            style = if (picked == null) ChunkyStyle.OUTLINE else ChunkyStyle.PRIMARY,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
