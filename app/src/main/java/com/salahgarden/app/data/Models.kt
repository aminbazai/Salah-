package com.salahgarden.app.data

import com.batoulapps.adhan.CalculationParameters
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.HighLatitudeRule
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

enum class Prayer(val displayName: String) {
    FAJR("Fajr"),
    DHUHR("Dhuhr"),
    ASR("Asr"),
    MAGHRIB("Maghrib"),
    ISHA("Isha");

    companion object {
        val ordered = entries
    }
}

data class DayLog(
    val logged: Set<Prayer> = emptySet(),
    val madeUp: Set<Prayer> = emptySet()
) {
    val count: Int get() = logged.size
    val complete: Boolean get() = logged.size == Prayer.ordered.size

    fun isLogged(prayer: Prayer): Boolean = prayer in logged
    fun isMadeUp(prayer: Prayer): Boolean = prayer in madeUp
}

enum class PrayerMethod(
    val label: String,
    val fajrAngle: Double,
    val ishaAngle: Double,
    val ishaIntervalMinutes: Int = 0
) {
    MWL("MWL", 18.0, 17.0),
    ISNA("ISNA", 15.0, 15.0),
    MAKKAH("Umm al-Qura", 18.5, 0.0, 90),
    DIYANET("Diyanet", 18.0, 17.0),
    JAKIM("JAKIM", 20.0, 18.0);

    fun parameters(): CalculationParameters {
        val params = if (ishaIntervalMinutes > 0) {
            CalculationParameters(fajrAngle, ishaIntervalMinutes)
        } else {
            CalculationParameters(fajrAngle, ishaAngle)
        }
        params.madhab = Madhab.SHAFI
        params.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE
        return params
    }
}

data class City(
    val id: String,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val zoneId: ZoneId,
    val method: PrayerMethod
) {
    val label: String get() = "$name, $country"
    val methodLabel: String get() = "${method.label} · auto"
}

object Cities {
    val london = City(
        "london", "London", "UK", 51.5074, -0.1278,
        ZoneId.of("Europe/London"), PrayerMethod.MWL
    )
    val manchester = City(
        "manchester", "Manchester", "UK", 53.4808, -2.2426,
        ZoneId.of("Europe/London"), PrayerMethod.MWL
    )
    val makkah = City(
        "makkah", "Makkah", "Saudi Arabia", 21.4225, 39.8262,
        ZoneId.of("Asia/Riyadh"), PrayerMethod.MAKKAH
    )
    val istanbul = City(
        "istanbul", "Istanbul", "Türkiye", 41.0082, 28.9784,
        ZoneId.of("Europe/Istanbul"), PrayerMethod.DIYANET
    )
    val newYork = City(
        "newyork", "New York", "USA", 40.7128, -74.0060,
        ZoneId.of("America/New_York"), PrayerMethod.ISNA
    )
    val kualaLumpur = City(
        "kl", "Kuala Lumpur", "Malaysia", 3.1390, 101.6869,
        ZoneId.of("Asia/Kuala_Lumpur"), PrayerMethod.JAKIM
    )

    val all = listOf(london, manchester, makkah, istanbul, newYork, kualaLumpur)

    fun byId(id: String): City? = all.firstOrNull { it.id == id }
}

data class DaySchedule(
    val date: LocalDate,
    val times: Map<Prayer, LocalTime>,
    val source: String
) {
    fun timeOf(prayer: Prayer): LocalTime = times.getValue(prayer)
}

data class DayGarden(
    val date: LocalDate,
    val log: DayLog
)

data class MissedInfo(
    val prayer: Prayer,
    val index: Int
)
