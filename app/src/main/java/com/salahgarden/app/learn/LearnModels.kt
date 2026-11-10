package com.salahgarden.app.learn

/**
 * Content model for the Learn Salah guide.
 *
 * Content is data, not UI: every lesson is a list of [Block]s, so a scholar can
 * review and edit text without touching composables. Arabic strings are UTF-8
 * and must be proof-read against a printed Mushaf before release.
 */

/** One Arabic recitation with its three layers. */
data class Recitation(
    val id: String,
    val arabic: String,
    val translit: String,
    val meaningEn: String,
    val meaningUr: String = "",
    /** Placeholder path; the audio row stays hidden until the file exists. */
    val audioFile: String? = null,
    val source: String? = null
)

/** A prayer position inside the "pray along" walkthrough. */
data class Position(
    val id: String,
    val nameEn: String,
    val nameUr: String,
    val recitationIds: List<String> = emptyList(),
    val hands: String = "",
    val eyes: String = "",
    val feet: String = "",
    val tip: String = "",
    /** Placeholder illustration; replace with real SVG/PNG assets later. */
    val image: String? = null
)

/** One rakat unit inside a prayer (fard / sunnah / nafl / witr). */
data class RakatUnit(
    val type: String,
    val rakat: Int,
    val wajib: Boolean = false
)

/** A prayer's full structure, used to build the walkthrough sequence. */
data class PrayerSpec(
    val id: String,
    val nameEn: String,
    val nameUr: String,
    val timeWindowKey: String,
    val units: List<RakatUnit>
)

sealed interface Block {
    data class Heading(val textEn: String, val textUr: String = "") : Block
    data class Text(val textEn: String, val textUr: String = "") : Block
    data class Steps(val items: List<String>, val itemsUr: List<String> = emptyList()) : Block
    data class Callout(val titleEn: String, val textEn: String, val titleUr: String = "", val textUr: String = "") : Block
    data class RecitationBlock(val recitation: Recitation) : Block
    data class DifferenceNote(val titleEn: String, val textEn: String, val titleUr: String = "", val textUr: String = "") : Block
    data class Table(val headers: List<String>, val headersUr: List<String> = emptyList(), val rows: List<List<String>>, val rowsUr: List<List<String>> = emptyList()) : Block
    data class Image(val slot: String, val captionEn: String = "", val captionUr: String = "") : Block
}

data class QuizQuestion(
    val questionEn: String,
    val questionUr: String,
    val optionsEn: List<String>,
    val optionsUr: List<String> = emptyList(),
    val correctIndex: Int
)

data class Lesson(
    val id: String,
    val order: Int,
    val titleEn: String,
    val titleUr: String,
    val summaryEn: String,
    val summaryUr: String = "",
    val blocks: List<Block>,
    val quiz: List<QuizQuestion> = emptyList()
)

/** Per-lesson progress, stored locally. */
data class LessonProgress(
    val lessonId: String,
    val completed: Boolean = false,
    val lastBlockIndex: Int = 0,
    val quizScore: Int = -1
)

/** Flashcard memorisation state for a recitation. */
data class FlashcardState(
    val recitationId: String,
    val known: Boolean = false,
    val repetitions: Int = 0,
    val nextDueDay: Long = 0L
)

/** Which of the three Arabic layers the reader wants to see. */
data class LayerVisibility(
    val arabic: Boolean = true,
    val translit: Boolean = true,
    val meaning: Boolean = true
)
