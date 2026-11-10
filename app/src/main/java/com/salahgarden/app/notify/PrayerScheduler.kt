package com.salahgarden.app.notify

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.salahgarden.app.data.City
import com.salahgarden.app.data.Prayer
import com.salahgarden.app.data.PrayerTimeProvider
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

object PrayerScheduler {

    const val ACTION_PRAYER_ALARM = "com.salahgarden.app.PRAYER_ALARM"
    const val EXTRA_PRAYER = "prayer"
    const val EXTRA_PRE_NUDGE = "pre_nudge"

    fun schedule(
        context: Context,
        city: City,
        remindersEnabled: Boolean,
        demoMode: Boolean = false
    ) {
        cancelAll(context)
        if (!remindersEnabled || demoMode) return

        val now = ZonedDateTime.now(city.zoneId)
        val today = LocalDate.now(city.zoneId)
        for (dayOffset in 0..2) {
            val date = today.plusDays(dayOffset.toLong())
            val schedule = PrayerTimeProvider.schedule(city, date, demoMode = false)
            Prayer.ordered.forEach { prayer ->
                val prayerTime = ZonedDateTime.of(date, schedule.timeOf(prayer), city.zoneId)
                scheduleIfFuture(context, prayerTime, prayer, false, now, dayOffset)
                scheduleIfFuture(context, prayerTime.minusMinutes(20), prayer, true, now, dayOffset)
            }
        }
    }

    private fun scheduleIfFuture(
        context: Context,
        whenValue: ZonedDateTime,
        prayer: Prayer,
        preNudge: Boolean,
        now: ZonedDateTime,
        dayOffset: Int
    ) {
        if (!whenValue.isAfter(now.plusMinutes(1))) return
        val alarmManager = context.getSystemService(AlarmManager::class.java) ?: return
        val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
            action = ACTION_PRAYER_ALARM
            putExtra(EXTRA_PRAYER, prayer.name)
            putExtra(EXTRA_PRE_NUDGE, preNudge)
        }
        val requestCode = dayOffset * 100 + prayer.ordinal * 2 + if (preNudge) 1 else 0
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val triggerAt = whenValue.toInstant().toEpochMilli()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            alarmManager.canScheduleExactAlarms()
        ) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent)
        } else {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent)
        }
    }

    fun cancelAll(context: Context) {
        val alarmManager = context.getSystemService(AlarmManager::class.java) ?: return
        for (dayOffset in 0..3) {
            Prayer.ordered.forEach { prayer ->
                for (preNudge in 0..1) {
                    val requestCode = dayOffset * 100 + prayer.ordinal * 2 + preNudge
                    val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
                        action = ACTION_PRAYER_ALARM
                    }
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    alarmManager.cancel(pendingIntent)
                    pendingIntent.cancel()
                }
            }
        }
    }
}

class PrayerAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != PrayerScheduler.ACTION_PRAYER_ALARM) return
        val prayer = runCatching {
            Prayer.valueOf(intent.getStringExtra(PrayerScheduler.EXTRA_PRAYER) ?: "")
        }.getOrNull() ?: return
        val preNudge = intent.getBooleanExtra(PrayerScheduler.EXTRA_PRE_NUDGE, false)
        PrayerNotifications.notifyPrayer(context, prayer, preNudge)
    }
}

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val accepted = setOf(
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_TIMEZONE_CHANGED
        )
        if (intent.action !in accepted) return

        val repository = com.salahgarden.app.data.GardenRepository(context)
        if (!repository.hasChosenCity) return
        PrayerNotifications.createChannel(context)
        PrayerScheduler.schedule(
            context = context,
            city = repository.selectedCity(),
            remindersEnabled = repository.remindersEnabled
        )
    }
}
