# Learn Salah — content guide

The **Learn Salah** tab is a self-contained beginner's guide: 15 ordered lessons, a
step-by-step prayer walkthrough engine, recitation cards with three layers, quizzes,
and local progress. It never touches the streak or habit logic.

## How it is built

| File | What it holds |
| --- | --- |
| `learn/LearnModels.kt` | Data model: `Lesson`, `Block`, `Recitation`, `Position`, `PrayerSpec`, `QuizQuestion`, progress + flashcard state |
| `learn/LearnRecitations.kt` | Every Arabic recitation, keyed by id |
| `learn/LearnPrayerSpecs.kt` | Rakat table, prayer specs, and `WalkthroughBuilder` (the pure sequence logic) |
| `learn/LearnSalahContent.kt` | Lessons 1–5 |
| `learn/LearnSalahContentPart2.kt` | Lessons 6–15 |
| `learn/LearnSalahRepository.kt` | Local-only progress, flashcards, bookmarks, reader settings |
| `ui/LearnSalahScreen.kt` | Guide home: progress ring, shortcuts, lesson list |
| `ui/LessonDetailScreen.kt` | Lesson reader: every block type, recitation cards, quiz |

Content is data. To change wording, open the content files and edit the strings — no
UI code needs to change.

## Editing a lesson

```kotlin
Lesson(
    id = "why_we_pray",          // stable id, used for progress
    order = 1,                   // position in the list
    titleEn = "...", titleUr = "...",
    summaryEn = "...", summaryUr = "...",
    blocks = listOf(
        Block.Heading("...", "..."),
        Block.Text("...", "..."),
        Block.Steps(listOf("..."), listOf("...")),      // numbered steps
        Block.Callout("titleEn", "textEn", "titleUr", "textUr"),
        Block.RecitationBlock(LearnRecitations.fatiha),
        Block.Table(headers, headersUr, rows, rowsUr),
        Block.DifferenceNote("...", "..."),             // madhab differences
        Block.Image("img/slot.png", "caption"),
    ),
    quiz = listOf(QuizQuestion("q", "qUr", listOf("a","b","c"), listOf("a","b","c"), 0))
)
```

Add a new lesson by appending to the list in `LearnSalahContent.kt` or
`LearnSalahContentPart2.kt`, then add it to `AllLearnLessons` in
`ui/LearnSalahScreen.kt` if it lives in a new file.

## Adding audio

Recitation cards take an `audioFile` path. Put the file in
`app/src/main/res/raw/` (for short clips) or `app/src/main/assets/audio/` and set
the path on the recitation:

```kotlin
Recitation(..., audioFile = "audio/fatiha.mp3")
```

No audio files are bundled yet — the paths in `LearnRecitations.kt` are placeholders.
Until real files exist, the audio controls are hidden rather than shown broken.
Record or source recitations with a clear voice and a quiet room; keep each clip to one
recitation, and name it exactly as the `audioFile` path says.

## Adding illustrations

The walkthrough has an `image` slot per position (for example `img/takbir.png`).
Drop replacement artwork in `app/src/main/res/drawable/` or `assets/img/` using the same
name. Placeholder boxes are rendered until the file exists, so the layout never breaks.
Simple flat vector illustrations in the app's paper-cut style work best.

## The "pray along" walkthrough

`WalkthroughBuilder.build(spec, rakat)` is a pure function: given a `PrayerSpec` and a
rakat count it returns the ordered list of positions (Takbir, Qiyam, Ruku, Qawmah,
Sajdah, Jalsa, Sajdah, Qa'dah, Salam), inserting Dua Qunoot for Witr's third rakat and
only adding Salam at the end. `WalkthroughBuilderTest` covers every prayer at every
rakat count, so change the sequence logic only with the tests green.

## Progress storage

`LearnSalahRepository` writes to the `learn_salah` SharedPreferences file:

- `completed_lessons` — set of lesson ids
- `last_block:<id>` — scroll position within a lesson
- `quiz_score:<id>` — last quiz score
- `card:<recitationId>` — flashcard state (known, repetitions, next due day)
- `bookmarks` — set of lesson ids
- `layer:arabic|translit|meaning` — which recitation layers are shown
- `font_scale` — reader text size
- `reviewed_by` — scholar sign-off, empty until filled

Spaced repetition is deliberately simple: marking a card known bumps its interval
1 → 3 → 7 days. `dueCards()` returns what is due today.

## Scholar review process

**Nothing in this guide is verified yet.** Before release:

1. Have a qualified scholar or Dar-ul-Uloom review every lesson.
2. Proof-read all Quranic text and duas letter by letter against a printed or official
   digital Mushaf (the King Fahd Complex text is a reliable source). Do not retype or
   "correct" Arabic from memory.
3. Fill `LearnSalahRepository.setReviewedBy(name)` only after sign-off. The guide shows
   "Awaiting scholar review" until then.
4. For points where the schools differ, keep the "Note on differences" block and do not
   declare another school wrong.

Known open questions for review are flagged in the content with
`DifferenceNote` blocks — for example the first-sitting Durood in the 4-rakat Sunnah of
Asr and Isha, and the fallback dua for someone who has not memorised Dua Qunoot.

## Fonts

- Arabic: **Noto Naskh Arabic** (OFL) — `res/font/noto_naskh_arabic.ttf`
- Urdu: **Noto Nastaliq Urdu** (OFL) — `res/font/noto_nastaliq_urdu.ttf`

Both are open-licensed and bundled. Keep them updated together; the content files use
`ArabicFamily` and `UrduFamily` from `ui/theme/Theme.kt`.
