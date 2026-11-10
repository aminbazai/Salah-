package com.salahgarden.app.learn

/**
 * Lessons 6–15 of the Learn Salah guide.
 *
 * ACCURACY NOTE: Arabic text must be proof-read against a printed Mushaf and
 * reviewed by a qualified scholar before release. Do not regenerate or
 * "fix" Arabic from memory.
 */

object LearnLessonsPart2 {

    val lessons: List<Lesson> = listOf(
        Lesson(
            id = "first_prayer",
            order = 6,
            titleEn = "Your first prayer: Fajr (2 rakat)",
            titleUr = "آپ کی پہلی نماز: فجر (۲ رکعت)",
            summaryEn = "A full, gentle walkthrough of the 2-rakat Fard of Fajr, position by position. The Sunnah of Fajr (also 2 rakat) is prayed first, in the same way.",
            summaryUr = "فجر کی ۲ رکعت فرض نماز کی مکمل رہنمائی، قدم بہ قدم۔ فجر کی سنت (بھی ۲ رکعت) پہلے پڑھی جاتی ہے، اسی طرح۔",
            blocks = listOf(
                Block.Callout(
                    titleEn = "Before you begin",
                    titleUr = "شروع کرنے سے پہلے",
                    textEn = "Make wudu, face the qibla, and have a clean place. You will need about five quiet minutes. Use the \"Pray along\" walkthrough for this prayer — it breaks the same steps into cards.",
                    textUr = "وضو کریں، قبلہ کی طرف منہ کریں اور پاک جگہ منتخب کریں۔ تقریباً پانچ منٹ کی خاموشی چاہیے۔ اس نماز کے لیے \"ساتھ پڑھیں\" والا حصہ استعمال کریں — وہی اقدام کارڈوں میں تقسیم ہیں۔"
                ),
                Block.Heading("Rakat 1", "پہلی رکعت"),
                Block.Steps(
                    listOf(
                        "Niyyah & Takbir Tahrimah. Stand facing the qibla, feet about four fingers apart. Intend in the heart: \"I pray 2 rakat fard of Fajr for Allah, facing the Ka'bah.\" Raise both hands to the ears (men; women to the shoulders) and say: اللَّهُ أَكْبَرُ (Allahu Akbar).",
                        "Fold the hands. Men: right over left, below the navel. Women: hands on the chest. Eyes on the place of sajdah.",
                        "Thana (silently).",
                        "Ta'awwudh (first rakat only).",
                        "Bismillah, then Surah Al-Fatiha.",
                        "A surah (for example Surah Al-Ikhlas) or at least three short verses.",
                        "Ruku. Say Allahu Akbar, bow with the back straight and level, hands on the knees with fingers spread. Say three times: سُبْحَانَ رَبِّيَ الْعَظِيمِ.",
                        "Qawmah (standing up from ruku). Say سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ while rising. Once upright, say رَبَّنَا لَكَ الْحَمْدُ.",
                        "First Sajdah. Say Allahu Akbar, go down: knees, then hands, then nose and forehead on the ground. Fingers together pointing to the qibla, elbows lifted from the ground (men), belly away from the thighs. Say three times: سُبْحَانَ رَبِّيَ الْأَعْلَىٰ.",
                        "Jalsa (sitting between sajdah). Say Allahu Akbar, sit on the left foot with the right foot upright, hands on the thighs. Pause briefly.",
                        "Second Sajdah. Say Allahu Akbar and repeat as the first sajdah."
                    ),
                    listOf(
                        "نیت اور تکبیر تحریمہ۔ قبلہ کی طرف کھڑے ہوں، پاؤں چار انگلیاں الگ۔ دل میں نیت کریں: \"میں فجر کی ۲ رکعت فرض نماز اللہ کے لیے، کعبہ کی طرف منہ کر کے پڑھتا ہوں۔\" دونوں ہاتھ کانوں تک اٹھائیں (مرد) یا کندھوں تک (عورت) اور کہیں: اللَّهُ أَكْبَرُ۔",
                        "ہاتھ باندھیں۔ مرد: دائیں ہاتھ بائیں پر، ناب سے نیچے۔ عورت: ہاتھ سینے پر۔ نگاہ سجدے کی جگہ پر۔",
                        "ثنا (خاموشی سے)۔",
                        "تعوذ (صرف پہلی رکعت میں)۔",
                        "بسم اللہ، پھر سورۃ الفاتحہ۔",
                        "کوئی سورت (مثلاً سورۃ الاخلاص) یا کم از کم تین مختصر آیات۔",
                        "رکوع۔ اللہ اکبر کہیں، پیٹھ سیدھی اور برابر رکھ کر جھکیں، ہاتھ گھٹنوں پر، انگلیاں کھلی ہوئیں۔ تین مرتبہ کہیں: سُبْحَانَ رَبِّيَ الْعَظِيمِ۔",
                        "قومہ (رکوع سے کھڑا ہونا)۔ اٹھتے ہوئے کہیں: سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ۔ کھڑے ہو کر کہیں: رَبَّنَا لَكَ الْحَمْدُ۔",
                        "پہلا سجدہ۔ اللہ اکبر کہیں، نیچے جائیں: پہلے گھٹنے، پھر ہاتھ، پھر ناک اور پیشانی زمین پر۔ انگلیاں قبلہ کی طرف، مرد کی ہری کہنیاں زمین سے اٹھی ہوئیں، پیٹ رانوں سے الگ۔ تین مرتبہ کہیں: سُبْحَانَ رَبِّيَ الْأَعْلَىٰ۔",
                        "جلسہ (سجدوں کے درمیان بیٹھنا)۔ اللہ اکبر کہیں، بائیں پنڈلی پر بیٹھیں اور دائیں پنڈلی کھڑی، ہاتھ رانوں پر۔ مختصر توقف کریں۔",
                        "دوسرا سجدہ۔ اللہ اکبر کہیں اور پہلے سجدے کی طرح کریں۔"
                    )
                ),
                Block.Heading("Rakat 2", "دوسری رکعت"),
                Block.Steps(
                    listOf(
                        "Say Allahu Akbar and stand up, without using the hands for support if possible. Fold the hands as before.",
                        "Say Bismillah, then Surah Al-Fatiha, then another surah. Thana and Ta'awwudh are not repeated.",
                        "Ruku, Qawmah and two sajdahs, exactly as in rakat 1."
                    ),
                    listOf(
                        "اللہ اکبر کہیں اور کھڑے ہو جائیں، ممکن ہو تو ہاتھوں کا سہارا نہ لیں۔ ہاتھ پہلے کی طرح باندھیں۔",
                        "بسم اللہ کہیں، پھر سورۃ الفاتحہ، پھر کوئی اور سورت۔ ثنا اور تعوذ دہرائے نہیں جاتے۔",
                        "رکوع، قومہ اور دونوں سجدے بالکل پہلی رکعت کی طرح۔"
                    )
                ),
                Block.Heading("Final sitting (Qa'dah Akhirah)", "آخری قعدہ"),
                Block.Steps(
                    listOf(
                        "After the second sajdah, sit as in jalsa and recite Tashahhud, then Durood Ibrahim, then Dua Masura.",
                        "Salam. Turn the face right saying السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ, then left, saying the same. The prayer is complete."
                    ),
                    listOf(
                        "دوسرے سجدے کے بعد جلسے کی طرح بیٹھیں اور تشہد، پھر درود ابراہیم، پھر دعا مسورہ پڑھیں۔",
                        "سلام۔ چہرہ دائیں کی طرف گم کریں اور کہیں: السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ، پھر بائیں کی طرف بھی یہی کہیں۔ نماز مکمل ہو گئی۔"
                    )
                ),
                Block.RecitationBlock(LearnRecitations.tashahhud),
                Block.RecitationBlock(LearnRecitations.durood),
                Block.RecitationBlock(LearnRecitations.duaMasura),
                Block.Callout(
                    titleEn = "You did it",
                    titleUr = "آپ نے کر دیا",
                    textEn = "That is a complete prayer. If anything felt unclear, open the \"Pray along\" walkthrough and go through it once more at your own pace.",
                    textUr = "یہ ایک مکمل نماز ہے۔ کچھ غیر واضح لگے تو \"ساتھ پڑھیں\" والا حصہ کھولیں اور اپنی رفتار سے دوبارہ دیکھیں۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "What is recited after the second rakat's sajdah?", "دوسری رکعت کے سجدے کے بعد کیا پڑھا جاتا ہے؟",
                    listOf("Only Surah Al-Fatiha", "Tashahhud, Durood, Dua", "Thana"),
                    listOf("صرف سورۃ الفاتحہ", "تشہد، درود، دعا", "ثنا"), 1
                ),
                QuizQuestion(
                    "Which of these is NOT repeated in the second rakat?", "دوسری رکعت میں کون سا حصہ دہرایا نہیں جاتا؟",
                    listOf("Surah Al-Fatiha", "Thana", "Ruku"), listOf("سورۃ الفاتحہ", "ثنا", "رکوع"), 1
                ),
                QuizQuestion(
                    "How many times is the face turned for Salam?", "سلام میں چہرہ کتنی مرتبہ گم کیا جاتا ہے؟",
                    listOf("Once", "Twice", "Four times"), listOf("ایک مرتبہ", "دو مرتبہ", "چار مرتبہ"), 1
                )
            )
        ),

        Lesson(
            id = "recitations",
            order = 7,
            titleEn = "Learn the recitations",
            titleUr = "ذکر و تلاوت سیکھیں",
            summaryEn = "Thana, Al-Fatiha, short surahs, Tashahhud, Durood and Dua — each with Arabic, transliteration and meaning, plus a memorisation helper.",
            summaryUr = "ثنا، فاتحہ، مختصر سورتیں، تشہد، درود اور دعا — عربی، تلفظ اور معنی کے ساتھ، اور یاد کرنے کی مدد۔",
            blocks = listOf(
                Block.Text(
                    "Learn these one at a time. Start with Al-Fatiha, since it is recited in every rakat. Use the memorisation cards to hide a layer and test yourself.",
                    "انہیں ایک ایک کر کے سیکھیں۔ سورۃ الفاتحہ سے شروع کریں، کیونکہ ہر رکعت میں وہی پڑھی جاتی ہے۔ یاد کرنے کے لیے فلیش کارڈ استعمال کریں — کوئی حصہ چھپا کر خود کو ٹیسٹ کریں۔"
                ),
                Block.RecitationBlock(LearnRecitations.fatiha),
                Block.RecitationBlock(LearnRecitations.ikhlas),
                Block.RecitationBlock(LearnRecitations.kawthar),
                Block.RecitationBlock(LearnRecitations.tashahhud),
                Block.RecitationBlock(LearnRecitations.durood),
                Block.RecitationBlock(LearnRecitations.duaMasura),
                Block.Text(
                    "Other short surahs to add later (copy from a verified digital Mushaf, such as the King Fahd Complex text — do not type them from memory): Al-Asr, An-Nasr, Al-Falaq, An-Nas, Al-Fil, Quraysh, Al-Ma'un, Al-Kafirun.",
                    "بعد میں شامل کرنے کے لیے دیگر مختصر سورتیں (تصدیق شدہ ڈیجیتل مصحف سے کاپی کریں، جیسے کنگ فہد کمپلیکس متن — حفظ سے ٹائپ نہ کریں): عصر، نصر، فلق، ناس، فیل، قریش، مون، کافرون۔"
                ),
                Block.Heading("Recitation volume (Hanafi)", "بلند آواز کا حکم (حنفی)"),
                Block.Text(
                    "Fajr, Maghrib and Isha: the imam recites aloud in the first two rakat (Fajr: both rakat; Maghrib and Isha: the first two). Zuhr and Asr are silent. A person praying alone may recite aloud or quietly in Fajr, Maghrib and Isha, and must be silent in Zuhr and Asr. Everyone behind an imam stays silent and listens; the imam's recitation counts for the follower.",
                    "فجر، مغرب اور عشاء: امام پہلی دو رکعتوں میں بلند آواز سے پڑھتے ہیں (فجر: دونوں رکعتیں؛ مغرب اور عشاء: پہلی دو)۔ ظہر اور عصر خاموشی سے۔ اکیلے پڑھنے والا فجر، مغرب اور عشاء میں بلند یا خاموش پڑھ سکتا ہے، اور ظہر و عصر میں خاموش رہے۔ امام کے پیچھے سب خاموش رہتے ہیں اور سنتے ہیں؛ امام کی تلاوت مقتدی کے لیے کافی ہے۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "Which surah is recited in every rakat?", "ہر رکعت میں کون سی سورت پڑھی جاتی ہے؟",
                    listOf("Al-Ikhlas", "Al-Fatiha", "Al-Kawthar"), listOf("الاخلاص", "الفاتحہ", "الکوثر"), 1
                ),
                QuizQuestion(
                    "In which prayers is the recitation silent?", "کن نمازوں میں تلاوت خاموشی سے ہوتی ہے؟",
                    listOf("Fajr and Maghrib", "Zuhr and Asr", "Isha only"),
                    listOf("فجر اور مغرب", "ظہر اور عصر", "صرف عشاء"), 1
                )
            )
        ),

        Lesson(
            id = "three_four_rakat",
            order = 8,
            titleEn = "3-rakat and 4-rakat prayers",
            titleUr = "۳ رکعت اور ۴ رکعت نمازیں",
            summaryEn = "How Zuhr, Asr, Maghrib and Isha are put together — where the surah stops and where the sittings fall.",
            summaryUr = "ظہر، عصر، مغرب اور عشاء کیسی ہوتی ہیں — کہاں سورت رک جاتی ہے اور کہاں بیٹھنا ہے۔",
            blocks = listOf(
                Block.Heading("4-rakat fard (Zuhr, Asr, Isha)", "۴ رکعت فرض (ظہر، عصر، عشاء)"),
                Block.Steps(
                    listOf(
                        "Rakat 1: as in Lesson 6 — Thana, Ta'awwudh, Bismillah, Fatiha, surah.",
                        "Rakat 2: Bismillah, Fatiha, surah, then sit and recite Tashahhud only (no Durood). Then stand up saying Allahu Akbar.",
                        "Rakat 3 and 4: Bismillah and Fatiha only — no surah — then ruku and sajdahs.",
                        "Final sitting after rakat 4: Tashahhud, Durood, Dua Masura, Salam."
                    ),
                    listOf(
                        "پہلی رکعت: درس ۶ کی طرح — ثنا، تعوذ، بسم اللہ، فاتحہ، سورت۔",
                        "دوسری رکعت: بسم اللہ، فاتحہ، سورت، پھر بیٹھ کر صرف تشہد پڑھیں (درود نہیں)۔ پھر اللہ اکبر کہہ کر کھڑے ہوں۔",
                        "تیسری اور چوتھی رکعت: صرف بسم اللہ اور فاتحہ — سورت نہیں — پھر رکوع اور سجدے۔",
                        "چوتھی رکعت کے بعد آخری قعدہ: تشہد، درود، دعا مسورہ، سلام۔"
                    )
                ),
                Block.Heading("Maghrib (3 fard rakat)", "مغرب (۳ فرض رکعت)"),
                Block.Steps(
                    listOf(
                        "Rakat 1 and 2 as above; sit and recite Tashahhud after rakat 2.",
                        "Rakat 3: Fatiha only, ruku, sajdahs. Final sitting: Tashahhud, Durood, Dua, Salam."
                    ),
                    listOf(
                        "پہلی اور دوسری رکعت اوپر کی طرح؛ دوسری رکعت کے بعد بیٹھ کر تشہد پڑھیں۔",
                        "تیسری رکعت: صرف فاتحہ، رکوع، سجدے۔ آخری قعدہ: تشہد، درود، دعا، سلام۔"
                    )
                ),
                Block.Text(
                    "In every rakat of Sunnah and Nafl prayers (including the 3rd and 4th rakat of a 4-rakat sunnah or nafl), a surah is added after Fatiha.",
                    "سنت اور نفل نمازوں کی ہر رکعت میں (چار رکعت سنت یا نفل کی تیسری اور چوتھی رکعت سمیت) فاتحہ کے بعد سورت پڑھی جاتی ہے۔"
                ),
                Block.DifferenceNote(
                    titleEn = "Note on differences",
                    titleUr = "اختلاف کا نوٹ",
                    textEn = "The 4-rakat Sunnah of Asr and Isha (ghair mu'akkadah) — many scholars teach reciting Durood in the middle sitting in the Hanafi tradition. This point is marked for scholar review.",
                    textUr = "عصر اور عشاء کی چار رکعت سنت (غیر مؤکدہ) — بہت سے علما حنفی روایت میں درمیانی قعدے میں درود پڑھنے کی تعلیم دیتے ہیں۔ یہ نکتہ عالم کے جائزے کے لیے نشان زد ہے۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "In the 3rd and 4th rakat of a fard prayer, what is recited?", "فرض نماز کی تیسری اور چوتھی رکعت میں کیا پڑھا جاتا ہے؟",
                    listOf("Fatiha and a surah", "Fatiha only", "Nothing"), listOf("فاتحہ اور سورت", "صرف فاتحہ", "کچھ نہیں"), 1
                ),
                QuizQuestion(
                    "After which rakat do you sit in a 4-rakat fard prayer?", "چار رکعت فرض نماز میں کس رکعت کے بعد بیٹھتے ہیں؟",
                    listOf("After rakat 1", "After rakat 2", "After rakat 3"),
                    listOf("پہلی رکعت کے بعد", "دوسری رکعت کے بعد", "تیسری رکعت کے بعد"), 1
                )
            )
        ),

        Lesson(
            id = "rakat_table",
            order = 9,
            titleEn = "Rakat table & Witr",
            titleUr = "رکعتوں کا جدول اور وتر",
            summaryEn = "How many rakat each prayer has, and how Witr fits after Isha. A beginner's minimum is the fard only.",
            summaryUr = "ہر نماز میں کتنی رکعتیں ہیں اور عشاء کے بعد وتر کیسے ہے۔ ابتدائی حد صرف فرض ہے۔",
            blocks = listOf(
                Block.Table(
                    headers = listOf("Prayer", "Before fard", "Fard", "After fard"),
                    headersUr = listOf("نماز", "فرض سے پہلے", "فرض", "فرض کے بعد"),
                    rows = listOf(
                        listOf("Fajr", "2 Sunnah Mu'akkadah", "2", "none"),
                        listOf("Zuhr", "4 Sunnah Mu'akkadah", "4", "2 Sunnah Mu'akkadah, then 2 Nafl"),
                        listOf("Asr", "4 Sunnah Ghair Mu'akkadah", "4", "none"),
                        listOf("Maghrib", "none", "3", "2 Sunnah Mu'akkadah, then 2 Nafl"),
                        listOf("Isha", "4 Sunnah Ghair Mu'akkadah", "4", "2 Sunnah Mu'akkadah, 2 Nafl, then 3 Witr (Wajib), then 2 Nafl (optional)"),
                        listOf("Jumu'ah", "4 Sunnah Mu'akkadah", "2 (behind the imam, after the khutbah)", "4 Sunnah Mu'akkadah, then 2 Sunnah Mu'akkadah, then 2 Nafl")
                    ),
                    rowsUr = listOf(
                        listOf("فجر", "۲ سنت مؤکدہ", "۲", "کوئی نہیں"),
                        listOf("ظہر", "۴ سنت مؤکدہ", "۴", "۲ سنت مؤکدہ، پھر ۲ نفل"),
                        listOf("عصر", "۴ سنت غیر مؤکدہ", "۴", "کوئی نہیں"),
                        listOf("مغرب", "کوئی نہیں", "۳", "۲ سنت مؤکدہ، پھر ۲ نفل"),
                        listOf("عشاء", "۴ سنت غیر مؤکدہ", "۴", "۲ سنت مؤکدہ، ۲ نفل، پھر ۳ وتر (واجب)، پھر ۲ نفل (اختیاری)"),
                        listOf("جمعہ", "۴ سنت مؤکدہ", "۲ (خطبے کے بعد امام کے پیچھے)", "۴ سنت مؤکدہ، پھر ۲ سنت مؤکدہ، پھر ۲ نفل")
                    )
                ),
                Block.Callout(
                    titleEn = "Minimum for a beginner",
                    titleUr = "ابتدائی حد",
                    textEn = "Fard only. Then add Sunnah Mu'akkadah, then Witr, then the rest.",
                    textUr = "صرف فرض۔ پھر سنت مؤکدہ، پھر وتر، پھر باقی۔"
                ),
                Block.Heading("Witr (3 rakat, Wajib, after Isha)", "وتر (۳ رکعت، واجب، عشاء کے بعد)"),
                Block.Text(
                    "Prayed like Maghrib, but in the 3rd rakat, after Fatiha and a surah, raise the hands to the ears saying Allahu Akbar, fold the hands again and recite Dua Qunoot, then go to ruku.",
                    "مغرب کی طرح پڑھی جاتی ہے، مگر تیسری رکعت میں فاتحہ اور سورت کے بعد ہاتھ کانوں تک اٹھا کر اللہ اکبر کہیں، دوبارہ ہاتھ باندھیں اور دعا قنوت پڑھیں، پھر رکوع میں جائیں۔"
                ),
                Block.RecitationBlock(LearnRecitations.duaQunoot),
                Block.DifferenceNote(
                    titleEn = "Note on differences",
                    titleUr = "اختلاف کا نوٹ",
                    textEn = "For a beginner who has not yet memorised Dua Qunoot, a scholar-approved fallback is \"Rabbana atina fid-dunya hasanah…\". Flagged for review.",
                    textUr = "جو ابھی دعا قنوت یاد نہیں کر چکا، اس کے لیے عالم کی منظور شدہ متبادل \"رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً…\" ہے۔ جائزے کے لیے نشان زد۔"
                ),
                Block.Text(
                    "Sunnah timing notes: the Fajr Sunnah is prayed before the fard; if the congregation has begun and there is a chance to catch the jama'ah, join it. After-fard Sunnah is not prayed after Fajr and Asr.",
                    "سنت کے اوقات: فجر کی سنت فرض سے پہلے پڑھی جاتی ہے؛ اگر جماعت شروع ہو چکی ہو اور پکڑنے کا امکان ہو تو جماعت میں شامل ہوں۔ فرض کے بعد کی سنت فجر اور عصر کے بعد نہیں پڑھی جاتی۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "How many fard rakat are in Maghrib?", "مغرب کی فرض رکعتیں کتنی ہیں؟",
                    listOf("2", "3", "4"), listOf("۲", "۳", "۴"), 1
                ),
                QuizQuestion(
                    "Which prayers have no Sunnah after them?", "کن نمازوں کے بعد سنت نہیں ہے؟",
                    listOf("Fajr and Asr", "Zuhr and Maghrib", "Isha only"),
                    listOf("فجر اور عصر", "ظہر اور مغرب", "صرف عشاء"), 0
                ),
                QuizQuestion(
                    "What do we say in ruku?", "رکوع میں کیا کہتے ہیں؟",
                    listOf("Subhana Rabbiyal A'la", "Subhana Rabbiyal 'Azim", "Rabbana lakal hamd"),
                    listOf("سُبْحَانَ رَبِّيَ الْأَعْلَىٰ", "سُبْحَانَ رَبِّيَ الْعَظِيمِ", "رَبَّنَا لَكَ الْحَمْدُ"), 1
                ),
                QuizQuestion(
                    "What is recited only in the final sitting?", "صرف آخری قعدے میں کیا پڑھا جاتا ہے؟",
                    listOf("Durood Ibrahim and Dua Masura", "Thana", "Dua Qunoot"),
                    listOf("درود ابراہیم اور دعا مسورہ", "ثنا", "دعا قنوت"), 0
                ),
                QuizQuestion(
                    "Is a surah recited in the 3rd and 4th rakat of a fard prayer?", "کیا فرض نماز کی تیسری اور چوتھی رکعت میں سورت پڑھی جاتی ہے؟",
                    listOf("No, only Fatiha", "Yes, always", "Only in Isha"),
                    listOf("نہیں، صرف فاتحہ", "ہاں، ہمیشہ", "صرف عشاء میں"), 0
                ),
                QuizQuestion(
                    "Witr is:", "وتر ہے:",
                    listOf("Wajib, 3 rakat, with Dua Qunoot in the 3rd rakat", "Nafl, 2 rakat", "Wajib, 4 rakat"),
                    listOf("واجب، ۳ رکعت، تیسری رکعت میں دعا قنوت", "نفل، ۲ رکعت", "واجب، ۴ رکعت"), 0
                )
            )
        ),

        Lesson(
            id = "jumuah_eid",
            order = 10,
            titleEn = "Jumu'ah & the two Eids",
            titleUr = "جمعہ اور دو عیدیں",
            summaryEn = "A short introduction to Friday prayer and the Eid prayers.",
            summaryUr = "جمعہ کی نماز اور عید کی نمازوں کا مختصر تعارف۔",
            blocks = listOf(
                Block.Heading("Jumu'ah", "جمعہ"),
                Block.Text(
                    "Friday congregational prayer, replacing Zuhr. Obligatory for adult male residents. Ghusl, clean clothes, perfume (not for women outside), going early, and listening quietly to the khutbah. Women may pray Zuhr at home.",
                    "جمعہ کی جماعتی نماز جو ظہر کی جگہ ہے۔ بالغ مرد شہریوں پر واجب ہے۔ غسل، صاف کپڑے، خوشبو (عورتوں کے لیے باہر نہیں)، جلدی جانا، اور خطبہ خاموشی سے سننا۔ عورتیں گھر پر ظہر پڑھ سکتی ہیں۔"
                ),
                Block.Heading("Eid prayers (Fitr and Adha)", "عید کی نمازیں (فطر اور اضحیٰ)"),
                Block.Text(
                    "2 rakat Wajib with 6 extra takbirs (Hanafi: 3 takbirs in each rakat, with the second rakat's takbirs before ruku). Detailed Eid guidance is planned for a future version.",
                    "۲ رکعت واجب، ۶ اضافی تکبیروں کے ساتھ (حنفی: ہر رکعت میں ۳ تکبیریں، دوسری رکعت کی تکبیریں رکوع سے پہلے)۔ عید کی تفصیلی رہنمائی مستقبل کے ورژن کے لیے ہے۔"
                ),
                Block.DifferenceNote(
                    titleEn = "Note on differences",
                    titleUr = "اختلاف کا نوٹ",
                    textEn = "The number and placement of the extra takbirs in the Eid prayer differs between schools. This guide follows the Hanafi position.",
                    textUr = "عید کی نماز میں اضافی تکبیروں کی تعداد اور مقام مکاتبِ فکر میں مختلف ہے۔ یہ رہنمائی حنفی موقف کے مطابق ہے۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "Jumu'ah replaces which prayer?", "جمعہ کن نماز کی جگہ ہے؟",
                    listOf("Zuhr", "Asr", "Fajr"), listOf("ظہر", "عصر", "فجر"), 0
                ),
                QuizQuestion(
                    "How many rakat is the Eid prayer?", "عید کی نماز کتنی رکعت ہے؟",
                    listOf("2", "3", "4"), listOf("۲", "۳", "۴"), 0
                )
            )
        ),

        Lesson(
            id = "womens_differences",
            order = 11,
            titleEn = "Women's differences (Hanafi)",
            titleUr = "عورتوں کے مختلف مسائل (حنفی)",
            summaryEn = "Common Hanafi points on how a woman's prayer differs — shown for everyone, marked clearly, free of judgment.",
            summaryUr = "عورت کی نماز میں مختلف عام حنفی مسائل — سب کے لیے، واضح نشان زد، بغیر تنقید کے۔",
            blocks = listOf(
                Block.Callout(
                    titleEn = "For everyone",
                    titleUr = "سب کے لیے",
                    textEn = "These points are taught in books like Kitab e Namaz and are shown here for everyone. They are not a judgment on anyone's practice.",
                    textUr = "یہ مسائل کتابِ نماز جیسی کتب میں پڑھائے جاتے ہیں اور یہاں سب کے لیے دکھائے گئے ہیں۔ یہ کسی کے عمل پر تنقید نہیں۔"
                ),
                Block.Steps(
                    listOf(
                        "Raise the hands to the shoulders at Takbir Tahrimah, not to the ears.",
                        "Place the hands on the chest.",
                        "In ruku: a slight bow, hands on the knees without spreading the fingers, legs slightly bent.",
                        "In sajdah: arms close to the sides, belly against the thighs, legs to the right.",
                        "In sitting: sit on the left hip with both feet out to the right.",
                        "Keep the voice low in all prayers.",
                        "Women are excused from prayer during menstruation and postnatal bleeding, and do not make those prayers up later (fasts are made up, prayers are not). Prayer resumes after ghusl."
                    ),
                    listOf(
                        "تکبیر تحریمہ میں ہاتھ کندھوں تک اٹھائیں، کانوں تک نہیں۔",
                        "ہاتھ سینے پر رکھیں۔",
                        "رکوع میں: ہلکا جھکنا، ہاتھ گھٹنوں پر، انگلیاں نہ کھولیں، ٹانگیں ہلکی جھکی ہوئیں۔",
                        "سجدے میں: بازو پہلوں کے قریب، پیٹ رانوں سے لگا ہوا، پاؤں دائیں طرف۔",
                        "بیٹھنے میں: بائیں کولہے پر بیٹھیں، دونوں پاؤں دائیں طرف باہر۔",
                        "تمام نمازوں میں آواز دھیمی رکھیں۔",
                        "عورتیں حیض اور نفاس کے دوران نماز سے معاف ہیں، اور وہ نمازیں بعد میں قضاء نہیں کرتیں (روزے قضاء ہوتے ہیں، نمازیں نہیں)۔ غسل کے بعد نماز دوبارہ شروع ہوتی ہے۔"
                    )
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "Where do women raise their hands at Takbir Tahrimah?", "تکبیر تحریمہ میں عورتیں ہاتھ کہاں اٹھاتی ہیں؟",
                    listOf("To the ears", "To the shoulders", "Keep them down"),
                    listOf("کانوں تک", "کندھوں تک", "نیچے رکھیں"), 1
                ),
                QuizQuestion(
                    "Are missed prayers during menstruation made up later?", "کیا حیض کے دوران چوکی ہوئی نمازیں بعد میں قضاء کی جاتی ہیں؟",
                    listOf("Yes, all of them", "No, they are not made up", "Only half"),
                    listOf("ہاں، سب", "نہیں، قضاء نہیں ہوتیں", "صرف آدھی"), 1
                )
            )
        ),

        Lesson(
            id = "mistakes_sahw",
            order = 12,
            titleEn = "Mistakes & Sajdah Sahw",
            titleUr = "غلطیاں اور سجدہ سہو",
            summaryEn = "Common beginner mistakes, and what to do when you forget something in prayer.",
            summaryUr = "عام ابتدائی غلطیاں، اور نماز میں کچھ بھول جائیں تو کیا کریں۔",
            blocks = listOf(
                Block.Heading("Common beginner mistakes", "عام ابتدائی غلطیاں"),
                Block.Steps(
                    listOf(
                        "Rushing. Pause in ruku, qawmah, sajdah and jalsa — calm, not hurried. Tama'ninah (stillness) is required.",
                        "Not facing the qibla, or standing with unclean clothes.",
                        "Doing the positions in the wrong order.",
                        "Reciting aloud when it should be silent (see Lesson 7).",
                        "Looking around — keep the eyes on the place of sajdah.",
                        "Talking, eating, or turning the chest away from the qibla, which breaks the prayer."
                    ),
                    listOf(
                        "جلدی کرنا۔ رکوع، قومہ، سجدہ اور جلسہ میں توقف کریں — سکون، جلدی نہیں۔ اطمینان ضروری ہے۔",
                        "قبلہ کی طرف منہ نہ کرنا، یا ناپاک کپڑوں میں کھڑا ہونا۔",
                        "اعضائے نماز کو غلط ترتیب سے ادا کرنا۔",
                        "جہاں خاموشی چاہیے وہاں بلند آواز سے پڑھنا (درس ۷ دیکھیں)۔",
                        "ادھر ادھر دیکھنا — نگاہ سجدے کی جگہ پر رکھیں۔",
                        "بات کرنا، کھانا، یا سینہ قبلہ سے پھیرنا — یہ نماز توڑ دیتا ہے۔"
                    )
                ),
                Block.Heading("Forgetting something: Sajdah Sahw (Hanafi)", "بھول جانا: سجدہ سہو (حنفی)"),
                Block.Text(
                    "If you forget a wajib (for example you forgot the surah after Fatiha, or forgot the first sitting, or recited in the wrong order), do Sajdah Sahw: in the last sitting, recite Tashahhud and make a salam to one side only (the right), then make two sajdahs, sit again and recite Tashahhud, Durood and Dua, then do the final salam to both sides.",
                    "اگر کوئی واجب بھول جائیں (مثلاً فاتحہ کے بعد سورت بھول جائیں، یا پہلا قعدہ بھول جائیں، یا ترتیب غلط ہو جائے) تو سجدہ سہو کریں: آخری قعدے میں تشہد پڑھ کر صرف ایک طرف (دائیں) سلام کریں، پھر دو سجدے کریں، دوبارہ بیٹھ کر تشہد و درود و دعا پڑھیں، پھر دونوں طرف سلام کریں۔"
                ),
                Block.Text(
                    "If a fard is missed (for example ruku or a sajdah, or wudu breaks), the prayer must be repeated. If you are unsure about the number of rakat, think about it and go with what you believe is more likely; if there is no leaning at all, assume the smaller number and do Sajdah Sahw.",
                    "اگر کوئی فرض چوک جائے (مثلاً رکوع یا سجدہ، یا وضو توٹ جائے) تو نماز دوبارہ پڑھنی ہوگی۔ رکعتوں کی تعداد کا شک ہو تو سوچیں اور زیادہ تر جس پر ترجیح ہو اسے اختیار کریں؛ کوئی ترجیح بالکل نہ ہو تو کم تعداد لیں اور سجدہ سہو کریں۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "What is Sajdah Sahw for?", "سجدہ سہو کس لیے ہے؟",
                    listOf("For a missed fard", "For a forgotten wajib", "For breaking wudu"),
                    listOf("چوکے ہوئے فرض کے لیے", "بھولے ہوئے واجب کے لیے", "وضو ٹوٹنے پر"), 1
                ),
                QuizQuestion(
                    "If a fard is missed, what must you do?", "فرض چوک جائے تو کیا کرنا ہے؟",
                    listOf("Sajdah Sahw", "Repeat the prayer", "Nothing"), listOf("سجدہ سہو", "نماز دوبارہ پڑھیں", "کچھ نہیں"), 1
                )
            )
        ),

        Lesson(
            id = "travel_qaza",
            order = 13,
            titleEn = "Travel, illness & Qaza",
            titleUr = "سفر، بیماری اور قضاء",
            summaryEn = "Shortening prayers while travelling, praying while ill, and making up missed prayers.",
            summaryUr = "سفر میں قصر، بیماری میں نماز، اور چوکی ہوئی نمازوں کا قضاء۔",
            blocks = listOf(
                Block.Heading("Travel (Qasr)", "سفر (قصر)"),
                Block.Text(
                    "A traveller (distance of about 77 km; Hanafi: intention to travel about 48 miles) shortens the 4-rakat fard prayers (Zuhr, Asr, Isha) to 2. Fajr and Maghrib stay the same. Sunnah prayers are optional while travelling, except the Fajr Sunnah, which should be prayed if possible. A traveller who intends to stay 15 days or more at a place prays in full.",
                    "مسافر (تقریباً ۷۷ کلومیٹر؛ حنفی: تقریباً ۴۸ میل کا سفر کرنے کی نیت) چار رکعت فرض نمازیں (ظہر، عصر، عشاء) ۲ رکعت کرتا ہے۔ فجر اور مغرب ویسی ہی رہتی ہیں۔ سفر میں سنتیں اختیاری ہیں، سوائے فجر کی سنت کے جو ممکن ہو تو پڑھی جائے۔ جو مسافر ۱۵ دن یا زیادہ رہنے کا ارادہ کرے وہ مکمل پڑھتا ہے۔"
                ),
                Block.Heading("Illness", "بیماری"),
                Block.Text(
                    "Pray standing if able; if not, sitting; if not, lying down with signs of the head. Do not skip prayer because of illness unless unconscious.",
                    "اگر ہو سکے تو کھڑے ہو کر پڑھیں؛ نہ ہو سکے تو بیٹھ کر؛ نہ ہو سکے تو لیٹ کر سر کے اشاروں سے۔ بےہوشی کے علاوہ بیماری کی وجہ سے نماز نہ چھوڑیں۔"
                ),
                Block.Heading("Qaza (making up missed prayers)", "قضاء (چوکی ہوئی نمازیں)"),
                Block.Text(
                    "Pray as soon as you remember or wake up. Make up the fard prayer in its original number of rakat, and Witr too. For many years of missed prayers, make a fixed daily plan — for example, one extra Qaza after each prayer, in order, until complete. Ask a scholar for a structured plan if the amount is large. Tawbah (sincere repentance) is the first step; do not despair.",
                    "جیسے ہی یاد آئے یا بیدار ہوئے پڑھ لیں۔ فرض نماز اسی تعداد میں قضاء کریں، اور وتر بھی۔ کئی سالوں کی چوکی ہوئی نمازوں کے لیے مستقل روزانہ منصوبہ بنائیں — مثلاً ہر نماز کے بعد ایک قضاء، ترتیب سے، مکمل ہونے تک۔ مقدار زیادہ ہو تو کسی عالم سے منظم منصوبہ پوچھیں۔ توبہ اولین قدم ہے؛ مایوس نہ ہوں۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "Which prayers are shortened while travelling?", "سفر میں کون سی نمازیں قصر ہوتی ہیں؟",
                    listOf("All five", "Zuhr, Asr and Isha", "Fajr and Maghrib"),
                    listOf("تمام پانچ", "ظہر، عصر اور عشاء", "فجر اور مغرب"), 1
                ),
                QuizQuestion(
                    "What is the first step for missed prayers?", "چوکی ہوئی نمازوں کا پہلا قدم کیا ہے؟",
                    listOf("Give charity", "Tawbah (repentance)", "Fast for a month"),
                    listOf("صدقہ دینا", "توبہ", "مہینہ روزے رکھنا"), 1
                )
            )
        ),

        Lesson(
            id = "after_salah",
            order = 14,
            titleEn = "Dua after salah & Tasbih Fatima",
            titleUr = "نماز کے بعد کی دعا اور تسبیح فاطمہ",
            summaryEn = "The dhikr after prayer: Astaghfirullah, the dua, and the Tasbih of Fatima (ra).",
            summaryUr = "نماز کے بعد کا ذکر: استغفار، دعا، اور تسبیح فاطمہ (رضی اللہ عنہا)۔",
            blocks = listOf(
                Block.Text(
                    "Recite Astaghfirullah three times, then the dua below.",
                    "تین مرتبہ استغفار اللہ پڑھیں، پھر نیچے دی گئی دعا پڑھیں۔"
                ),
                Block.RecitationBlock(LearnRecitations.astaghfirullah),
                Block.RecitationBlock(LearnRecitations.afterSalam),
                Block.Heading("Tasbih Fatima (ra)", "تسبیح فاطمہ (رضی اللہ عنہا)"),
                Block.Text(
                    "SubhanAllah ×33, Alhamdulillah ×33, Allahu Akbar ×34. Then Ayat al-Kursi (Surah Al-Baqarah 2:255 — copy from a verified Mushaf) and personal du'a with raised hands.",
                    "سبحان اللہ ×۳۳، الحمد للہ ×۳۳، اللہ اکبر ×۳۴۔ پھر آیت الکرسی (سورۃ البقرہ ۲:۲۵۵ — تصدیق شدہ مصحف سے کاپی کریں) اور اٹھائے ہوئے ہاتھوں سے اپنی دعا۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "How many times is Astaghfirullah recited after prayer?", "نماز کے بعد استغفار اللہ کتنی مرتبہ پڑھا جاتا ہے؟",
                    listOf("Three", "Once", "Seven"), listOf("تین", "ایک", "سات"), 0
                ),
                QuizQuestion(
                    "In Tasbih Fatima, how many times is Allahu Akbar said?", "تسبیح فاطمہ میں اللہ اکبر کتنی مرتبہ کہا جاتا ہے؟",
                    listOf("33", "34", "35"), listOf("۳۳", "۳۴", "۳۵"), 1
                )
            )
        ),

        Lesson(
            id = "habit",
            order = 15,
            titleEn = "Building the habit",
            titleUr = "عادت بنانا",
            summaryEn = "Small, steady steps: one prayer at a time, paired with fixed moments, using the app's reminders and streaks.",
            summaryUr = "چھوٹے مستقل اقدام: ایک ایک کر کے، مقررہ اوقات کے ساتھ، ایپ کی یاددہانی اور سلسلے استعمال کرتے ہوئے۔",
            blocks = listOf(
                Block.Steps(
                    listOf(
                        "Start with one prayer you can keep daily, then add one more every week or two.",
                        "Pair salah with a fixed moment: after waking, after the adhan, before leaving for work.",
                        "Use the app's reminders and streaks. If you miss a day, simply restart without shame — consistency matters more than perfection.",
                        "Keep a short dua list on your phone lock screen until you memorise it.",
                        "Pray in congregation when you can; it multiplies the reward."
                    ),
                    listOf(
                        "ایک نماز سے شروع کریں جو روزانہ قائم رہے، پھر ہر دو ہفتوں میں ایک اور شامل کریں۔",
                        "نماز کو کسی مقررہ لمحے سے جوڑیں: بیدار ہونے پر، اذان کے بعد، کام پر نکلنے سے پہلے۔",
                        "ایپ کی یاددہانی اور سلسلے استعمال کریں۔ ایک دن چھوٹ جائے تو بغیر شرمندگی کے دوبارہ شروع کریں — استقامت کامیابی سے بڑھ کر ہے۔",
                        "یاد ہونے تک ایک مختصر دعا کی فہرست فون کی لاک اسکرین پر رکھیں۔",
                        "جب ممکن ہو جماعت میں پڑھیں؛ اس میں اجر بہت زیادہ ہے۔"
                    )
                ),
                Block.Callout(
                    titleEn = "A gentle word",
                    titleUr = "نرم الفاظ",
                    textEn = "Start small: one prayer at a time. The goal is not a perfect week — it is that you come back tomorrow.",
                    textUr = "چھوٹے ساتھ شروع کریں: ایک ایک کر کے۔ مقصد کوئی کامل ہفتہ نہیں — مقصد یہ ہے کہ آپ کل دوبارہ لوٹیں۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "What is the best way to build the habit?", "عادت بنانے کا بہترین طریقہ کیا ہے؟",
                    listOf("Start with one prayer and add gradually", "Pray all five from day one or not at all", "Only on weekends"),
                    listOf("ایک نماز سے شروع کر کے آہستہ آہستہ بڑھائیں", "پہلے دن سے پانچوں یا کچھ نہیں", "صرف ہفتے کے آخر میں"), 0
                )
            )
        )
    )
}
