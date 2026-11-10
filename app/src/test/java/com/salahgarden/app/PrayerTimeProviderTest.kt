package com.salahgarden.app

import com.salahgarden.app.data.Cities
import com.salahgarden.app.data.Prayer
import com.salahgarden.app.data.PrayerTimeProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class PrayerTimeProviderTest {

    private val date: LocalDate = LocalDate.of(2026, 11, 9)

    private fun minutes(time: LocalTime): Int = time.hour * 60 + time.minute

    private fun assertTimeClose(
        label: String,
        expected: String,
        actual: LocalTime,
        toleranceMinutes: Int = 3
    ) {
        val expectedMinutes = expected.substring(0, 2).toInt() * 60 + expected.substring(3, 5).toInt()
        val delta = kotlin.math.abs(minutes(actual) - expectedMinutes)
        assertTrue(
            "$label expected $expected but was ${PrayerTimeProvider.format(actual)}",
            delta <= toleranceMinutes
        )
    }

    @Test
    fun londonMatchesAladhanReference() {
        val schedule = PrayerTimeProvider.schedule(Cities.london, date)
        assertTimeClose("Fajr", "05:13", schedule.timeOf(Prayer.FAJR))
        assertTimeClose("Dhuhr", "11:44", schedule.timeOf(Prayer.DHUHR))
        assertTimeClose("Asr", "13:57", schedule.timeOf(Prayer.ASR))
        assertTimeClose("Maghrib", "16:20", schedule.timeOf(Prayer.MAGHRIB))
        assertTimeClose("Isha", "18:09", schedule.timeOf(Prayer.ISHA))
    }

    @Test
    fun makkahMatchesAladhanReference() {
        val schedule = PrayerTimeProvider.schedule(Cities.makkah, date)
        assertTimeClose("Fajr", "05:10", schedule.timeOf(Prayer.FAJR))
        assertTimeClose("Dhuhr", "12:04", schedule.timeOf(Prayer.DHUHR))
        assertTimeClose("Asr", "15:18", schedule.timeOf(Prayer.ASR))
        assertTimeClose("Maghrib", "17:41", schedule.timeOf(Prayer.MAGHRIB))
        assertTimeClose("Isha", "19:11", schedule.timeOf(Prayer.ISHA))
    }

    @Test
    fun newYorkMatchesAladhanReference() {
        val schedule = PrayerTimeProvider.schedule(Cities.newYork, date)
        assertTimeClose("Fajr", "05:18", schedule.timeOf(Prayer.FAJR))
        assertTimeClose("Dhuhr", "11:40", schedule.timeOf(Prayer.DHUHR))
        assertTimeClose("Asr", "14:22", schedule.timeOf(Prayer.ASR))
        assertTimeClose("Maghrib", "16:43", schedule.timeOf(Prayer.MAGHRIB))
        assertTimeClose("Isha", "18:01", schedule.timeOf(Prayer.ISHA))
    }

    @Test
    fun kualaLumpurMatchesAladhanReference() {
        val schedule = PrayerTimeProvider.schedule(Cities.kualaLumpur, date)
        assertTimeClose("Fajr", "05:37", schedule.timeOf(Prayer.FAJR))
        assertTimeClose("Dhuhr", "12:57", schedule.timeOf(Prayer.DHUHR))
        assertTimeClose("Asr", "16:20", schedule.timeOf(Prayer.ASR))
        assertTimeClose("Maghrib", "18:57", schedule.timeOf(Prayer.MAGHRIB))
        assertTimeClose("Isha", "20:09", schedule.timeOf(Prayer.ISHA))
    }

    @Test
    fun demoPreviewMatchesDesignTimes() {
        val schedule = PrayerTimeProvider.schedule(Cities.london, date, demoMode = true)
        assertEquals("05:12", PrayerTimeProvider.format(schedule.timeOf(Prayer.FAJR)))
        assertEquals("12:34", PrayerTimeProvider.format(schedule.timeOf(Prayer.DHUHR)))
        assertEquals("15:58", PrayerTimeProvider.format(schedule.timeOf(Prayer.ASR)))
        assertEquals("18:42", PrayerTimeProvider.format(schedule.timeOf(Prayer.MAGHRIB)))
        assertEquals("20:10", PrayerTimeProvider.format(schedule.timeOf(Prayer.ISHA)))
    }

    @Test
    fun prayersAreInChronologicalOrder() {
        listOf(
            Cities.london,
            Cities.manchester,
            Cities.makkah,
            Cities.istanbul,
            Cities.newYork,
            Cities.kualaLumpur
        ).forEach { city ->
            val schedule = PrayerTimeProvider.schedule(city, date)
            Prayer.ordered.zipWithNext().forEach { (first, second) ->
                assertTrue(
                    "${city.name}: ${first.displayName} should precede ${second.displayName}",
                    schedule.timeOf(first) <= schedule.timeOf(second)
                )
            }
        }
    }
}
