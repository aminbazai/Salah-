package com.salahgarden.app

import com.salahgarden.app.learn.LearnLessons
import com.salahgarden.app.learn.LearnLessonsPart2
import com.salahgarden.app.learn.LearnPrayerSpecs
import com.salahgarden.app.learn.LearnPositions
import com.salahgarden.app.learn.WalkthroughBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The guide has exactly one piece of real logic: building the ordered position
 * sequence for a prayer. These tests pin that contract for every prayer and
 * every rakat count the app offers.
 */
class WalkthroughBuilderTest {

    private val allLessons = LearnLessons.lessons + LearnLessonsPart2.lessons



    @Test
    fun allFifteenLessonsArePresentAndOrdered() {
        assertEquals(15, allLessons.size)
        assertEquals((1..15).toList(), allLessons.map { it.order })
    }

    @Test
    fun everyLessonHasABilingualTitleAndSummary() {
        allLessons.forEach { lesson ->
            assertTrue("${lesson.id} missing English title", lesson.titleEn.isNotBlank())
            assertTrue("${lesson.id} missing Urdu title", lesson.titleUr.isNotBlank())
            assertTrue("${lesson.id} missing summary", lesson.summaryEn.isNotBlank())
            assertTrue("${lesson.id} has no content", lesson.blocks.isNotEmpty())
        }
    }

    @Test
    fun twoRakatFajrStartsAndEndsCorrectly() {
        val steps = WalkthroughBuilder.build(LearnPrayerSpecs.fajr, 2)
        assertEquals(LearnPositions.takbir.id, steps.first().position.id)
        assertEquals(LearnPositions.salam.id, steps.last().position.id)
        // 2 rakat → 1 middle sitting + 1 final sitting
        assertEquals(2, steps.count { it.position.id == "qadah" })
        assertEquals(4, steps.count { it.position.id == "sajdah" })
        assertEquals(2, steps.count { it.position.id == "ruku" })
        assertEquals(2, steps.count { it.position.id == "qiyam" })
    }

    @Test
    fun fourRakatHasFourStandingBlocksAndFourSittings() {
        val steps = WalkthroughBuilder.build(LearnPrayerSpecs.zuhr, 4)
        assertEquals(4, steps.count { it.position.id == "qiyam" })
        assertEquals(4, steps.count { it.position.id == "ruku" })
        assertEquals(8, steps.count { it.position.id == "sajdah" })
        // 3 middle sittings + 1 final sitting
        assertEquals(4, steps.count { it.position.id == "qadah" })
        assertEquals(1, steps.count { it.position.id == "salam" })
    }

    @Test
    fun threeRakatMaghribHasTheRightShape() {
        val steps = WalkthroughBuilder.build(LearnPrayerSpecs.maghrib, 3)
        assertEquals(3, steps.count { it.position.id == "ruku" })
        assertEquals(6, steps.count { it.position.id == "sajdah" })
        // 2 middle sittings + 1 final sitting
        assertEquals(3, steps.count { it.position.id == "qadah" })
        assertEquals(LearnPositions.salam.id, steps.last().position.id)
    }

    @Test
    fun witrAddsQunootInThirdRakat() {
        val steps = WalkthroughBuilder.build(LearnPrayerSpecs.witr, 3)
        assertEquals(3, steps.count { it.position.id == "ruku" })
        assertEquals(1, steps.count { it.position.id == "qunoot" })
        val qunoot = steps.first { it.position.id == "qunoot" }
        assertEquals(3, qunoot.rakatNumber)
    }

    @Test
    fun everyPrayerBuildsForEveryRakatCountUpToFour() {
        LearnPrayerSpecs.all.forEach { spec ->
            for (rakat in 1..4) {
                val steps = WalkthroughBuilder.build(spec, rakat)
                assertTrue("$spec $rakat produced no steps", steps.isNotEmpty())
                assertEquals("$spec $rakat must start with takbir", "takbir", steps.first().position.id)
                assertEquals("$spec $rakat must end with salam", "salam", steps.last().position.id)
                assertEquals("$spec $rakat ruku count", rakat, steps.count { it.position.id == "ruku" })
            }
        }
    }

    @Test
    fun dailyFardTotalIsSeventeen() {
        assertEquals(17, LearnPrayerSpecs.dailyFardTotal)
    }

    @Test
    fun everyRakatUnitIsLabelledAndPositive() {
        LearnPrayerSpecs.all.forEach { spec ->
            spec.units.forEach { unit ->
                assertTrue("${spec.id} has an unlabelled unit", unit.type.isNotBlank())
                assertTrue("${spec.id} ${unit.type} must be positive", unit.rakat > 0)
            }
        }
    }
}
