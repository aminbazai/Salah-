# Salah — habit app direction study

A minimal app for one job: **pray five times a day.**

This folder contains the first deliverable: a user flow and three working UI directions. Pick one direction, then the app gets built around it. The chosen direction — **Salah Garden** — now also exists as a native Android app in `android/`.

## Open it

Start with **`index.html`** — the direction picker. It links to all three live prototypes.

No build step or server is required. Open the HTML files directly in a browser. Google Fonts load from a CDN, so an internet connection gives the intended type; offline, the prototypes fall back to system fonts.

| File | What it is |
| --- | --- |
| `index.html` | Gallery comparing the three directions and linking everything |
| `user-flow.html` | The MVP flow: first run, daily loop, missed loop, notifications, scope |
| `ui-1-noor-nights.html` | Direction 01 — calm night-sky storybook |
| `ui-2-salah-garden.html` | Direction 02 — daylight paper-cut garden |
| `ui-3-salah-quest.html` | Direction 03 — bold comic mission control |
| `previews/` | Today-screen captures used by the gallery |

Each prototype has four screens: **Location**, **Today**, **Missed**, **Week**. Use the left rail (desktop) or tab bar to move through them. Add `?screen=setup`, `?screen=missed`, or `?screen=week` to a prototype URL to open a specific screen.

## Native Android app

Salah Garden is also built as a native Android app in **`android/`**, using Kotlin and Jetpack Compose Material 3. It is the real daily loop, not a mockup: prayer times are calculated on device, logs and streaks persist, and reminders are scheduled locally.

### Requirements

- JDK 17 (the build is verified with `C:\Program Files\Java\jdk-17.0.0.1`)
- Android SDK 35 (`compileSdk` 35, `minSdk` 26, `targetSdk` 35)
- A device or emulator on Android 8.0+

### Build and run

```powershell
cd android
$env:JAVA_HOME='C:\Program Files\Java\jdk-17.0.0.1'
.\gradlew.bat :app:assembleDebug
```

The APK lands at `android/app/build/outputs/apk/debug/app-debug.apk`.

```powershell
adb install -r android/app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.salahgarden.app/.MainActivity
```

Run the unit tests with:

```powershell
cd android
$env:JAVA_HOME='C:\Program Files\Java\jdk-17.0.0.1'
.\gradlew.bat :app:testDebugUnitTest
```

### Demo mode

Debug builds accept a fixed-clock demo so every screen opens on the same moment. It uses separate preferences and never touches real logs. Demo mode starts past the first-run onboarding (the flag is pre-set), so launch it to see the garden directly.

```powershell
adb shell am start -n com.salahgarden.app/.MainActivity --ez demo true
```

To see the first-run flow instead, clear the app's data first:

```powershell
adb shell pm clear com.salahgarden.app
adb shell am start -n com.salahgarden.app/.MainActivity
```

Jump straight to a screen with `--es screen setup|missed|week|history|guide|today`:

```powershell
adb shell am start -n com.salahgarden.app/.MainActivity --ez demo true --es screen missed
```

The demo clock is **Mon 9 Nov 2026, 17:30** in London: Fajr and Dhuhr logged, Asr current, six prior complete days, best run 11. Reminders can be toggled and previewed from Setup ("Preview a gentle nudge").

### What is wired

- **Prayer times:** [`adhan`](https://github.com/batoulapps/adhan-java) 1.2.1, calculated on device. Shafi Asr, twilight-angle high-latitude rule, and per-city methods (MWL, Umm al-Qura, Diyanet, ISNA, JAKIM). Unit tests check London, Makkah, New York, and Kuala Lumpur against Aladhan reference values.
- **Location:** platform `LocationManager` + `Geocoder` with runtime permissions; a six-city picker is the fallback.
- **Persistence:** `SharedPreferences` + JSON day logs for prayers, make-up flags, city, reminders, and streaks.
- **Reminders:** `AlarmManager` schedules an adhan nudge plus a 20-minute pre-nudge for each prayer, three days ahead, with a notification channel, boot/time-change rescheduling, and quiet after Isha.
- **Loop:** one-tap logging, make-up for today, a shame-free missed debrief that shows one of ten authentic ahadith about salah (with collection, number, and grading), 7-day flowers, weekly totals, streak and best run.
- **Full history:** every stored day is kept — nothing is pruned. The History tab lists all logged days newest-first, grouped by month, with per-day counts, make-up and missed markers, plus lifetime totals (prayers watered, perfect days, current streak).
- **First-run onboarding:** three steps — your name, then notifications, then location — with the system dialogs for each. Nothing is required; both permissions can be skipped and changed later. The name shows in the Today greeting.
- **Salah guide:** the **Learn Salah** tab is a separate 15-lesson beginner guide — from zero, in order — with bilingual content, a step-by-step prayer walkthrough engine, recitation cards (Arabic / transliteration / meaning, each layer toggleable), quizzes, and local progress. See `android/GUIDE_CONTENT_README.md` for how to edit lessons and add audio or illustrations.

The hadith shown after a missed prayer is chosen from the bank by date and prayer, so different prayers and days rotate through the collection while the same check-in stays stable. "Another hadith" browses the rest. Each entry keeps its reference and grading visible; the bank lives in `ui/GardenFormat.kt` and is covered by `HadithBankTest`.

### Source map

| Path | Responsibility |
| --- | --- |
| `android/app/src/main/java/com/salahgarden/app/MainActivity.kt` | Edge-to-edge host, permissions, demo/screen extras |
| `.../GardenViewModel.kt` | Derived state, prayer windows, logging, setup, reminders |
| `.../data/PrayerTimeProvider.kt` | Adhan calculation, methods, formatting |
| `.../data/GardenRepository.kt` | Persistence, streaks, week logs, demo seed |
| `.../notify/` | Channel, reminder builder, alarms, receivers |
| `.../ui/TodayScreen.kt` | Hero, plant row, logging, gentle nudges |
| `.../ui/SetupScreen.kt` | City search, GPS, reminders, save |
| `.../ui/MissedScreen.kt` | Check-in, hadith card, make-up action |
| `.../ui/WeekScreen.kt` | Streak, seven flowers, totals, next watering |
| `.../ui/HistoryScreen.kt` | Full prayer history: lifetime stats and every stored day, grouped by month |
| `.../ui/OnboardingScreen.kt` | First-run: name, notification permission, location permission |
| `.../learn/` | Learn Salah content + data model + progress repository |
| `.../ui/LearnSalahScreen.kt` | Learn Salah home: progress ring, shortcuts, lesson list |
| `.../ui/LessonDetailScreen.kt` | Lesson reader: every block type, recitation cards, quiz |
| `.../ui/GardenFormat.kt` | Formatting helpers, the salah hadith bank, and per-missed-prayer rotation |
| `.../ui/GardenArt.kt` | All paper-cut artwork and Bud |
| `.../ui/GardenComponents.kt` | Cards, buttons, switch, tabs |
| `android/screenshots/` | Emulator captures used for verification |

### Rive swap point

No `.riv` assets are included, so all motion is native Compose (animated plant states, Bud's idle wobble, petal rain, celebration scale/fade). To move to Rive:

1. Add `app.rive:rive-android` (plus the Compose integration from the Rive docs) to `android/app/build.gradle.kts`.
2. Drop the state machine into `app/src/main/res/raw/`, for example `bud.riv`.
3. Replace the drawing composables in `ui/GardenArt.kt` — `BudSprout`, `PlantArt`, `WiltedPlant`, `WeekFlower` — with `RiveAnimation` and bind the state machine inputs from the existing `GardenUiState`.
4. Drive inputs from `GardenUiState`: `log.isLogged(currentPrayer)` → `watered`, `madeUp` → `made_up`, `celebration != null` → `prayed`, `streak` increase → `streak_up`, and `state.missedPrayer` → `missed`.
5. Replace `PetalRain` in `ui/GardenApp.kt` with the Rive celebration, or keep it as the overlay while Rive handles the hero. The rest of the screens and the entire data layer stay unchanged.

## The three directions

### 01 · Noor Nights
A calm storybook night. Five stars light up along a path as prayers are logged; a lantern mascot named Noor keeps company. The missed screen reads like a quiet note, not a failure state.
**Feels like:** bedtime, warmth, soft gold on deep indigo.
**Best for:** winding down and a gentle nightly ritual.

### 02 · Salah Garden
A daylight paper-cut garden. Five plants grow as prayers are watered; Bud the sprout responds to the day. Missed salah offers water rather than guilt, and a make-up prayer still counts.
**Feels like:** tactile, sunny, hand-made paper layers.
**Best for:** younger kids and a concrete growth metaphor.

### 03 · Salah Quest
A comic mission-control panel. Five missions and five fuel cells; Sami the rocket waits on the launch pad. Logging a prayer is a mission complete with confetti and a stamp.
**Feels like:** bold outlines, blue and yellow, Duolingo-style momentum.
**Best for:** energy, momentum, and older kids who like missions.

## What all three share

The choice is about look and feel, not features. Every direction includes the same spine:

- **Location → times.** City search or a GPS shortcut selects the calculation method and the five prayer windows.
- **One-tap logging.** The current prayer has a countdown and one obvious "I prayed" button.
- **Shame-free missed flow.** A closed window opens a debrief with one reason why salah matters, plus an option to pray now. A make-up prayer counts toward today.
- **A quiet week view.** Seven days, five marks per day, the current streak, and the next window. No charts, coins, shop, feed, or ads.
- **Notification policy.** One nudge at each adhan, a second 20 minutes before, quiet after Isha. In-app check-ins remain if reminders are off.
- **Rive slots.** Idle, prayed, missed, and streak-up moments are documented in each prototype's "Rive motion plan" panel.

Demo city data covers London, Manchester, Makkah, Istanbul, New York, and Kuala Lumpur with MWL, Umm al-Qura, Diyanet, ISNA, and JAKIM methods. Prayer times are illustrative, not calculated.

## Demo behaviour

- The clock is fixed at **17:30** so every prototype opens on the same moment: Fajr and Dhuhr logged, Asr current, Maghrib next.
- Logging Asr updates the ring/path/garden/fuel cells, the week total, and opens the celebration.
- The missed screen's "Pray now" marks Asr as made up, keeps the streak, and returns to Today.
- City changes update labels and times immediately. There is no persistence: reloading resets the demo.
- The GPS button simulates a found location (London); it does not request browser geolocation.
- Motion is CSS/SVG for now. No `.riv` files are included.

## Before this becomes the real app

1. **Pick a direction.** Everything else is independent of the visual choice.
2. **Real prayer times.** Integrate the [Aladhan API](https://aladhan.com/prayer-times-api): latitude/longitude or city, date, and calculation method. Cache the day's times and handle timezone/DST explicitly.
3. **Persist the day.** Start with local storage (logged prayers, make-up flags, city, reminder preference, streak). Add an account only when cross-device sync is actually needed.
4. **Notifications.** Local notifications for the adhan and the 20-minute pre-nudge; respect the "quiet after Isha" rule and the user's toggle. Keep an in-app fallback.
5. **Rive.** Build the idle, prayed, missed, and streak-up state machines. Trigger `prayed` on log, `missed` when a window closes, and `streak_up` only when the day completes.
6. **Window logic.** Decide the exact handling of missed prayers, travel, and make-up counts before writing the streak rules. The demo uses a simple "make-up counts today" rule.
7. **Accessibility pass.** Focus states, reduced motion, screen-reader labels for prayer status, and 44px touch targets are partially covered in the prototypes; verify with real devices.

The native app in `android/` already implements direction 02 and items 1–4 (on-device adhan calculation instead of the Aladhan HTTP API). Item 5 is the Rive swap point documented above.

## Deliberately out of scope

- Social feed, leaderboards, or sharing
- Coins, shop, loot boxes, or extra currencies
- Ads or upsells
- Long lessons, quizzes, or content libraries
- More than five prayers or extra habit tracking

The only success metric that matters: **did the person pray today, and will they come back for the next one?**
