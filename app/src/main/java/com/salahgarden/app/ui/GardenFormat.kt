package com.salahgarden.app.ui

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class SalahHadith(
    val theme: String,
    val text: String,
    val source: String
)

/**
 * Authentic ahadith about salah. One is shown on every missed-prayer check-in;
 * "Another hadith" cycles through the bank. Translations are kept short and
 * kid-readable, with the reference and grading so nothing is passed off as
 * scripture without a source.
 */
val SalahHadiths = listOf(
    SalahHadith(
        theme = "accountability",
        text = "The first thing a person will be asked about on the Day of Judgement is their prayer.",
        source = "Jami' at-Tirmidhi 413 · hasan"
    ),
    SalahHadith(
        theme = "mercy",
        text = "If a river were at the door of one of you and he bathed in it five times a day, would any dirt remain? That is the five prayers; Allah erases sins with them.",
        source = "Sahih al-Bukhari 528 · agreed upon"
    ),
    SalahHadith(
        theme = "light",
        text = "Prayer is a light.",
        source = "Sahih Muslim 223"
    ),
    SalahHadith(
        theme = "love",
        text = "Prayer is the coolness of my eyes.",
        source = "Sunan an-Nasa'i 3939 · hasan"
    ),
    SalahHadith(
        theme = "conversation",
        text = "When one of you stands for prayer, he is speaking with his Lord.",
        source = "Sahih al-Bukhari 405"
    ),
    SalahHadith(
        theme = "closeness",
        text = "The closest a servant comes to his Lord is when he is prostrating.",
        source = "Sahih Muslim 482"
    ),
    SalahHadith(
        theme = "practice",
        text = "Pray as you have seen me praying.",
        source = "Sahih al-Bukhari 631"
    ),
    SalahHadith(
        theme = "return",
        text = "Whoever forgets a prayer or sleeps through it, let him pray it when he remembers it.",
        source = "Sahih Muslim 684"
    ),
    SalahHadith(
        theme = "mercy",
        text = "The five prayers are an expiation for what comes between them, so long as major sins are avoided.",
        source = "Sahih Muslim 233"
    ),
    SalahHadith(
        theme = "steadiness",
        text = "Whoever guards the five prayers will find them a light, a proof, and a salvation on the Day of Judgement.",
        source = "Musnad Ahmad 6576 · hasan"
    )
)

fun hadithIndexFor(epochDay: Long, prayerOrdinal: Int): Int =
    Math.floorMod(epochDay + prayerOrdinal, SalahHadiths.size.toLong()).toInt()

fun formatDuration(totalMinutes: Long): String {
    val minutes = totalMinutes.coerceAtLeast(0)
    val hours = minutes / 60
    val rest = minutes % 60
    return if (hours > 0) "${hours}h ${rest}m" else "${rest}m"
}

fun dateLine(date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern("EEE · d MMM", Locale.ENGLISH))
        .uppercase(Locale.ENGLISH)

fun shortWeekday(date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))
        .uppercase(Locale.ENGLISH)

fun monthLabel(date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))
        .uppercase(Locale.ENGLISH)

fun fullDateLine(date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern("EEE · d MMM yyyy", Locale.ENGLISH))
        .uppercase(Locale.ENGLISH)

fun numberWord(value: Int): String = when (value) {
    0 -> "No"
    1 -> "One"
    2 -> "Two"
    3 -> "Three"
    4 -> "Four"
    5 -> "Five"
    6 -> "Six"
    7 -> "Seven"
    8 -> "Eight"
    9 -> "Nine"
    10 -> "Ten"
    else -> value.toString()
}
