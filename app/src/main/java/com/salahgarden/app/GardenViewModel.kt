package com.salahgarden.app

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.salahgarden.app.data.City
import com.salahgarden.app.data.DayGarden
import com.salahgarden.app.data.DayLog
import com.salahgarden.app.data.DaySchedule
import com.salahgarden.app.data.DeviceLocationProvider
import com.salahgarden.app.data.GardenRepository
import com.salahgarden.app.data.Prayer
import com.salahgarden.app.data.PrayerMethod
import com.salahgarden.app.data.PrayerTimeProvider
import com.salahgarden.app.notify.PrayerNotifications
import com.salahgarden.app.notify.PrayerScheduler
import com.salahgarden.app.ui.GardenScreen
import com.salahgarden.app.ui.SalahHadiths
import com.salahgarden.app.ui.hadithIndexFor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

data class Celebration(
    val title: String,
    val body: String
)

data class GardenUiState(
    val screen: GardenScreen,
    val hasChosenCity: Boolean,
    val userName: String,
    val onboardingDone: Boolean,
    val date: LocalDate,
    val now: LocalTime,
    val city: City,
    val schedule: DaySchedule,
    val log: DayLog,
    val streak: Int,
    val bestRun: Int,
    val week: List<DayGarden>,
    val weekTotal: Int,
    val history: List<DayGarden>,
    val historyTotal: Int,
    val historyPerfectDays: Int,
    val currentPrayer: Prayer,
    val nextPrayer: Prayer,
    val nextTime: LocalTime,
    val minutesUntilNext: Long,
    val fillFraction: Float,
    val tomorrowFajr: LocalTime,
    val reminders: Boolean,
    val setupCity: City,
    val setupFajr: LocalTime,
    val setupMaghrib: LocalTime,
    val searchQuery: String,
    val locating: Boolean,
    val missedPrayer: Prayer,
    val hadithIndex: Int,
    val celebration: Celebration?,
    val toast: String?,
    val toastId: Long,
    val notificationPrompt: Long
)

class GardenViewModel(
    application: Application,
    val demoMode: Boolean
) : AndroidViewModel(application) {

    private val repository = GardenRepository(application, demoMode)
    private val locationProvider = DeviceLocationProvider(application)

    private var screen = when {
        !repository.hasCompletedOnboarding -> GardenScreen.ONBOARDING
        repository.hasChosenCity -> GardenScreen.TODAY
        else -> GardenScreen.SETUP
    }
    private var setupCity: City = repository.selectedCity()
    private var searchQuery = ""
    private var locating = false
    private var missedSelection: Prayer? = null
    private var hadithIndex = 0
    private var celebrationValue: Celebration? = null
    private var toastValue: String? = null
    private var toastCounter = 0L
    private var notificationPrompt =
        if (repository.hasChosenCity && repository.remindersEnabled) 1L else 0L

    private val _state = MutableStateFlow(buildState())
    val state = _state.asStateFlow()

    init {
        PrayerNotifications.createChannel(getApplication())
        if (repository.hasChosenCity) {
            scheduleAlarms()
        }
        if (!demoMode) {
            viewModelScope.launch {
                while (isActive) {
                    delay(30_000L)
                    val previousDate = _state.value.date
                    publish()
                    if (_state.value.date != previousDate) scheduleAlarms()
                }
            }
        }
    }

    private fun buildState(): GardenUiState {
        val city = repository.selectedCity()
        val date = if (demoMode) DEMO_DATE else LocalDate.now(city.zoneId)
        val now = if (demoMode) DEMO_NOW else PrayerTimeProvider.nowIn(city.zoneId)
        val schedule = PrayerTimeProvider.schedule(city, date, demoMode)
        val log = repository.dayLog(date)
        val tomorrow = PrayerTimeProvider.schedule(city, date.plusDays(1), demoMode)
        val setupSchedule = PrayerTimeProvider.schedule(setupCity, date, demoMode)

        val current = Prayer.ordered.lastOrNull { !now.isBefore(schedule.timeOf(it)) }
            ?: Prayer.ISHA
        val next = Prayer.ordered.firstOrNull { schedule.timeOf(it).isAfter(now) }
            ?: Prayer.FAJR
        val nextIsTomorrow = next == Prayer.FAJR && !now.isBefore(schedule.timeOf(Prayer.ISHA))
        val nextTime = if (nextIsTomorrow) tomorrow.timeOf(Prayer.FAJR) else schedule.timeOf(next)

        val nowMinutes = now.hour * 60 + now.minute
        val minutesUntilNext = if (nextIsTomorrow) {
            (1440 - nowMinutes) + nextTime.hour * 60 + nextTime.minute
        } else {
            nextTime.hour * 60 + nextTime.minute - nowMinutes
        }

        val startMinutes = schedule.timeOf(current).let { it.hour * 60 + it.minute }
        var endMinutes = nextTime.let { it.hour * 60 + it.minute }
        if (endMinutes <= startMinutes) endMinutes += 1440
        var elapsedMinutes = nowMinutes
        if (elapsedMinutes < startMinutes) elapsedMinutes += 1440
        val fill = if (log.isLogged(current)) {
            1f
        } else {
            ((elapsedMinutes - startMinutes).toFloat() / (endMinutes - startMinutes).toFloat())
                .coerceIn(0f, 1f)
        }

        val passed = Prayer.ordered.filter { !schedule.timeOf(it).isAfter(now) }
        val derivedMissed = passed.lastOrNull { it != current && !log.isLogged(it) }
            ?: passed.lastOrNull { !log.isLogged(it) }
            ?: current

        val week = repository.week(date)
        val history = repository.history(date)

        return GardenUiState(
            screen = screen,
            hasChosenCity = repository.hasChosenCity,
            userName = repository.userName,
            onboardingDone = repository.hasCompletedOnboarding,
            date = date,
            now = now,
            city = city,
            schedule = schedule,
            log = log,
            streak = repository.streak(date),
            bestRun = repository.bestRun(date),
            week = week,
            weekTotal = week.sumOf { it.log.count },
            history = history,
            historyTotal = history.sumOf { it.log.count },
            historyPerfectDays = history.count { it.log.complete },
            currentPrayer = current,
            nextPrayer = next,
            nextTime = nextTime,
            minutesUntilNext = minutesUntilNext.toLong(),
            fillFraction = fill,
            tomorrowFajr = tomorrow.timeOf(Prayer.FAJR),
            reminders = repository.remindersEnabled,
            setupCity = setupCity,
            setupFajr = setupSchedule.timeOf(Prayer.FAJR),
            setupMaghrib = setupSchedule.timeOf(Prayer.MAGHRIB),
            searchQuery = searchQuery,
            locating = locating,
            missedPrayer = missedSelection ?: derivedMissed,
            hadithIndex = hadithIndex,
            celebration = celebrationValue,
            toast = toastValue,
            toastId = toastCounter,
            notificationPrompt = notificationPrompt
        )
    }

    private fun publish() {
        _state.value = buildState()
    }

    fun refresh() {
        if (!demoMode) {
            publish()
            if (repository.hasChosenCity) scheduleAlarms()
        }
    }

    fun openToday() {
        screen = GardenScreen.TODAY
        missedSelection = null
        publish()
    }

    fun openWeek() {
        screen = GardenScreen.WEEK
        publish()
    }

    fun openHistory() {
        screen = GardenScreen.HISTORY
        publish()
    }

    fun openLearn() {
        screen = GardenScreen.LEARN
        publish()
    }

    fun updateName(name: String) {
        repository.userName = name
        _state.value = _state.value.copy(userName = name)
    }

    fun finishOnboarding() {
        repository.completeOnboarding()
        screen = if (repository.hasChosenCity) GardenScreen.TODAY else GardenScreen.SETUP
        if (repository.hasChosenCity && repository.remindersEnabled) notificationPrompt += 1
        publish()
    }

    fun openSetup() {
        setupCity = repository.selectedCity()
        searchQuery = ""
        screen = GardenScreen.SETUP
        publish()
    }

    fun closeSetup() {
        screen = GardenScreen.TODAY
        publish()
    }

    fun updateSearch(query: String) {
        searchQuery = query
        _state.value = _state.value.copy(searchQuery = query)
    }

    fun selectCity(city: City) {
        setupCity = city
        publish()
    }

    fun useCurrentLocation() {
        if (locating) return
        locating = true
        publish()
        viewModelScope.launch {
            val fix = locationProvider.currentLocation()
            locating = false
            if (fix == null) {
                showToast("Couldn't reach your location. Pick a city below.")
            } else {
                setupCity = City(
                    id = "current",
                    name = fix.name,
                    country = fix.country,
                    latitude = fix.latitude,
                    longitude = fix.longitude,
                    zoneId = fix.zoneId,
                    method = methodFor(fix.country)
                )
                showToast("Location found · ${fix.name}")
            }
            publish()
        }
    }

    fun locationDenied() {
        showToast("Location off. You can still choose a city below.")
    }

    fun saveLocation() {
        repository.saveCity(setupCity)
        setupCity = repository.selectedCity()
        screen = GardenScreen.TODAY
        missedSelection = null
        if (repository.remindersEnabled) notificationPrompt += 1
        scheduleAlarms()
        showToast("Prayer times updated for ${setupCity.name}.")
        publish()
    }

    fun toggleReminders() {
        repository.remindersEnabled = !repository.remindersEnabled
        if (repository.remindersEnabled) notificationPrompt += 1
        scheduleAlarms()
        showToast(
            if (repository.remindersEnabled) {
                "Reminders on. One nudge at each adhan."
            } else {
                "Reminders off. In-app check-ins still wait for you."
            }
        )
        publish()
    }

    fun logCurrentPrayer() {
        val snapshot = _state.value
        val prayer = snapshot.currentPrayer
        if (snapshot.log.isLogged(prayer)) return
        repository.logPrayer(snapshot.date, prayer)
        val updated = repository.dayLog(snapshot.date)
        celebrationValue = Celebration(
            title = "${prayer.displayName} watered.",
            body = "${updated.count} of 5 blooms today. One small watering at a time."
        )
        publish()
    }

    fun openMissed(prayer: Prayer? = null) {
        missedSelection = prayer
        val snapshot = _state.value
        val focus = prayer ?: snapshot.missedPrayer
        hadithIndex = hadithIndexFor(snapshot.date.toEpochDay(), focus.ordinal)
        screen = GardenScreen.MISSED
        publish()
    }

    fun closeMissed() {
        missedSelection = null
        screen = GardenScreen.TODAY
        publish()
    }

    fun nextHadith() {
        hadithIndex = (hadithIndex + 1) % SalahHadiths.size
        _state.value = _state.value.copy(hadithIndex = hadithIndex)
    }

    fun prayMissedNow() {
        val snapshot = _state.value
        val prayer = snapshot.missedPrayer
        repository.logPrayer(snapshot.date, prayer, madeUp = true)
        val updated = repository.dayLog(snapshot.date)
        missedSelection = null
        screen = GardenScreen.TODAY
        celebrationValue = Celebration(
            title = "${prayer.displayName} watered.",
            body = "Today's garden still has ${updated.count} blooms. Keep growing."
        )
        publish()
    }

    fun dismissCelebration() {
        celebrationValue = null
        _state.value = _state.value.copy(celebration = null)
    }

    fun clearToast(id: Long) {
        if (id != toastCounter) return
        toastValue = null
        _state.value = _state.value.copy(toast = null)
    }

    fun showTestNotification() {
        PrayerNotifications.notifyPrayer(getApplication(), Prayer.ASR, preNudge = false)
        showToast("A preview nudge is on its way.")
    }

    private fun showToast(message: String) {
        toastCounter += 1
        toastValue = message
    }

    private fun scheduleAlarms() {
        PrayerScheduler.schedule(
            context = getApplication(),
            city = repository.selectedCity(),
            remindersEnabled = repository.remindersEnabled,
            demoMode = demoMode
        )
    }

    private fun methodFor(country: String): PrayerMethod = when {
        country.contains("Saudi", ignoreCase = true) -> PrayerMethod.MAKKAH
        country.contains("Türkiye", ignoreCase = true) ||
            country.contains("Turkey", ignoreCase = true) -> PrayerMethod.DIYANET
        country.contains("Malaysia", ignoreCase = true) -> PrayerMethod.JAKIM
        country.contains("United States", ignoreCase = true) ||
            country.contains("America", ignoreCase = true) ||
            country.contains("Canada", ignoreCase = true) -> PrayerMethod.ISNA
        else -> PrayerMethod.MWL
    }

    companion object {
        val DEMO_DATE: LocalDate = LocalDate.of(2026, 11, 9)
        val DEMO_NOW: LocalTime = LocalTime.of(17, 30)

        fun factory(application: Application, demo: Boolean) =
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
                    GardenViewModel(application, demo) as T
            }
    }
}
