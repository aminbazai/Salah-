package com.salahgarden.app.learn

/**
 * Prayer structures and the "pray along" sequence builder.
 *
 * This is the one piece of the guide with real logic, so it is kept pure and
 * unit-tested: given a prayer and a rakat count it produces the ordered list
 * of positions a reader steps through.
 */

object LearnPositions {
    val takbir = Position(
        id = "takbir",
        nameEn = "Takbir Tahrimah",
        nameUr = "تکبیر تحریمہ",
        recitationIds = listOf("basmalah"),
        hands = "Raise both hands to the ears (men) or shoulders (women), then fold them.",
        eyes = "Look at the place of sajdah.",
        feet = "Stand upright, feet about four fingers apart.",
        tip = "The prayer begins here. Leave the world outside for a few minutes.",
        image = "img/takbir.png"
    )

    val qiyam = Position(
        id = "qiyam",
        nameEn = "Qiyam · standing",
        nameUr = "قیام",
        recitationIds = listOf("thana", "taawwudh", "basmalah", "fatiha", "ikhlas"),
        hands = "Right hand over the left, below the navel (men); on the chest (women).",
        eyes = "Look at the place of sajdah.",
        feet = "Stand still and calm.",
        tip = "Recite slowly. If you have not memorised a surah yet, three short verses are enough.",
        image = "img/qiyam.png"
    )

    val ruku = Position(
        id = "ruku",
        nameEn = "Ruku · bowing",
        nameUr = "رکوع",
        recitationIds = listOf("ruku"),
        hands = "Hands on the knees, fingers spread.",
        eyes = "Look at your feet.",
        feet = "Back straight and level, knees straight.",
        tip = "Say Subhana Rabbiyal 'Azim three times. Be calm — do not rush.",
        image = "img/ruku.png"
    )

    val qawmah = Position(
        id = "qawmah",
        nameEn = "Qawmah · standing up",
        nameUr = "قومہ",
        recitationIds = listOf("sama", "rabbana_lakal_hamd"),
        hands = "Fold the hands again as in qiyam.",
        eyes = "Look at the place of sajdah.",
        feet = "Stand up fully and pause for a breath.",
        tip = "Stand straight and still for a moment before going down.",
        image = "img/qawmah.png"
    )

    val sajdah = Position(
        id = "sajdah",
        nameEn = "Sajdah · prostration",
        nameUr = "سجدہ",
        recitationIds = listOf("sajdah"),
        hands = "Hands on the ground beside the ears; fingers together facing the qibla.",
        eyes = "Look towards the tip of the nose.",
        feet = "Toes of both feet on the ground, pointing to the qibla.",
        tip = "Say Subhana Rabbiyal A'la three times. This is the closest you are to Allah — stay a moment.",
        image = "img/sajdah.png"
    )

    val jalsa = Position(
        id = "jalsa",
        nameEn = "Jalsa · sitting between sajdah",
        nameUr = "جلسہ",
        recitationIds = emptyList(),
        hands = "Hands on the thighs.",
        eyes = "Look at the place of sajdah.",
        feet = "Sit on the left foot, right foot upright.",
        tip = "Pause here at least the time of one SubhanAllah.",
        image = "img/jalsa.png"
    )

    val qadah = Position(
        id = "qadah",
        nameEn = "Qa'dah · final sitting",
        nameUr = "قعدہ",
        recitationIds = listOf("tashahhud", "durood", "dua_masura"),
        hands = "Hands on the thighs. Raise the index finger at 'ash-hadu an la ilaha'.",
        eyes = "Look at the place of sajdah.",
        feet = "Sit on the left hip, both feet out to the right.",
        tip = "Recite Tashahhud, then Durood, then the dua.",
        image = "img/qadah.png"
    )

    val salam = Position(
        id = "salam",
        nameEn = "Salam · finishing",
        nameUr = "سلام",
        recitationIds = listOf("salam"),
        hands = "Hands stay on the thighs.",
        eyes = "Turn the head to the right shoulder, then the left.",
        feet = "Keep sitting.",
        tip = "Your prayer is complete. Take a breath, make du'a, and smile — you watered your garden.",
        image = "img/salam.png"
    )

    val qunoot = Position(
        id = "qunoot",
        nameEn = "Dua Qunoot",
        nameUr = "دعا قنوت",
        recitationIds = listOf("dua_qunoot"),
        hands = "Raise both hands to the ears saying Allahu Akbar, then fold them.",
        eyes = "Look at the place of sajdah.",
        feet = "Standing.",
        tip = "In the third rakat of Witr. If you have not memorised it yet, a short approved fallback is enough.",
        image = "img/qunoot.png"
    )

    val all: Map<String, Position> = listOf(
        takbir, qiyam, ruku, qawmah, sajdah, jalsa, qadah, salam, qunoot
    ).associateBy { it.id }

    fun byId(id: String): Position? = all[id]
}

object LearnPrayerSpecs {

    val fajr = PrayerSpec(
        "fajr", "Fajr", "فجر", "From true dawn until just before sunrise",
        listOf(RakatUnit("Sunnah Mu'akkadah", 2), RakatUnit("Fard", 2, wajib = true))
    )
    val zuhr = PrayerSpec(
        "zuhr", "Zuhr", "ظہر", "After the sun passes its zenith until Asr begins",
        listOf(
            RakatUnit("Sunnah Mu'akkadah", 4),
            RakatUnit("Fard", 4, wajib = true),
            RakatUnit("Sunnah Mu'akkadah", 2),
            RakatUnit("Nafl", 2)
        )
    )
    val asr = PrayerSpec(
        "asr", "Asr", "عصر", "From Asr time until just before sunset",
        listOf(RakatUnit("Sunnah Ghair Mu'akkadah", 4), RakatUnit("Fard", 4, wajib = true))
    )
    val maghrib = PrayerSpec(
        "maghrib", "Maghrib", "مغرب", "From sunset until the red twilight disappears",
        listOf(
            RakatUnit("Fard", 3, wajib = true),
            RakatUnit("Sunnah Mu'akkadah", 2),
            RakatUnit("Nafl", 2)
        )
    )
    val isha = PrayerSpec(
        "isha", "Isha", "عشاء", "From the disappearance of twilight until before dawn",
        listOf(
            RakatUnit("Sunnah Ghair Mu'akkadah", 4),
            RakatUnit("Fard", 4, wajib = true),
            RakatUnit("Sunnah Mu'akkadah", 2),
            RakatUnit("Nafl", 2),
            RakatUnit("Witr", 3, wajib = true),
            RakatUnit("Nafl", 2)
        )
    )
    val jumuah = PrayerSpec(
        "jumuah", "Jumu'ah", "جمعہ", "Friday, replaces Zuhr",
        listOf(
            RakatUnit("Sunnah Mu'akkadah", 4),
            RakatUnit("Fard", 2, wajib = true),
            RakatUnit("Sunnah Mu'akkadah", 4),
            RakatUnit("Sunnah Mu'akkadah", 2),
            RakatUnit("Nafl", 2)
        )
    )

    val all = listOf(fajr, zuhr, asr, maghrib, isha, jumuah)

    /** The Witr prayer on its own, for the walkthrough. */
    val witr = PrayerSpec(
        "witr", "Witr", "وتر", "After Isha",
        listOf(RakatUnit("Witr", 3, wajib = true))
    )

    fun byId(id: String): PrayerSpec? = all.firstOrNull { it.id == id }

    /** Total rakat of a prayer, including sunnah and nafl. */
    fun totalRakat(spec: PrayerSpec): Int = spec.units.sumOf { it.rakat }

    /** Only the obligatory (fard/wajib) rakat. */
    fun fardRakat(spec: PrayerSpec): Int =
        spec.units.filter { it.wajib }.sumOf { it.rakat }

    /** Total fard rakat across all five daily prayers (Witr is wajib but separate). */
    val dailyFardTotal: Int = fajr.units.filter { it.wajib }.sumOf { it.rakat } +
        zuhr.units.filter { it.wajib }.sumOf { it.rakat } +
        asr.units.filter { it.wajib }.sumOf { it.rakat } +
        maghrib.units.filter { it.wajib }.sumOf { it.rakat } +
        isha.units.filter { it.wajib && it.type != "Witr" }.sumOf { it.rakat }
}

/** One step in the walkthrough. */
data class WalkthroughStep(
    val position: Position,
    val rakatNumber: Int,
    /** True when this rakat's standing block should include Thana and a surah. */
    val fullStanding: Boolean
)

object WalkthroughBuilder {

    /**
     * Build the ordered positions for [rakat] rakat of [spec].
     *
     * - Rakat 1: Takbir, standing with Thana + Fatiha + surah, ruku, qawmah,
     *   two sajdahs with jalsa between.
     * - Middle rakat: standing with Fatiha + surah, ruku, qawmah, two sajdahs.
     * - Every rakat except the last ends in a sitting with Tashahhud only.
     * - The last rakat ends in the final sitting (Tashahhud + Durood + dua) and Salam.
     * - Witr inserts Dua Qunoot in the third rakat before ruku.
     */
    fun build(spec: PrayerSpec, rakat: Int): List<WalkthroughStep> {
        require(rakat > 0) { "rakat must be positive" }
        val steps = mutableListOf<WalkthroughStep>()
        val isWitr = spec.id == "witr" || spec.units.any { it.type == "Witr" }

        for (raka in 1..rakat) {
            val isFirst = raka == 1
            val isLast = raka == rakat

            if (isFirst) {
                steps += WalkthroughStep(LearnPositions.takbir, raka, fullStanding = true)
            }

            // Every rakat stands: the first carries Thana, Ta'awwudh, Fatiha and a
            // surah; later rakat carry Bismillah, Fatiha and (in sunnah/nafl) a surah.
            steps += WalkthroughStep(LearnPositions.qiyam, raka, fullStanding = true)

            if (isWitr && raka == 3) {
                steps += WalkthroughStep(LearnPositions.qunoot, raka, fullStanding = false)
            }

            steps += WalkthroughStep(LearnPositions.ruku, raka, fullStanding = false)
            steps += WalkthroughStep(LearnPositions.qawmah, raka, fullStanding = false)
            steps += WalkthroughStep(LearnPositions.sajdah, raka, fullStanding = false)
            steps += WalkthroughStep(LearnPositions.jalsa, raka, fullStanding = false)
            steps += WalkthroughStep(LearnPositions.sajdah, raka, fullStanding = false)

            if (isLast) {
                steps += WalkthroughStep(LearnPositions.qadah, raka, fullStanding = false)
                steps += WalkthroughStep(LearnPositions.salam, raka, fullStanding = false)
            } else {
                // Middle sitting: Tashahhud only, then stand for the next rakat.
                steps += WalkthroughStep(LearnPositions.qadah, raka, fullStanding = false)
            }
        }
        return steps
    }
}
