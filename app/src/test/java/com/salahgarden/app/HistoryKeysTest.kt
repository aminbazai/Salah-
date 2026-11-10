package com.salahgarden.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

/**
 * The history feature reads every `day_<date>` key from the preferences and
 * presents the newest first. These tests pin the key contract so a rename or
 * a bad date silently breaks history without anyone noticing.
 */
class HistoryKeysTest {

    private val today: LocalDate = LocalDate.of(2026, 11, 9)

    private fun parseDayKey(key: String): LocalDate? =
        runCatching { LocalDate.parse(key.removePrefix("day_")) }.getOrNull()

    @Test
    fun dayKeysParseToTheirDate() {
        val date = LocalDate.of(2026, 11, 3)
        assertEquals(date, parseDayKey("day_2026-11-03"))
    }

    @Test
    fun nonDayKeysAreIgnored() {
        assertNull(parseDayKey("city_id"))
        assertNull(parseDayKey("reminders"))
        assertNull(parseDayKey("best_run"))
    }

    @Test
    fun malformedDayKeysAreIgnored() {
        assertNull(parseDayKey("day_"))
        assertNull(parseDayKey("day_2026-13-40"))
        assertNull(parseDayKey("day_not-a-date"))
    }

    @Test
    fun futureDaysAreExcluded() {
        val keys = listOf("day_2026-11-09", "day_2026-11-10", "day_2026-11-08")
        val dates = keys.mapNotNull { parseDayKey(it) }.filter { !it.isAfter(today) }
        assertEquals(listOf("2026-11-09", "2026-11-08"), dates.map { it.toString() })
    }

    @Test
    fun historyIsNewestFirst() {
        val keys = listOf("day_2026-11-09", "day_2026-11-08", "day_2026-10-01", "day_2026-11-01")
        val dates = keys.mapNotNull { parseDayKey(it) }.filter { !it.isAfter(today) }.sortedDescending()
        assertEquals(
            listOf("2026-11-09", "2026-11-08", "2026-11-01", "2026-10-01"),
            dates.map { it.toString() }
        )
    }

    @Test
    fun monthLabelAndFullDateLineAreReadable() {
        val date = LocalDate.of(2026, 11, 9)
        assertEquals("NOVEMBER 2026", com.salahgarden.app.ui.monthLabel(date))
        assertEquals("MON · 9 NOV 2026", com.salahgarden.app.ui.fullDateLine(date))
    }
}
