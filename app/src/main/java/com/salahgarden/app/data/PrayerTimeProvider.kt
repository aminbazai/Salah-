package com.salahgarden.app.data

import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object PrayerTimeProvider {

    private val clockFormat = DateTimeFormatter.ofPattern("HH:mm")

    private val demoTimes = mapOf(
        "london" to listOf("05:12", "12:34", "15:58", "18:42", "20:10"),
        "manchester" to listOf("05:20", "12:30", "15:42", "18:36", "20:06"),
        "makkah" to listOf("05:05", "12:12", "15:34", "18:05", "19:25"),
        "istanbul" to listOf("05:45", "12:55", "16:02", "18:20", "19:45"),
        "newyork" to listOf("05:30", "12:20", "15:45", "19:12", "20:30"),
        "kl" to listOf("05:50", "13:05", "16:25", "19:10", "20:20")
    )

    fun schedule(city: City, date: LocalDate, demoMode: Boolean = false): DaySchedule {
        if (demoMode) {
            val values = demoTimes[city.id] ?: demoTimes.getValue("london")
            return DaySchedule(
                date = date,
                times = Prayer.ordered.mapIndexed { index, prayer ->
                    prayer to LocalTime.parse(values[index])
                }.toMap(),
                source = "Design preview · 17:30"
            )
        }

        val times = PrayerTimes(
            Coordinates(city.latitude, city.longitude),
            DateComponents(date.year, date.monthValue, date.dayOfMonth),
            city.method.parameters()
        )

        fun local(dateValue: java.util.Date): LocalTime =
            dateValue.toInstant().atZone(city.zoneId).toLocalTime()

        return DaySchedule(
            date = date,
            times = mapOf(
                Prayer.FAJR to local(times.fajr),
                Prayer.DHUHR to local(times.dhuhr),
                Prayer.ASR to local(times.asr),
                Prayer.MAGHRIB to local(times.maghrib),
                Prayer.ISHA to local(times.isha)
            ),
            source = city.method.label
        )
    }

    fun format(time: LocalTime): String = time.format(clockFormat)

    fun nextDayFajr(city: City, date: LocalDate, demoMode: Boolean): LocalTime =
        schedule(city, date.plusDays(1), demoMode).timeOf(Prayer.FAJR)

    fun nowIn(zoneId: ZoneId): LocalTime = LocalTime.now(zoneId).withSecond(0).withNano(0)
}
