package com.salahgarden.app.learn

/**
 * The fifteen lessons of the Learn Salah guide, in order.
 *
 * Content is data. A scholar should be able to review and edit these strings
 * without touching any composable.
 *
 * ACCURACY NOTE: Arabic text must be proof-read against a printed Mushaf and
 * reviewed by a qualified scholar (e.g. a local Dar-ul-Uloom) before release.
 * Do not regenerate, paraphrase or "fix" Arabic from memory.
 */

object LearnLessons {

    val lessons: List<Lesson> = listOf(
        Lesson(
            id = "why_we_pray",
            order = 1,
            titleEn = "Why we pray & the five prayers",
            titleUr = "ہم نماز کیوں پڑھتے ہیں اور پانچ نمازیں",
            summaryEn = "Salah is the second pillar of Islam and the most important act of worship after the declaration of faith. A Muslim prays five times a day — a direct connection with Allah, a source of calm, and a daily reset.",
            summaryUr = "نماز اسلام کا دوسرا رکن ہے اور ایمان کے بعد سب سے عمدہ عبادت ہے۔ مسلمان دن میں پانچ مرتبہ نماز پڑھتا ہے۔",
            blocks = listOf(
                Block.Text(
                    "Salah is the second pillar of Islam and the most important act of worship after the declaration of faith. A Muslim prays five times a day. Prayer is a direct connection with Allah, a source of calm and discipline, and a daily reset.",
                    "نماز اسلام کا دوسرا رکن ہے اور شہادت کے بعد سب سے بڑی عبادت ہے۔ مسلمان دن میں پانچ مرتبہ نماز ادا کرتا ہے۔ نماز اللہ سے براہ راست تعلق ہے، سکون اور نظم کا ذریعہ ہے اور روزانہ نئی شروعات ہے۔"
                ),
                Block.Table(
                    headers = listOf("Prayer", "Time window", "Fard rakat"),
                    headersUr = listOf("نماز", "وقت", "فرض رکعتیں"),
                    rows = listOf(
                        listOf("Fajr", "From true dawn until just before sunrise", "2"),
                        listOf("Zuhr", "After the sun passes its zenith until Asr begins", "4"),
                        listOf("Asr", "From Asr time until just before sunset", "4"),
                        listOf("Maghrib", "From sunset until the red twilight disappears", "3"),
                        listOf("Isha", "From the disappearance of twilight until before dawn (best before midnight)", "4")
                    ),
                    rowsUr = listOf(
                        listOf("فجر", "سچی سپیدی سے طلوع آفتاب سے کچھ پہلے تک", "۲"),
                        listOf("ظہر", "زوال آفتاب کے بعد سے عصر کے وقت تک", "۴"),
                        listOf("عصر", "عصر کے وقت سے غروب آفتاب سے کچھ پہلے تک", "۴"),
                        listOf("مغرب", "غروب آفتاب سے شفق کے غائب ہونے تک", "۳"),
                        listOf("عشاء", "شفق کے غائب ہونے سے فجر سے پہلے تک (آدھی رات سے پہلے بہتر)", "۴")
                    )
                ),
                Block.Text(
                    "Use a trusted local timetable or this app's prayer-time feature for exact times. Prayer must not begin at sunrise, exact midday (zawal), or sunset.",
                    "عین وقت کے لیے کسی قابلِ اعتماد مقامی ٹائم ٹیبل یا اس ایپ کی نماز کے وقت والی سہولت استعمال کریں۔ نماز نہ طلوع آفتاب کے وقت، نہ ٹھیک دوپہر (زوال) پر اور نہ غروب آفتاب کے وقت شروع ہو۔"
                ),
                Block.Callout(
                    titleEn = "Beginner tip",
                    textEn = "Start by learning Fajr (only 2 rakat). Add the others gradually. Praying imperfectly while learning is far better than not praying.",
                    titleUr = "ابتدائی مشورہ",
                    textUr = "فجر سیکھ کر شروع کریں (صرف ۲ رکعت)۔ باقی نمازیں آہستہ آہستہ سیکھیں۔ سیکھتے ہوئے ادھوری نماز بھی پڑھنا، نماز چھوڑ دینے سے بہت بہتر ہے۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "How many fard rakat are in Fajr?", "فجر کی فرض رکعتیں کتنی ہیں؟",
                    listOf("2", "3", "4"), listOf("۲", "۳", "۴"), 0
                ),
                QuizQuestion(
                    "When may a prayer NOT begin?", "نماز کب شروع نہیں کرنی چاہیے؟",
                    listOf("At true dawn", "At exact midday (zawal)", "After Asr time"),
                    listOf("سچی سپیدی پر", "ٹھیک زوال پر", "عصر کے بعد"), 1
                ),
                QuizQuestion(
                    "Which prayer is a good place to start learning?", "سیکھنے کے لیے کون سی نماز بہتر ہے؟",
                    listOf("Isha", "Fajr", "Jumu'ah"), listOf("عشاء", "فجر", "جمعہ"), 1
                )
            )
        ),

        Lesson(
            id = "time_qibla",
            order = 2,
            titleEn = "Prayer times & Qibla",
            titleUr = "نماز کا وقت اور قبلہ",
            summaryEn = "Qibla is the direction of the Kaaba in Makkah. Face it throughout the prayer — and if you cannot find it, make your best effort; the prayer is valid.",
            summaryUr = "قبلہ مکہ مکرمہ میں عبادت گاہ کعبہ کی طرف ہے۔ پوری نماز میں اسی طرف منہ رکھیں۔",
            blocks = listOf(
                Block.Text(
                    "Qibla is the direction of the Kaaba in Makkah. Face it throughout the prayer. If the direction is unknown and no one can tell you, make your best effort and pray — the prayer is valid.",
                    "قبلہ مکہ مکرمہ میں کعبہ کی سمت ہے۔ پوری نماز میں اسی طرف منہ رکھیں۔ اگر سمت معلوم نہ ہو اور کوئی بتا نہ سکے تو اپنی بہترین کوشش کریں اور نماز پڑھ لیں — نماز درست ہے۔"
                ),
                Block.Text(
                    "Use this app's city picker and prayer-time feature for exact times. Prayer must not begin at sunrise, exact midday (zawal), or sunset.",
                    "عین وقت کے لیے اس ایپ کا شہر انتخاب اور نماز کے وقت والی سہولت استعمال کریں۔"
                ),
                Block.Callout(
                    titleEn = "About compasses",
                    textEn = "A compass can be affected by metal and magnets. In a car, near a window, or beside a phone it may point wrong. When in doubt, ask someone local or use a known direction.",
                    titleUr = "قطب نما کے بارے میں",
                    textUr = "قطب نما لوہے اور مقناطیس سے متاثر ہو سکتا ہے۔ گاڑی میں، کھڑکی کے پاس یا فون کے قریب یہ غلط سمت دکھا سکتا ہے۔ شک ہو تو کسی مقامی سے پوچھیں یا معلوم سمت استعمال کریں۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "What is the Qibla?", "قبلہ کیا ہے؟",
                    listOf("The direction of the Kaaba", "The direction of the sun", "Any direction"),
                    listOf("کعبہ کی سمت", "سورج کی سمت", "کوئی بھی سمت"), 0
                ),
                QuizQuestion(
                    "If you cannot find the Qibla, what should you do?", "اگر قبلہ معلوم نہ ہو تو کیا کریں؟",
                    listOf("Skip the prayer", "Make your best effort and pray", "Wait until you know"),
                    listOf("نماز چھوڑ دیں", "بہترین کوشش کر کے پڑھ لیں", "معلوم ہونے تک انتظار کریں"), 1
                )
            )
        ),

        Lesson(
            id = "taharah",
            order = 3,
            titleEn = "Taharah: purity",
            titleUr = "طہارت: وضو، غسل، تیمم",
            summaryEn = "Wudu (ablution), ghusl (full bath) and tayammum (dry ablution) — how to become clean for prayer, and what breaks that cleanliness.",
            summaryUr = "وضو، غسل اور تیمم — نماز کے لیے پاکی کیسے حاصل کریں اور کیا اسے توڑ دیتا ہے۔",
            blocks = listOf(
                Block.Heading("Wudu (ablution)", "وضو"),
                Block.Text(
                    "Intention: in the heart — \"I am making wudu for the pleasure of Allah and to pray.\"",
                    "نیت: دل میں — \"میں اللہ کی رضا اور نماز کے لیے وضو کرتا ہوں۔\""
                ),
                Block.Steps(
                    items = listOf(
                        "Say \"Bismillah\" (بِسْمِ اللَّهِ).",
                        "Wash both hands up to the wrists, three times (between the fingers too).",
                        "Rinse the mouth three times, using the right hand.",
                        "Sniff water into the nose and clean it, three times.",
                        "Wash the whole face three times, from hairline to chin and ear to ear. Run wet fingers through a beard if any.",
                        "Wash the right arm up to and including the elbow, three times; then the left.",
                        "Wipe the head once with wet hands (Masah).",
                        "Wipe the ears (inside with the index fingers, behind with the thumbs) and the back of the neck.",
                        "Wash the right foot up to and including the ankle, three times, cleaning between the toes with the little finger; then the left foot."
                    ),
                    itemsUr = listOf(
                        "\"بسم اللہ\" کہیں۔",
                        "دونوں ہاتھ کلائیوں سمیت تین مرتبہ دھوئیں (انگلیوں کے درمیان بھی)۔",
                        "تین مرتبہ کلی کریں (دائیں ہاتھ سے)۔",
                        "تین مرتبہ ناک میں پانی لے کر صاف کریں۔",
                        "تین مرتبہ چہرہ دھوئیں، ماتھے سے ٹھوڑی تک اور کان سے کان تک۔ داڑھی ہو تو خشک انگلیوں سے دھوئیں۔",
                        "دائیں بازو کہنی سمیت تین مرتبہ دھوئیں، پھر بائیں۔",
                        "خشک ہاتھوں سے سر کا مسح ایک مرتبہ کریں۔",
                        "کانوں کا مسح کریں (اندر انگستھان سے، پیچھے انگوٹھے سے) اور گردن کے پیچھے کا مسح۔",
                        "دائیں پاؤں ٹخنے سمیت تین مرتبہ دھوئیں، انگلیوں کے درمیان چھوٹی انگلی سے صاف کریں؛ پھر بایاں پاؤں۔"
                    )
                ),
                Block.Callout(
                    titleEn = "The four fard acts of wudu (Hanafi)",
                    textEn = "Wash the face; wash both arms including the elbows; wipe at least a quarter of the head; wash both feet including the ankles. If any is missed, wudu is not valid.",
                    titleUr = "وضو کے چار فرض (حنفی)",
                    textUr = "چہرہ دھونا؛ دونوں بازو کہنیوں سمیت دھونا؛ سر کا کم از کم چوتھائی حصہ مسح کرنا؛ دونوں پاؤں ٹخنوں سمیت دھونا۔ کوئی بھی چھوٹ جائے تو وضو درست نہیں۔"
                ),
                Block.RecitationBlock(LearnRecitations.duaAfterWudu),
                Block.Heading("What breaks wudu (Hanafi)", "وضو کیا توڑتا ہے (حنفی)"),
                Block.Text(
                    "Anything that exits the private parts (urine, stool, gas, etc.); flowing blood or pus from the body; vomiting a mouthful; deep sleep or fainting; loud laughter inside a prayer with ruku and sajdah; sleeping lying down or leaning. Touching the spouse or the private part does not break wudu in Hanafi fiqh.",
                    "شرمگاہ سے کچھ نکلنا (پیشاب، پاخانہ، ہوا وغیرہ)؛ جسم سے بہتا ہوا خون یا پیپ؛ مُکمل منہ قے کرنا؛ گہری نیند یا بےہوشی؛ رکوع اور سجدے والی نماز میں بلند آواز سے ہنسنا؛ لیٹ کر یا سہارے لے کر سونا۔ شریک حیات یا شرمگاہ کو چھونا حنفی فقہ میں وضو نہیں توڑتا۔"
                ),
                Block.Heading("Ghusl (full bath)", "غسل"),
                Block.Text(
                    "Required after sexual impurity (janabah) and after the end of menstruation or postnatal bleeding. Fard acts: rinse the mouth, rinse the nose, wash the entire body so water reaches every part. Sunnah: begin with Bismillah, wash the hands and private parts, make wudu, then pour water over the head and the body, right side first.",
                    "غسل جنابت کے بعد اور حیض یا نفاس کے ختم ہونے پر واجب ہے۔ فرض کام: کلی کرنا، ناک میں پانی لے کر صاف کرنا، پورے جسم کو دھونا تاکہ ہر جگہ پانی پہنچے۔ سنت: بسم اللہ سے شروع کرنا، ہاتھ اور شرمگاہ دھونا، وضو کرنا، پھر سر اور جسم پر پانی ڈالنا، پہلے دائیں طرف۔"
                ),
                Block.Heading("Tayammum (dry ablution)", "تیمم"),
                Block.Text(
                    "Allowed when water is unavailable or harmful to use (illness). Intend, strike both palms on clean earth (or an earth-based surface such as clean stone, wall, soil), wipe the whole face; strike again and wipe both arms up to and including the elbows.",
                    "جب پانی نہ ہو یا استعمال مضر ہو (بیماری) تو تیمم جائز ہے۔ نیت کریں، دونوں ہتھیلیاں صاف مٹی (یا صاف پتھر، دیوار، مٹی) پر ماریں، پورا چہرہ مسح کریں؛ دوبارہ ماریں اور دونوں بازو کہنیوں سمیت مسح کریں۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "How many fard acts does wudu have?", "وضو کے فرض کتنے ہیں؟",
                    listOf("Three", "Four", "Five"), listOf("تین", "چار", "پانچ"), 1
                ),
                QuizQuestion(
                    "Which of these breaks wudu in Hanafi fiqh?", "حنفی فقہ میں ان میں سے کیا وضو توڑتا ہے؟",
                    listOf("Touching the spouse", "Deep sleep", "Washing the face"),
                    listOf("شریک حیات کو چھونا", "گہری نیند", "چہرہ دھونا"), 1
                ),
                QuizQuestion(
                    "What is tayammum for?", "تیمم کس لیے ہے؟",
                    listOf("When water is unavailable or harmful", "Instead of every wudu", "Only while travelling"),
                    listOf("جب پانی نہ ہو یا مضر ہو", "ہر وضو کی جگہ", "صرف سفر میں"), 0
                )
            )
        ),

        Lesson(
            id = "conditions",
            order = 4,
            titleEn = "Conditions before Salah",
            titleUr = "نماز کی شرائط",
            summaryEn = "Six things to check before you start: clean body, clean clothes, clean place, wudu, covering, qibla, time and intention.",
            summaryUr = "شروع کرنے سے پہلے چیک کریں: پاک جسم، پاک کپڑے، پاک جگہ، وضو، پوشش، قبلہ، وقت اور نیت۔",
            blocks = listOf(
                Block.Steps(
                    items = listOf(
                        "Body, clothes and place are clean (free of najasah).",
                        "Wudu (or ghusl when needed).",
                        "Covering (satr): men from navel to knees at minimum — cover more in practice; women cover the whole body except the face, hands and feet.",
                        "Facing the Qibla.",
                        "The time of the prayer has entered.",
                        "Intention (niyyah): in the heart. Saying it aloud is not required."
                    ),
                    itemsUr = listOf(
                        "جسم، کپڑے اور جگہ پاک ہوں (نجاست سے پاک)۔",
                        "وضو (اور ضرورت ہو تو غسل)۔",
                        "پوشش (ستر): مرد کم از کم ناب سے گھٹنے تک — عمل میں زیادہ ڈھانپیں؛ عورت چہرہ، ہاتھ اور پاؤں کے علاوہ پورے جسم کو ڈھانپے۔",
                        "قبلہ کی طرف منہ کرنا۔",
                        "نماز کا وقت ہو چکا ہو۔",
                        "نیت: دل میں۔ بلند آواز سے کہنا ضروری نہیں۔"
                    )
                ),
                Block.Callout(
                    titleEn = "A gentle reminder",
                    textEn = "If one of these is hard for you right now, do what you can. Allah does not burden a soul beyond what it can bear.",
                    titleUr = "نرم یاددہانی",
                    textUr = "اگر ابھی کوئی شرط مشکل ہو تو جتنا ممکن ہو کریں۔ اللہ کسی جان پر اس کی استطاعت سے زیادہ بوجھ نہیں ڈالتا۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "How many conditions are listed before salah?", "نماز سے پہلے کتنی شرائط ہیں؟",
                    listOf("Four", "Five", "Six"), listOf("چار", "پانچ", "چھ"), 2
                ),
                QuizQuestion(
                    "Where is the intention made?", "نیت کہاں کی جاتی ہے؟",
                    listOf("In the heart", "Out loud", "By writing it"), listOf("دل میں", "بلند آواز سے", "لکھ کر"), 0
                )
            )
        ),

        Lesson(
            id = "adhan_iqamah",
            order = 5,
            titleEn = "Adhan & Iqamah",
            titleUr = "اذان اور اقامہ",
            summaryEn = "The call to prayer and the call that begins it. Learn the words, their meaning, and the dua after the adhan.",
            summaryUr = "نماز کے لیے بلانے کی اذان اور نماز شروع کرنے کا اقامہ۔ الفاظ، معنی اور اذان کے بعد کی دعا سیکھیں۔",
            blocks = listOf(
                Block.RecitationBlock(LearnRecitations.adhan),
                Block.Text(
                    "For Fajr only, after \"Hayya 'alal falah\" say: الصَّلَاةُ خَيْرٌ مِنَ النَّوْمِ (prayer is better than sleep), two times.",
                    "صرف فجر میں \"حَيَّ عَلَى الْفَلَاحِ\" کے بعد کہیں: الصَّلَاةُ خَيْرٌ مِنَ النَّوْمِ (نماز نیند سے بہتر ہے)، دو مرتبہ۔"
                ),
                Block.Heading("Iqamah (Hanafi)", "اقامہ (حنفی)"),
                Block.Text(
                    "The same words as the adhan, with قَدْ قَامَتِ الصَّلَاةُ (Qad qamatis-salah — \"the prayer has begun\") said twice after \"Hayya 'alal falah\". Said quickly, just before the congregation begins.",
                    "اذان کے ہی الفاظ، البتہ \"حَيَّ عَلَى الْفَلَاحِ\" کے بعد قَدْ قَامَتِ الصَّلَاةُ (نماز کھڑی ہو گئی) دو مرتبہ کہیں۔ جلدی سے، جماعت شروع ہونے سے کچھ پہلے کہا جاتا ہے۔"
                ),
                Block.RecitationBlock(LearnRecitations.duaAfterAdhan),
                Block.DifferenceNote(
                    titleEn = "A note on differences",
                    titleUr = "اختلاف کا نوٹ",
                    textEn = "The number of takbirs at the start and the repetition of the witness phrases differ between schools. This guide follows the Hanafi practice common in Pakistan. Other schools have their own sound positions — we do not declare them wrong.",
                    textUr = "ابتدائی تکبیروں کی تعداد اور گواہی کے جملوں کی تکرار مکاتبِ فکر میں مختلف ہے۔ یہ رہنمائی پاکستان میں عام حنفی عمل کے مطابق ہے۔ دیگر مکاتب کے اپنے صحیح موقف ہیں — ہم انہیں غلط نہیں کہتے۔"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    "How many times is the takbir said at the start of the adhan?", "اذان کے آغاز میں تکبیر کتنی مرتبہ کہی جاتی ہے؟",
                    listOf("Two", "Four", "Six"), listOf("دو", "چار", "چھ"), 1
                ),
                QuizQuestion(
                    "Which phrase is added for Fajr?", "فجر کے لیے کون سا جملہ اضافہ ہے؟",
                    listOf("As-salatu khairum-minan-naum", "Hayya 'alas-salah", "Qad qamatis-salah"),
                    listOf("الصَّلَاةُ خَيْرٌ مِنَ النَّوْمِ", "حَيَّ عَلَى الصَّلَاةِ", "قَدْ قَامَتِ الصَّلَاةُ"), 0
                ),
                QuizQuestion(
                    "When is the Iqamah said?", "اقامہ کب کہا جاتا ہے؟",
                    listOf("The night before", "Just before the congregation begins", "After the prayer"),
                    listOf("رات قبلہ", "جماعت شروع ہونے سے کچھ پہلے", "نماز کے بعد"), 1
                )
            )
        )
    )
}
