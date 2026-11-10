package com.salahgarden.app.learn

/**
 * Every Arabic recitation in the guide, keyed by id.
 *
 * DEVELOPER NOTE — ACCURACY: Arabic text here must be proof-read against a
 * printed Mushaf and reviewed by a qualified scholar (e.g. a local
 * Dar-ul-Uloom) before release. Do not regenerate, paraphrase or "fix" Arabic
 * from memory. Surah text not included below must be copied from a verified
 * digital Mushaf (King Fahd Complex text).
 */

object LearnRecitations {

    val thana = Recitation(
        id = "thana",
        arabic = "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ وَتَبَارَكَ اسْمُكَ وَتَعَالَىٰ جَدُّكَ وَلَا إِلَٰهَ غَيْرُكَ",
        translit = "Subhanaka Allahu mma wa bihamdika wa tabarakasmuka wa ta'ala jadduka wa la ilaha ghayruk",
        meaningEn = "Glory be to You, O Allah, and praise. Blessed is Your name and exalted is Your majesty; there is no god but You.",
        meaningUr = "تیری ذات پاک ہے اے اللہ، اور تیری حمد ہے۔ تیرا نام بابرکت ہے اور تیرا جلال بلند ہے؛ کوئی معبود نہیں سوائے تیرے۔",
        audioFile = "audio/thana.mp3"
    )

    val taawwudh = Recitation(
        id = "taawwudh",
        arabic = "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ",
        translit = "A'udhu billahi minash-shaytanir-rajim",
        meaningEn = "I seek refuge with Allah from the accursed Shaytan.",
        meaningUr = "میں اللہ کی پناہ مانگتا ہوں مردود شیطان سے۔",
        audioFile = "audio/taawwudh.mp3"
    )

    val basmalah = Recitation(
        id = "basmalah",
        arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
        translit = "Bismillahir-Rahmanir-Rahim",
        meaningEn = "In the name of Allah, the Most Gracious, the Most Merciful.",
        meaningUr = "اللہ کے نام سے جو نہایت مہربان رحم کرنے والا ہے۔",
        audioFile = "audio/basmalah.mp3"
    )

    val fatiha = Recitation(
        id = "fatiha",
        arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ\n" +
            "الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "مَالِكِ يَوْمِ الدِّينِ\n" +
            "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ\n" +
            "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ\n" +
            "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
        translit = "Alhamdu lillahi Rabbil 'alamin. Ar-Rahmanir-Rahim. Maliki yawmid-din. " +
            "Iyyaka na'budu wa iyyaka nasta'in. Ihdinas-siratal-mustaqim. " +
            "Siratal-ladheena an'amta 'alayhim ghayril-maghdubi 'alayhim walad-dallin.",
        meaningEn = "All praise is for Allah, Lord of all worlds. The Most Gracious, the Most Merciful. " +
            "Master of the Day of Judgment. You alone we worship, and You alone we ask for help. " +
            "Guide us on the straight path — the path of those You have blessed, not of those who earned anger, nor of those who went astray.",
        meaningUr = "تمام تعریفیں اللہ کے لیے ہیں جو تمام جہانوں کا رب ہے۔ نہایت مہربان رحم کرنے والا ہے۔ " +
            "جزا و سزا کے دن کا مالک ہے۔ ہم صرف تیری عبادت کرتے ہیں اور صرف تجھ سے مدد مانگتے ہیں۔ " +
            "ہمیں سیدھے راستے کی ہدایت دے، ان لوگوں کے راستے جن پر تو نے نعمتیں نازل کیں، نہ ان کے جن پر غضب ہوا اور نہ گمراہوں کے۔",
        source = "Surah Al-Fatiha (1:1–7)",
        audioFile = "audio/fatiha.mp3"
    )

    val ikhlas = Recitation(
        id = "ikhlas",
        arabic = "قُلْ هُوَ اللَّهُ أَحَدٌ ۝ اللَّهُ الصَّمَدُ ۝ لَمْ يَلِدْ وَلَمْ يُولَدْ ۝ وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
        translit = "Qul huwallahu ahad. Allahus-samad. Lam yalid wa lam yulad. Wa lam yakul-lahu kufuwan ahad.",
        meaningEn = "Say: He is Allah, the One. Allah, the Eternal Refuge. He neither begets nor is begotten, nor is there to Him any equivalent.",
        meaningUr = "کہہ دو: وہ اللہ ایک ہے۔ اللہ بےنیاز ہے۔ نہ کسی کو جنا اور نہ کسی کو جنا گیا، اور نہیں کوئی اس جیسا۔",
        source = "Surah Al-Ikhlas (112:1–4)",
        audioFile = "audio/ikhlas.mp3"
    )

    val kawthar = Recitation(
        id = "kawthar",
        arabic = "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ ۝ فَصَلِّ لِرَبِّكَ وَانْحَرْ ۝ إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ",
        translit = "Inna a'taynakal-kawthar. Fa-salli li-Rabbika wanhar. Inna shani'aka huwal-abtar.",
        meaningEn = "Indeed, We have given you abundance. So pray to your Lord and sacrifice. Indeed, your enemy is the one cut off.",
        meaningUr = "بےشک ہم نے تجھے کوثر دیے۔ پس اپنے رب کے لیے نماز پڑھ اور قربانی کر۔ بےشک تیرا دشمن ہی بےاصل ہے۔",
        source = "Surah Al-Kawthar (108:1–3)",
        audioFile = "audio/kawthar.mp3"
    )

    val ruku = Recitation(
        id = "ruku",
        arabic = "سُبْحَانَ رَبِّيَ الْعَظِيمِ",
        translit = "Subhana Rabbiyal 'Azim",
        meaningEn = "Glory to my Lord, the Magnificent. (Three times.)",
        meaningUr = "میرے رب کی ذات پاک ہے جو عظمت والا ہے۔ (تین مرتبہ۔)",
        audioFile = "audio/ruku.mp3"
    )

    val sama = Recitation(
        id = "sama",
        arabic = "سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ",
        translit = "Sami'Allahu liman hamidah",
        meaningEn = "Allah hears the one who praises Him.",
        meaningUr = "اللہ نے سنا اس شخص نے جس نے تعریف کی۔",
        audioFile = "audio/sama.mp3"
    )

    val rabbanaLakalHamd = Recitation(
        id = "rabbana_lakal_hamd",
        arabic = "رَبَّنَا لَكَ الْحَمْدُ",
        translit = "Rabbana lakal hamd",
        meaningEn = "Our Lord, for You is all praise.",
        meaningUr = "اے ہمارے رب، تمام حمد تیرے لیے ہے۔",
        audioFile = "audio/rabbana_lakal_hamd.mp3"
    )

    val sajdah = Recitation(
        id = "sajdah",
        arabic = "سُبْحَانَ رَبِّيَ الْأَعْلَىٰ",
        translit = "Subhana Rabbiyal A'la",
        meaningEn = "Glory to my Lord, the Most High. (Three times.)",
        meaningUr = "میرے رب کی ذات پاک ہے جو بلند ترین ہے۔ (تین مرتبہ۔)",
        audioFile = "audio/sajdah.mp3"
    )

    val tashahhud = Recitation(
        id = "tashahhud",
        arabic = "التَّحِيَّاتُ لِلَّهِ وَالصَّلَوَاتُ وَالطَّيِّبَاتُ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَىٰ عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ",
        translit = "At-tahiyyatu lillahi was-salawatu wat-tayyibat. As-salamu 'alayka ayyuhan-nabiyyu wa rahmatullahi wa barakatuh. As-salamu 'alayna wa 'ala 'ibadillahis-salihin. Ash-hadu an la ilaha illallahu wa ash-hadu anna Muhammadan 'abduhu wa rasuluh.",
        meaningEn = "All greetings, prayers and good things are for Allah. Peace be upon you, O Prophet, and Allah's mercy and blessings. Peace be upon us and upon Allah's righteous servants. I bear witness that there is no god but Allah, and I bear witness that Muhammad is His servant and Messenger.",
        meaningUr = "تمام تحیات، نمازیں اور پاکیزہ چیزیں اللہ کے لیے ہیں۔ اے نبی، آپ پر سلام ہو اور اللہ کی رحمت اور برکتیں۔ ہم پر اور اللہ کے نیک بندوں پر سلام ہو۔ میں گواہی دیتا ہوں کہ اللہ کے سوا کوئی معبود نہیں اور میں گواہی دیتا ہوں کہ محمد ﷺ اللہ کے بندے اور رسول ہیں۔",
        audioFile = "audio/tashahhud.mp3"
    )

    val durood = Recitation(
        id = "durood",
        arabic = "اللَّهُمَّ صَلِّ عَلَىٰ مُحَمَّدٍ وَعَلَىٰ آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَىٰ إِبْرَاهِيمَ وَعَلَىٰ آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ ۝ اللَّهُمَّ بَارِكْ عَلَىٰ مُحَمَّدٍ وَعَلَىٰ آلِ مُحَمَّدٍ كَمَا بَارَكْتَ عَلَىٰ إِبْرَاهِيمَ وَعَلَىٰ آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ",
        translit = "Allahumma salli 'ala Muhammadin wa 'ala ali Muhammadin kama sallayta 'ala Ibrahima wa 'ala ali Ibrahim, innaka Hamidum-Majid. Allahumma barik 'ala Muhammadin wa 'ala ali Muhammadin kama barakta 'ala Ibrahima wa 'ala ali Ibrahim, innaka Hamidum-Majid.",
        meaningEn = "O Allah, send Your grace upon Muhammad and the family of Muhammad, as You sent it upon Ibrahim and the family of Ibrahim; You are the Praiseworthy, the Glorious. O Allah, bless Muhammad and the family of Muhammad, as You blessed Ibrahim and the family of Ibrahim; You are the Praiseworthy, the Glorious.",
        meaningUr = "اے اللہ، محمد ﷺ اور آل محمد پر رحمت نازل فرما جیسے ابراہیم اور آل ابراہیم پر نازل فرمائی؛ بےشک تو تعریف والا جلیل ہے۔ اے اللہ، محمد ﷺ اور آل محمد پر برکت نازل فرما جیسے ابراہیم اور آل ابراہیم پر نازل فرمائی؛ بےشک تو تعریف والا جلیل ہے۔",
        audioFile = "audio/durood.mp3"
    )

    val duaMasura = Recitation(
        id = "dua_masura",
        arabic = "اللَّهُمَّ إِنِّي ظَلَمْتُ نَفْسِي ظُلْمًا كَثِيرًا وَلَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ فَاغْفِرْ لِي مَغْفِرَةً مِنْ عِنْدِكَ وَارْحَمْنِي إِنَّكَ أَنْتَ الْغَفُورُ الرَّحِيمُ",
        translit = "Allahumma inni zalamtu nafsi zulman kathira wa la yaghfirud-dhunuba illa anta, faghfir li maghfiratan min 'indika warhamni, innaka antal-Ghafurur-Rahim.",
        meaningEn = "O Allah, I have greatly wronged myself, and none forgives sins but You. So forgive me with forgiveness from You and have mercy on me; You are the Forgiving, the Merciful.",
        meaningUr = "اے اللہ، میں نے اپنے آپ پر بہت ظلم کیا اور تیرے سوا کوئی گناہ نہیں معاف کرتا، پس مجھے اپنی طرف سے مغفرت دے اور مجھ پر رحم فرما؛ بےشک تو بخشنے والا رحم کرنے والا ہے۔",
        audioFile = "audio/dua_masura.mp3"
    )

    val salam = Recitation(
        id = "salam",
        arabic = "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ",
        translit = "As-salamu 'alaykum wa rahmatullah",
        meaningEn = "Peace and the mercy of Allah be upon you.",
        meaningUr = "تم پر اللہ کی سلامتی اور اس کی رحمت نازل ہو۔",
        audioFile = "audio/salam.mp3"
    )

    val duaAfterWudu = Recitation(
        id = "dua_after_wudu",
        arabic = "أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ",
        translit = "Ash-hadu an la ilaha illallahu wahdahu la sharika lah, wa ash-hadu anna Muhammadan 'abduhu wa rasuluh.",
        meaningEn = "I bear witness that there is no god but Allah alone, with no partner, and I bear witness that Muhammad is His servant and Messenger.",
        meaningUr = "میں گواہی دیتا ہوں کہ اللہ کے سوا کوئی معبود نہیں، وہ اکیلا ہے اس کا کوئی شریک نہیں، اور میں گواہی دیتا ہوں کہ محمد ﷺ اس کے بندے اور رسول ہیں۔",
        audioFile = "audio/dua_after_wudu.mp3"
    )

    val adhan = Recitation(
        id = "adhan",
        arabic = "اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ\n" +
            "أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ (×٢)\n" +
            "أَشْهَدُ أَنَّ مُحَمَّدًا رَسُولُ اللَّهِ (×٢)\n" +
            "حَيَّ عَلَى الصَّلَاةِ (×٢)\n" +
            "حَيَّ عَلَى الْفَلَاحِ (×٢)\n" +
            "اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ\n" +
            "لَا إِلَٰهَ إِلَّا اللَّهُ",
        translit = "Allahu Akbar (×٤) · Ash-hadu an la ilaha illallah (×٢) · " +
            "Ash-hadu anna Muhammadar-rasulullah (×٢) · Hayya 'alas-salah (×٢) · " +
            "Hayya 'alal-falah (×٢) · Allahu Akbar (×٢) · La ilaha illallah",
        meaningEn = "Allah is the Greatest; I bear witness there is no god but Allah; I bear witness Muhammad is the Messenger of Allah; come to prayer; come to success; Allah is the Greatest; there is no god but Allah.",
        meaningUr = "اللہ سب سے بڑا ہے؛ میں گواہی دیتا ہوں کہ اللہ کے سوا کوئی معبود نہیں؛ میں گواہی دیتا ہوں کہ محمد ﷺ اللہ کے رسول ہیں؛ نماز کی طرف آؤ؛ فلاح کی طرف آؤ؛ اللہ سب سے بڑا ہے؛ اللہ کے سوا کوئی معبود نہیں۔",
        audioFile = "audio/adhan.mp3"
    )

    val qadQamatis = Recitation(
        id = "qad_qamatis_salah",
        arabic = "قَدْ قَامَتِ الصَّلَاةُ",
        translit = "Qad qamatis-salah",
        meaningEn = "The prayer has begun.",
        meaningUr = "نماز کھڑی ہو گئی۔",
        audioFile = "audio/qad_qamatis_salah.mp3"
    )

    val duaAfterAdhan = Recitation(
        id = "dua_after_adhan",
        arabic = "اللَّهُمَّ رَبَّ هَٰذِهِ الدَّعْوَةِ التَّامَّةِ وَالصَّلَاةِ الْقَائِمَةِ آتِ مُحَمَّدًا الْوَسِيلَةَ وَالْفَضِيلَةَ وَابْعَثْهُ مَقَامًا مَحْمُودًا الَّذِي وَعَدْتَهُ",
        translit = "Allahumma rabba hadhihid-da'watit-tammati was-salatil-qa'imah, ati Muhammadan al-wasilata wal-fadilah, wab'athhu maqaman mahmudan alladhi wa'adtah.",
        meaningEn = "O Allah, Lord of this perfect call and established prayer, grant Muhammad the wasilah and excellence, and raise him to the praised station You have promised him.",
        meaningUr = "اے اللہ، اس مکمل اعلان اور قائم ہونے والی نماز کے رب، محمد ﷺ کو وسيلة اور فضیلت عطا فرما اور انہیں اس محمود مقام پر پہنچا جس کا تو نے وعدہ کیا ہے۔",
        audioFile = "audio/dua_after_adhan.mp3"
    )

    val duaQunoot = Recitation(
        id = "dua_qunoot",
        arabic = "اللَّهُمَّ إِنَّا نَسْتَعِينُكَ وَنَسْتَغْفِرُكَ وَنُؤْمِنُ بِكَ وَنَتَوَكَّلُ عَلَيْكَ وَنُثْنِي عَلَيْكَ الْخَيْرَ وَنَشْكُرُكَ وَلَا نَكْفُرُكَ وَنَخْلَعُ وَنَتْرُكُ مَنْ يَفْجُرُكَ ۝ اللَّهُمَّ إِيَّاكَ نَعْبُدُ وَلَكَ نُصَلِّي وَنَسْجُدُ وَإِلَيْكَ نَسْعَىٰ وَنَحْفِدُ وَنَرْجُو رَحْمَتَكَ وَنَخْشَىٰ عَذَابَكَ إِنَّ عَذَابَكَ بِالْكُفَّارِ مُلْحِقٌ",
        translit = "Allahumma inna nasta'inuka wa nastaghfiruka wa nu'minu bika wa natawakkalu 'alayka wa nuthni 'alaykal-khaira wa nashkuruka wa la nakfuruka wa nakhla'u wa natruku man yafjuruk. Allahumma iyyaka na'budu wa laka nusalli wa nasjudu wa ilayka nas'a wa nahfidu wa narju rahmataka wa nakhsha 'adhabak, inna 'adhabaka bil-kuffari mulhiq.",
        meaningEn = "O Allah, we seek Your help and Your forgiveness, we believe in You and trust in You, we praise You and thank You and are not ungrateful to You, and we leave and forsake the one who disobeys You. O Allah, You alone we worship, and to You we pray and prostrate; for Your sake we strive and strive, we hope for Your mercy and fear Your punishment; Your punishment will surely reach the disbelievers.",
        meaningUr = "اے اللہ، ہم تیری مدد چاہتے ہیں اور تیرا بخش چاہتے ہیں، ہم تجھ پر ایمان لاتے ہیں اور تجھ پر بھروسہ کرتے ہیں، تیری تعریف کرتے ہیں اور تیرا شکر ادا کرتے ہیں اور تیرا ناشکری نہیں کرتے، اور جس نے تیری نافرمانی کی اسے چھوڑ دیتے ہیں۔ اے اللہ، ہم صرف تیری عبادت کرتے ہیں اور تیرے لیے نماز پڑھتے ہیں اور سجدہ کرتے ہیں؛ تیرے لیے کوشش کرتے ہیں، تیری رحمت کی امید رکھتے ہیں اور تیرے عذاب سے ڈرتے ہیں؛ تیرا عذاب کافروں تک پہنچے گا۔",
        audioFile = "audio/dua_qunoot.mp3"
    )

    val afterSalam = Recitation(
        id = "after_salam",
        arabic = "اللَّهُمَّ أَنْتَ السَّلَامُ وَمِنْكَ السَّلَامُ تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ",
        translit = "Allahumma antas-salamu wa minkas-salamu tabarakta ya dhal-jalali wal-ikram",
        meaningEn = "O Allah, You are Peace and from You comes peace; blessed are You, O Owner of majesty and honour.",
        meaningUr = "اے اللہ، تو سلامتی ہے اور تیری طرف سے سلامتی ہے؛ بابرکت ہے تو اے جلال اور عزت والے۔",
        audioFile = "audio/after_salam.mp3"
    )

    val astaghfirullah = Recitation(
        id = "astaghfirullah",
        arabic = "أَسْتَغْفِرُ اللَّهَ",
        translit = "Astaghfirullah",
        meaningEn = "I seek forgiveness from Allah. (Three times.)",
        meaningUr = "میں اللہ سے مغفرت چاہتا ہوں۔ (تین مرتبہ۔)",
        audioFile = "audio/astaghfirullah.mp3"
    )

    /** Every recitation, keyed by id. */
    val all: Map<String, Recitation> = listOf(
        thana, taawwudh, basmalah, fatiha, ikhlas, kawthar, ruku, sama,
        rabbanaLakalHamd, sajdah, tashahhud, durood, duaMasura, salam,
        adhan, qadQamatis, duaAfterAdhan, duaAfterWudu, duaQunoot, afterSalam, astaghfirullah
    ).associateBy { it.id }

    fun byId(id: String): Recitation? = all[id]
}
