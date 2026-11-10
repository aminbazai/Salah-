package com.salahgarden.app

import com.salahgarden.app.data.Prayer
import com.salahgarden.app.ui.SalahHadiths
import com.salahgarden.app.ui.hadithIndexFor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class HadithBankTest {

    @Test
    fun bankHasEnoughAhadithForRotation() {
        assertTrue("Hadith bank should rotate, not repeat", SalahHadiths.size >= 8)
    }

    @Test
    fun everyHadithHasTextThemeAndSourcedReference() {
        SalahHadiths.forEach { hadith ->
            assertTrue("Missing hadith text", hadith.text.isNotBlank())
            assertTrue("Missing theme", hadith.theme.isNotBlank())
            assertTrue("Missing source", hadith.source.isNotBlank())
            assertTrue(
                "Source should include a collection number: ${hadith.source}",
                hadith.source.any { it.isDigit() }
            )
        }
    }

    @Test
    fun hadithTextsAreUnique() {
        assertEquals(
            SalahHadiths.size,
            SalahHadiths.map { it.text }.distinct().size
        )
    }

    @Test
    fun eachPrayerGetsADifferentHadithOnTheSameDay() {
        val day = LocalDate.of(2026, 11, 9).toEpochDay()
        val picks = Prayer.ordered.map { hadithIndexFor(day, it.ordinal) }
        assertEquals(Prayer.ordered.size, picks.distinct().size)
    }

    @Test
    fun consecutiveDaysWalkTheWholeBank() {
        val start = LocalDate.of(2026, 11, 9).toEpochDay()
        val picks = (0 until SalahHadiths.size).map { offset ->
            hadithIndexFor(start + offset, prayerOrdinal = 0)
        }
        assertEquals(SalahHadiths.size, picks.distinct().size)
        assertTrue(picks.all { it in SalahHadiths.indices })
    }
}
