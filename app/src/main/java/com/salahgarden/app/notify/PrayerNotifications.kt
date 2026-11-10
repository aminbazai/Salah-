package com.salahgarden.app.notify

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.salahgarden.app.MainActivity
import com.salahgarden.app.R
import com.salahgarden.app.data.Prayer

object PrayerNotifications {

    const val CHANNEL_ID = "prayer_reminders"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.notification_channel_description)
            enableVibration(true)
            setShowBadge(true)
        }
        context.getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
    }

    fun notifyPrayer(context: Context, prayer: Prayer, preNudge: Boolean) {
        if (Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val openApp = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MainActivity.EXTRA_OPEN_SCREEN, "today")
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            prayer.ordinal,
            openApp,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = if (preNudge) {
            "20 minutes until ${prayer.displayName}"
        } else {
            "${prayer.displayName} is here"
        }
        val body = if (preNudge) {
            "Bud has a little water ready. A quiet moment is coming."
        } else {
            "Your garden is waiting. One prayer, a few quiet minutes."
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.leaf))
            .build()

        NotificationManagerCompat.from(context)
            .notify(prayer.ordinal * 2 + if (preNudge) 1 else 0, notification)
    }
}
