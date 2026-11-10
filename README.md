<div align="center">

# 🌱 Salah

### A minimal app for one job: pray five times a day.

![Platform](https://img.shields.io/badge/platform-Android%208.0%2B-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?logo=kotlin&logoColor=white)
![UI](https://img.shields.io/badge/UI-Material%203-4285F4)
![SDK](https://img.shields.io/badge/minSdk%2026%20%C2%B7%20targetSdk%2035-informational)
![JDK](https://img.shields.io/badge/JDK-17-orange)

**No feed. No coins. No ads. Just: did you pray today, and will you come back for the next one?**

</div>

---

## Table of Contents

- [Overview](#overview)
- [Quick Start](#quick-start)
- [Design Directions](#design-directions)
- [Native Android App: Salah Garden](#native-android-app-salah-garden)
  - [Requirements](#requirements)
  - [Build and Run](#build-and-run)
  - [Run the Tests](#run-the-tests)
  - [Demo Mode](#demo-mode)
- [Features](#features)
- [Shared Product Spine](#shared-product-spine)
- [Project Structure](#project-structure)
- [Source Map](#source-map)
- [Rive Integration Guide](#rive-integration-guide)
- [Roadmap](#roadmap)
- [Out of Scope](#out-of-scope)
- [Success Metric](#success-metric)

---

## Overview

This repository holds the **first deliverable** of the Salah project: a user flow and three working UI directions for a single-purpose prayer habit app. One direction, **Salah Garden**, has been chosen and is now built as a **real native Android app** (Kotlin, Jetpack Compose, Material 3).

| Deliverable | Status |
|---|---|
| User flow (first run, daily loop, missed loop, notifications, scope) | ✅ Done |
| Three live HTML prototypes | ✅ Done |
| Native Android app (Salah Garden) | ✅ Built and working |
| Rive animations | 🔜 Documented swap point |

---

## Quick Start

### Explore the design directions

No build step or server is required. Open `index.html` directly in a browser.

> Google Fonts load from a CDN. With internet you get the intended typography; offline, the prototypes fall back to system fonts.

### Build the Android app

```powershell
cd android
$env:JAVA_HOME='C:\Program Files\Java\jdk-17.0.0.1'
.\gradlew.bat :app:assembleDebug
```

Then install it:

```bash
adb install -r android/app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.salahgarden.app/.MainActivity
```

---

## Design Directions

Start with **`index.html`**, the direction picker, which links to all three live prototypes.

| File | Description |
|---|---|
| `index.html` | Gallery comparing the three directions |
| `user-flow.html` | The MVP flow: first run, daily loop, missed loop, notifications, scope |
| `ui-1-noor-nights.html` | Direction 01: calm night-sky storybook |
| `ui-2-salah-garden.html` | Direction 02: daylight paper-cut garden |
| `ui-3-salah-quest.html` | Direction 03: bold comic mission control |
| `previews/` | Today-screen captures used by the gallery |

Each prototype has **four screens**: Location, Today, Missed, Week. Use the left rail (desktop) or the tab bar to move between them. To open a specific screen, add a query parameter:

```
?screen=setup   ?screen=missed   ?screen=week
```

### The three directions at a glance

| | 01 · Noor Nights | 02 · Salah Garden ✅ *chosen* | 03 · Salah Quest |
|---|---|---|---|
| **Concept** | Calm storybook night | Daylight paper-cut garden | Comic mission control |
| **Metaphor** | Five stars light up along a path; a lantern mascot named **Noor** keeps company | Five plants grow as prayers are watered; **Bud** the sprout responds to the day | Five missions and fuel cells; **Sami** the rocket waits on the launch pad |
| **Missed prayer** | Reads like a quiet note, not a failure | Offers water rather than guilt | Mission can still be completed |
| **Feel** | Bedtime, warmth, soft gold on deep indigo | Tactile, sunny, handmade paper layers | Bold outlines, blue and yellow, Duolingo-style momentum |
| **Best for** | Winding down, a gentle nightly ritual | Younger kids, a concrete growth metaphor | Energy, momentum, older kids who like missions |

> The choice is about **look and feel, not features.** Every direction shares the same spine (see [Shared Product Spine](#shared-product-spine)).

### Prototype demo behaviour

- The clock is fixed at **17:30**: Fajr and Dhuhr logged, Asr current, Maghrib next.
- Logging Asr updates the ring / path / garden / fuel cells and the week total, and opens a celebration.
- The Missed screen's **"Pray now"** marks Asr as made up, keeps the streak, and returns to Today.
- City changes update labels and times immediately.
- There is **no persistence**: reloading resets the demo.
- The GPS button *simulates* a found location (London); it does not request browser geolocation.
- Motion is CSS/SVG. No `.riv` files are included.
- Prayer times in the prototypes are **illustrative, not calculated**.
- Demo cities: London, Manchester, Makkah, Istanbul, New York, Kuala Lumpur (MWL, Umm al-Qura, Diyanet, ISNA, JAKIM).

---

## Native Android App: Salah Garden

The real daily loop, not a mockup. Prayer times are calculated on device, logs and streaks persist, and reminders are scheduled locally.

### Requirements

| Requirement | Version |
|---|---|
| JDK | 17 (build verified with `C:\Program Files\Java\jdk-17.0.0.1`) |
| Android SDK | 35 (`compileSdk` 35, `minSdk` 26, `targetSdk` 35) |
| Device / emulator | Android 8.0+ |

### Build and Run

```powershell
cd android
$env:JAVA_HOME='C:\Program Files\Java\jdk-17.0.0.1'
.\gradlew.bat :app:assembleDebug
```

The APK is written to:

```
android/app/build/outputs/apk/debug/app-debug.apk
```

Install and launch:

```bash
adb install -r android/app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.salahgarden.app/.MainActivity
```

### Run the Tests

```powershell
cd android
$env:JAVA_HOME='C:\Program Files\Java\jdk-17.0.0.1'
.\gradlew.bat :app:testDebugUnitTest
```

### Demo Mode

Debug builds accept a **fixed-clock demo** so every screen opens on the same moment. It uses separate preferences and **never touches real logs**. Demo mode starts past first-run onboarding, so you land directly in the garden.

```bash
adb shell am start -n com.salahgarden.app/.MainActivity --ez demo true
```

**Demo clock:** Monday 9 Nov 2026, 17:30 London time. Fajr and Dhuhr are logged, Asr is current, there are six prior complete days, and the best run is 11.

**Jump straight to a screen:**

```bash
adb shell am start -n com.salahgarden.app/.MainActivity --ez demo true --es screen missed
```

Available values: `setup` · `missed` · `week` · `history` · `guide` · `today`

**See the first-run flow instead** by clearing app data first:

```bash
adb shell pm clear com.salahgarden.app
adb shell am start -n com.salahgarden.app/.MainActivity
```

> Reminders can be toggled and previewed from Setup using **"Preview a gentle nudge"**.

---

## Features

### 🕌 Prayer Times
- Calculated **on device** using [adhan 1.2.1](https://github.com/batoulapps/adhan-java), with no network required.
- Shafi Asr, twilight-angle high-latitude rule, and per-city methods: **MWL, Umm al-Qura, Diyanet, ISNA, JAKIM**.
- Unit tests verify London, Makkah, New York, and Kuala Lumpur against Aladhan reference values.

### 📍 Location
- Platform `LocationManager` + `Geocoder` with runtime permissions.
- A six-city picker is the fallback when location is unavailable or declined.

### 💾 Persistence
- `SharedPreferences` + JSON day logs.
- Stores prayers, make-up flags, city, reminder preference, and streaks.

### 🔔 Reminders
- `AlarmManager` schedules an **adhan nudge** plus a **20-minute pre-nudge** for each prayer, three days ahead.
- Dedicated notification channel, boot and time-change rescheduling, and **quiet after Isha**.

### 🌿 The Daily Loop
- **One-tap logging** of the current prayer, with a countdown.
- **Make-up prayer** counts toward today.
- **Shame-free missed debrief** showing one of **ten authentic ahadith about salah**, with collection, number, and grading visible. The hadith is chosen by date and prayer so the same check-in stays stable, while "Another hadith" browses the rest.
- **7-day flowers**, weekly totals, current streak, and best run.

### 📜 Full History
- Every stored day is kept, and nothing is pruned.
- The History tab lists all logged days newest-first, grouped by month, with per-day counts and make-up / missed markers.
- Lifetime totals: prayers watered, perfect days, current streak.

### 👋 First-Run Onboarding
- Three steps: your **name**, **notifications**, **location**, each with its system dialog.
- Nothing is required: both permissions can be skipped and changed later.
- The name appears in the Today greeting.

### 📖 Learn Salah Guide
- A separate **15-lesson beginner guide**, from zero and in order.
- Bilingual content, a step-by-step prayer walkthrough engine, and recitation cards (Arabic / transliteration / meaning, each layer toggleable).
- Quizzes and local progress tracking.
- See [`android/GUIDE_CONTENT_README.md`](android/GUIDE_CONTENT_README.md) to edit lessons and add audio or illustrations.

---

## Shared Product Spine

Every direction, prototype and native, is built on the same foundation.

1. **Location → times.** City search or a GPS shortcut selects the calculation method and the five prayer windows.
2. **One-tap logging.** The current prayer has a countdown and one obvious "I prayed" button.
3. **Shame-free missed flow.** A closed window opens a debrief with one reason why salah matters, plus an option to pray now. A make-up prayer counts toward today.
4. **A quiet week view.** Seven days, five marks per day, the current streak, and the next window. No charts, coins, shop, feed, or ads.
5. **Notification policy.** One nudge at each adhan, a second 20 minutes before, quiet after Isha. In-app check-ins remain if reminders are off.
6. **Rive slots.** Idle, prayed, missed, and streak-up moments are documented in each prototype's "Rive motion plan" panel.

---

## Project Structure

```
.
├── index.html                  # Direction picker
├── user-flow.html              # MVP user flow
├── ui-1-noor-nights.html       # Direction 01
├── ui-2-salah-garden.html      # Direction 02 (chosen)
├── ui-3-salah-quest.html       # Direction 03
├── previews/                   # Today-screen captures for the gallery
└── android/                    # Native Android app (Salah Garden)
    ├── app/
    ├── screenshots/            # Emulator captures used for verification
    └── GUIDE_CONTENT_README.md # How to edit the Learn Salah content
```

---

## Source Map

Paths below are relative to `android/app/src/main/java/com/salahgarden/app/`.

### Core

| Path | Responsibility |
|---|---|
| `MainActivity.kt` | Edge-to-edge host, permissions, demo / screen extras |
| `GardenViewModel.kt` | Derived state, prayer windows, logging, setup, reminders |

### Data

| Path | Responsibility |
|---|---|
| `data/PrayerTimeProvider.kt` | Adhan calculation, methods, formatting |
| `data/GardenRepository.kt` | Persistence, streaks, week logs, demo seed |

### Notifications

| Path | Responsibility |
|---|---|
| `notify/` | Channel, reminder builder, alarms, receivers |

### Screens

| Path | Responsibility |
|---|---|
| `ui/TodayScreen.kt` | Hero, plant row, logging, gentle nudges |
| `ui/SetupScreen.kt` | City search, GPS, reminders, save |
| `ui/MissedScreen.kt` | Check-in, hadith card, make-up action |
| `ui/WeekScreen.kt` | Streak, seven flowers, totals, next watering |
| `ui/HistoryScreen.kt` | Lifetime stats and every stored day, grouped by month |
| `ui/OnboardingScreen.kt` | First run: name, notification permission, location permission |
| `ui/LearnSalahScreen.kt` | Learn Salah home: progress ring, shortcuts, lesson list |
| `ui/LessonDetailScreen.kt` | Lesson reader: every block type, recitation cards, quiz |

### Design System and Content

| Path | Responsibility |
|---|---|
| `ui/GardenFormat.kt` | Formatting helpers, the salah hadith bank, per-missed-prayer rotation |
| `ui/GardenArt.kt` | All paper-cut artwork and Bud |
| `ui/GardenComponents.kt` | Cards, buttons, switch, tabs |
| `learn/` | Learn Salah content, data model, and progress repository |

> The hadith bank lives in `ui/GardenFormat.kt` and is covered by `HadithBankTest`.

---

## Rive Integration Guide

No `.riv` assets are included yet, so all motion is **native Compose**: animated plant states, Bud's idle wobble, petal rain, and celebration scale/fade. The swap to Rive is isolated and documented.

**Steps**

1. Add `app.rive:rive-android` (plus the Compose integration from the Rive docs) to `android/app/build.gradle.kts`.
2. Drop the state machine into `app/src/main/res/raw/`, for example `bud.riv`.
3. In `ui/GardenArt.kt`, replace the drawing composables `BudSprout`, `PlantArt`, `WiltedPlant`, and `WeekFlower` with `RiveAnimation`, and bind the state machine inputs from the existing `GardenUiState`.
4. Replace `PetalRain` in `ui/GardenApp.kt` with the Rive celebration, or keep it as an overlay while Rive handles the hero.

**State machine inputs**

| App state | Rive input |
|---|---|
| `log.isLogged(currentPrayer)` | `watered` |
| `madeUp` | `made_up` |
| `celebration != null` | `prayed` |
| Streak increases | `streak_up` |
| `state.missedPrayer` | `missed` |

The rest of the screens and the entire data layer stay unchanged.

---

## Roadmap

Status of the original "before this becomes the real app" checklist:

| # | Item | Status | Notes |
|---|---|:---:|---|
| 1 | Pick a direction | ✅ | Salah Garden |
| 2 | Real prayer times | ✅ | Calculated on device via adhan instead of the Aladhan HTTP API |
| 3 | Persist the day | ✅ | `SharedPreferences` + JSON logs. An account is only needed for cross-device sync |
| 4 | Notifications | ✅ | Adhan + 20-minute pre-nudge, quiet after Isha, in-app fallback |
| 5 | Rive | 🔜 | See [Rive Integration Guide](#rive-integration-guide) |
| 6 | Window logic | 🔄 | Finalize handling of missed prayers, travel, and make-up counts in the streak rules |
| 7 | Accessibility pass | 🔄 | Focus states, reduced motion, screen-reader labels for prayer status, 44px touch targets. Verify on real devices |

---

## Out of Scope

These are deliberately excluded to keep the app focused.

- ❌ Social feed, leaderboards, or sharing
- ❌ Coins, shop, loot boxes, or extra currencies
- ❌ Ads or upsells
- ❌ Long content libraries *(beyond the single beginner Learn Salah guide)*
- ❌ More than five prayers or extra habit tracking

---

## Success Metric

> **Did the person pray today, and will they come back for the next one?**

Everything in this project is measured against that single question.
