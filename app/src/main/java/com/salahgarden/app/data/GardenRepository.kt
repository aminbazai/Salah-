package com.salahgarden.app.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId

class GardenRepository(
    context: Context,
    val demoMode: Boolean = false
) {
    private val prefs = context.getSharedPreferences(
        if (demoMode) "salah_garden_demo" else "salah_garden",
        Context.MODE_PRIVATE
    )

    init {
        if (demoMode) seedDemoIfNeeded()
    }

    val hasChosenCity: Boolean
        get() = prefs.getBoolean(KEY_CHOSEN, false)

    var remindersEnabled: Boolean
        get() = prefs.getBoolean(KEY_REMINDERS, true)
        set(value) {
            prefs.edit().putBoolean(KEY_REMINDERS, value).apply()
        }

    val hasCompletedOnboarding: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDED, false)

    fun completeOnboarding() {
        prefs.edit().putBoolean(KEY_ONBOARDED, true).apply()
    }

    var userName: String
        get() = prefs.getString(KEY_NAME, null) ?: ""
        set(value) {
            prefs.edit().putString(KEY_NAME, value).apply()
        }

    fun selectedCity(): City {
        val id = prefs.getString(KEY_CITY_ID, Cities.london.id) ?: Cities.london.id
        if (id == "current") {
            val name = prefs.getString(KEY_CUSTOM_NAME, null) ?: return Cities.london
            val country = prefs.getString(KEY_CUSTOM_COUNTRY, "") ?: ""
            val latitude = prefs.getFloat(KEY_CUSTOM_LAT, Cities.london.latitude.toFloat()).toDouble()
            val longitude = prefs.getFloat(KEY_CUSTOM_LON, Cities.london.longitude.toFloat()).toDouble()
            val zone = runCatching {
                ZoneId.of(prefs.getString(KEY_CUSTOM_ZONE, ZoneId.systemDefault().id)!!)
            }.getOrDefault(ZoneId.systemDefault())
            val method = runCatching {
                PrayerMethod.valueOf(prefs.getString(KEY_CUSTOM_METHOD, PrayerMethod.MWL.name)!!)
            }.getOrDefault(PrayerMethod.MWL)
            return City("current", name, country, latitude, longitude, zone, method)
        }
        return Cities.byId(id) ?: Cities.london
    }

    fun saveCity(city: City) {
        val editor = prefs.edit().putBoolean(KEY_CHOSEN, true)
        if (city.id == "current") {
            editor
                .putString(KEY_CITY_ID, "current")
                .putString(KEY_CUSTOM_NAME, city.name)
                .putString(KEY_CUSTOM_COUNTRY, city.country)
                .putFloat(KEY_CUSTOM_LAT, city.latitude.toFloat())
                .putFloat(KEY_CUSTOM_LON, city.longitude.toFloat())
                .putString(KEY_CUSTOM_ZONE, city.zoneId.id)
                .putString(KEY_CUSTOM_METHOD, city.method.name)
        } else {
            editor.putString(KEY_CITY_ID, city.id)
        }
        editor.apply()
    }

    fun dayLog(date: LocalDate): DayLog {
        val raw = prefs.getString(dayKey(date), null) ?: return DayLog()
        return runCatching {
            val objectValue = JSONObject(raw)
            DayLog(
                logged = readPrayers(objectValue.optJSONArray("logged")),
                madeUp = readPrayers(objectValue.optJSONArray("madeUp"))
            )
        }.getOrDefault(DayLog())
    }

    fun saveDayLog(date: LocalDate, log: DayLog) {
        val objectValue = JSONObject()
            .put("logged", writePrayers(log.logged))
            .put("madeUp", writePrayers(log.madeUp))
        prefs.edit().putString(dayKey(date), objectValue.toString()).apply()
    }

    fun logPrayer(date: LocalDate, prayer: Prayer, madeUp: Boolean = false): DayLog {
        val current = dayLog(date)
        val logged = current.logged + prayer
        val madeUpSet = if (madeUp) current.madeUp + prayer else current.madeUp - prayer
        val updated = DayLog(logged, madeUpSet)
        saveDayLog(date, updated)
        return updated
    }

    fun week(today: LocalDate): List<DayGarden> =
        (6 downTo 0).map { offset ->
            val date = today.minusDays(offset.toLong())
            DayGarden(date, dayLog(date))
        }

    /**
     * Every stored day, newest first. The whole history lives in the
     * `day_<date>` preference keys, so "history" is just the set of keys
     * that exist — nothing is ever pruned.
     */
    fun history(today: LocalDate, limit: Int = 365): List<DayGarden> {
        val prefix = "day_"
        return prefs.all.keys
            .filter { it.startsWith(prefix) }
            .mapNotNull { key ->
                runCatching { LocalDate.parse(key.removePrefix(prefix)) }.getOrNull()
            }
            .filter { !it.isAfter(today) }
            .sortedDescending()
            .take(limit)
            .map { date -> DayGarden(date, dayLog(date)) }
    }

    /** Newest stored day that is not today, or null when only today exists. */
    fun previousHistoryDay(today: LocalDate): LocalDate? =
        history(today).firstOrNull { !it.date.isEqual(today) }?.date

    fun streak(today: LocalDate): Int {
        var day = today
        if (!dayLog(today).complete) day = today.minusDays(1)
        var streak = 0
        while (streak < 3650 && dayLog(day).complete) {
            streak += 1
            day = day.minusDays(1)
        }
        return streak
    }

    fun bestRun(today: LocalDate): Int {
        val stored = prefs.getInt(KEY_BEST_RUN, 0)
        val current = maxOf(stored, streak(today))
        if (current > stored) prefs.edit().putInt(KEY_BEST_RUN, current).apply()
        return current
    }

    fun totalWatered(today: LocalDate): Int =
        (0..6).sumOf { dayLog(today.minusDays(it.toLong())).count }

    private fun seedDemoIfNeeded() {
        if (prefs.getBoolean(KEY_DEMO_SEEDED, false)) return
        val demoToday = LocalDate.of(2026, 11, 9)
        prefs.edit()
            .putBoolean(KEY_CHOSEN, true)
            .putString(KEY_CITY_ID, Cities.london.id)
            .putBoolean(KEY_REMINDERS, false)
            .putBoolean(KEY_ONBOARDED, true)
            .putInt(KEY_BEST_RUN, 11)
            .putBoolean(KEY_DEMO_SEEDED, true)
            .apply()
        for (offset in 1..6) {
            saveDayLog(demoToday.minusDays(offset.toLong()), DayLog(Prayer.ordered.toSet()))
        }
        saveDayLog(demoToday, DayLog(setOf(Prayer.FAJR, Prayer.DHUHR)))
    }

    private fun dayKey(date: LocalDate): String = "day_$date"

    private fun readPrayers(array: JSONArray?): Set<Prayer> {
        if (array == null) return emptySet()
        return buildSet {
            for (index in 0 until array.length()) {
                runCatching { Prayer.valueOf(array.getString(index)) }.getOrNull()?.let(::add)
            }
        }
    }

    private fun writePrayers(prayers: Set<Prayer>): JSONArray {
        val array = JSONArray()
        Prayer.ordered.forEach { prayer ->
            if (prayer in prayers) array.put(prayer.name)
        }
        return array
    }

    companion object {
        private const val KEY_CHOSEN = "chosen_city"
        private const val KEY_CITY_ID = "city_id"
        private const val KEY_REMINDERS = "reminders"
        private const val KEY_BEST_RUN = "best_run"
        private const val KEY_CUSTOM_NAME = "custom_name"
        private const val KEY_CUSTOM_COUNTRY = "custom_country"
        private const val KEY_CUSTOM_LAT = "custom_lat"
        private const val KEY_CUSTOM_LON = "custom_lon"
        private const val KEY_CUSTOM_ZONE = "custom_zone"
        private const val KEY_CUSTOM_METHOD = "custom_method"
        private const val KEY_DEMO_SEEDED = "demo_seeded"
        private const val KEY_ONBOARDED = "onboarding_done"
        private const val KEY_NAME = "user_name"
    }
}
