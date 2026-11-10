package com.salahgarden.app.learn

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

/**
 * Local-only progress for the Learn Salah guide.
 *
 * Stores lesson completion, last block, quiz scores, flashcard state and
 * bookmarks in SharedPreferences. Nothing leaves the phone.
 */
class LearnSalahRepository(context: Context) {

    private val prefs = context.getSharedPreferences("learn_salah", Context.MODE_PRIVATE)

    // ---- Lessons ----

    fun completedLessons(): Set<String> = prefs.getStringSet(KEY_COMPLETED, emptySet()) ?: emptySet()

    fun isCompleted(lessonId: String): Boolean = lessonId in completedLessons()

    fun setCompleted(lessonId: String, completed: Boolean) {
        val current = completedLessons().toMutableSet()
        if (completed) current.add(lessonId) else current.remove(lessonId)
        prefs.edit().putStringSet(KEY_COMPLETED, current).apply()
    }

    fun lastBlock(lessonId: String): Int = prefs.getInt("$KEY_LAST_BLOCK:$lessonId", 0)

    fun setLastBlock(lessonId: String, index: Int) {
        prefs.edit().putInt("$KEY_LAST_BLOCK:$lessonId", index).apply()
    }

    fun quizScore(lessonId: String): Int = prefs.getInt("$KEY_QUIZ:$lessonId", -1)

    fun setQuizScore(lessonId: String, score: Int) {
        prefs.edit().putInt("$KEY_QUIZ:$lessonId", score).apply()
    }

    /** Completed count, then total. */
    fun progressCounts(all: List<Lesson>): Pair<Int, Int> =
        all.count { it.id in completedLessons() } to all.size

    // ---- Flashcards ----

    fun flashcard(recitationId: String): FlashcardState {
        val raw = prefs.getString("$KEY_CARD:$recitationId", null) ?: return FlashcardState(recitationId)
        return runCatching {
            val o = JSONObject(raw)
            FlashcardState(
                recitationId = recitationId,
                known = o.optBoolean("known"),
                repetitions = o.optInt("repetitions"),
                nextDueDay = o.optLong("nextDueDay")
            )
        }.getOrDefault(FlashcardState(recitationId))
    }

    fun saveFlashcard(state: FlashcardState) {
        val o = JSONObject()
            .put("known", state.known)
            .put("repetitions", state.repetitions)
            .put("nextDueDay", state.nextDueDay)
        prefs.edit().putString("$KEY_CARD:${state.recitationId}", o.toString()).apply()
    }

    /**
     * Mark a card "known": bump repetitions and push the next review a little
     * further out (1 day, then 3, then 7). Local spaced repetition only.
     */
    fun markKnown(recitationId: String, today: LocalDate = LocalDate.now()): FlashcardState {
        val current = flashcard(recitationId)
        val reps = current.repetitions + 1
        val interval = when {
            reps <= 1 -> 1L
            reps == 2 -> 3L
            else -> 7L
        }
        val next = current.copy(
            known = true,
            repetitions = reps,
            nextDueDay = today.plusDays(interval).toEpochDay()
        )
        saveFlashcard(next)
        return next
    }

    fun markUnknown(recitationId: String): FlashcardState {
        val next = flashcard(recitationId).copy(known = false, repetitions = 0, nextDueDay = 0L)
        saveFlashcard(next)
        return next
    }

    fun allFlashcardIds(): List<String> =
        LearnRecitations.all.keys.toList()

    /** Cards due today (or overdue), for the memorisation shortcut. */
    fun dueCards(today: LocalDate = LocalDate.now()): List<String> {
        val todayEpoch = today.toEpochDay()
        return allFlashcardIds().filter { id ->
            val card = flashcard(id)
            card.nextDueDay <= todayEpoch
        }
    }

    // ---- Bookmarks ----

    fun bookmarks(): Set<String> = prefs.getStringSet(KEY_BOOKMARKS, emptySet()) ?: emptySet()

    fun toggleBookmark(lessonId: String): Boolean {
        val current = bookmarks().toMutableSet()
        val added = if (lessonId in current) {
            current.remove(lessonId); false
        } else {
            current.add(lessonId); true
        }
        prefs.edit().putStringSet(KEY_BOOKMARKS, current.toSet()).apply()
        return added
    }

    fun isBookmarked(lessonId: String): Boolean = lessonId in bookmarks()

    // ---- Reader settings ----

    var showArabic: Boolean
        get() = prefs.getBoolean("$KEY_LAYER:arabic", true)
        set(value) = prefs.edit().putBoolean("$KEY_LAYER:arabic", value).apply()

    var showTranslit: Boolean
        get() = prefs.getBoolean("$KEY_LAYER:translit", true)
        set(value) = prefs.edit().putBoolean("$KEY_LAYER:translit", value).apply()

    var showMeaning: Boolean
        get() = prefs.getBoolean("$KEY_LAYER:meaning", true)
        set(value) = prefs.edit().putBoolean("$KEY_LAYER:meaning", value).apply()

    var fontScale: Float
        get() = prefs.getFloat(KEY_FONT_SCALE, 1f)
        set(value) = prefs.edit().putFloat(KEY_FONT_SCALE, value.coerceIn(0.85f, 1.4f)).apply()

    /** Empty until a qualified scholar signs off. */
    val reviewedBy: String
        get() = prefs.getString(KEY_REVIEWED_BY, null) ?: ""

    fun setReviewedBy(name: String) {
        prefs.edit().putString(KEY_REVIEWED_BY, name).apply()
    }

    companion object {
        private const val KEY_COMPLETED = "completed_lessons"
        private const val KEY_LAST_BLOCK = "last_block"
        private const val KEY_QUIZ = "quiz_score"
        private const val KEY_CARD = "card"
        private const val KEY_BOOKMARKS = "bookmarks"
        private const val KEY_LAYER = "layer"
        private const val KEY_FONT_SCALE = "font_scale"
        private const val KEY_REVIEWED_BY = "reviewed_by"
    }
}
