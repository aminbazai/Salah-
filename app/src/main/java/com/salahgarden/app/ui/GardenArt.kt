package com.salahgarden.app.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import com.salahgarden.app.ui.theme.Ink
import com.salahgarden.app.ui.theme.Leaf
import com.salahgarden.app.ui.theme.LeafDark
import com.salahgarden.app.ui.theme.Paper
import com.salahgarden.app.ui.theme.Pot
import com.salahgarden.app.ui.theme.PotRim
import com.salahgarden.app.ui.theme.Sun
import com.salahgarden.app.ui.theme.Coral
import com.salahgarden.app.ui.theme.Sky

enum class PlantStatus { DONE, CURRENT, MISSED, UPCOMING }

val BloomColors = listOf(
    Color(0xFF79B6D6),
    Color(0xFFF0B44B),
    Color(0xFFE87358),
    Color(0xFFA477B8),
    Color(0xFF8E8BC9)
)

private fun DrawScope.roundLine(
    from: Offset,
    to: Offset,
    color: Color,
    width: Float
) {
    drawLine(color, from, to, strokeWidth = width, cap = StrokeCap.Round)
}

private fun DrawScope.pot() {
    drawOval(
        color = Ink.copy(alpha = 0.09f),
        topLeft = Offset(14f, 69f),
        size = Size(36f, 6f)
    )
    val potPath = Path().apply {
        moveTo(17f, 56f)
        lineTo(47f, 56f)
        lineTo(43f, 70f)
        lineTo(21f, 70f)
        close()
    }
    drawPath(potPath, Pot)
    drawRoundRect(
        color = PotRim,
        topLeft = Offset(15f, 52f),
        size = Size(34f, 6f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f, 3f)
    )
}

private fun DrawScope.stemLeaves(left: Offset, right: Offset) {
    val leftLeaf = Path().apply {
        moveTo(left.x, left.y)
        cubicTo(left.x - 6f, left.y + 1f, left.x - 10f, left.y - 2f, left.x - 11f, left.y - 7f)
        cubicTo(left.x - 5f, left.y - 8f, left.x - 1f, left.y - 5f, left.x, left.y)
        close()
    }
    val rightLeaf = Path().apply {
        moveTo(right.x, right.y)
        cubicTo(right.x + 6f, right.y + 1f, right.x + 10f, right.y - 2f, right.x + 11f, right.y - 7f)
        cubicTo(right.x + 5f, right.y - 8f, right.x + 1f, right.y - 5f, right.x, right.y)
        close()
    }
    drawPath(leftLeaf, Color(0xFF7DB675))
    drawPath(rightLeaf, Color(0xFF8FC47F))
}

@Composable
fun PlantArt(
    status: PlantStatus,
    color: Color,
    modifier: Modifier = Modifier,
    animate: Boolean = true
) {
    val transition = rememberInfiniteTransition(label = "plant")
    val pulseValue by transition.animateFloat(
        initialValue = 0.975f,
        targetValue = 1.045f,
        animationSpec = infiniteRepeatable(tween(2250), RepeatMode.Reverse),
        label = "bloom"
    )
    val bobValue by transition.animateFloat(
        initialValue = 0f,
        targetValue = -3.2f,
        animationSpec = infiniteRepeatable(tween(1100), RepeatMode.Reverse),
        label = "drop"
    )
    val pulse = if (animate && status == PlantStatus.DONE) pulseValue else 1f
    val bob = if (animate && status == PlantStatus.CURRENT) bobValue else 0f

    Canvas(modifier) {
        scale(size.width / 64f, size.height / 76f, pivot = Offset.Zero) {
            pot()
            when (status) {
                PlantStatus.DONE -> {
                    roundLine(Offset(32f, 52f), Offset(32f, 33f), Color(0xFF5E9C5F), 3f)
                    stemLeaves(Offset(32f, 44f), Offset(32f, 41f))
                    scale(pulse, pulse, pivot = Offset(32f, 24f)) {
                        val petalColor = color
                        drawCircle(petalColor, 6.4f, Offset(32f, 13f))
                        drawCircle(petalColor, 6.4f, Offset(42f, 20f))
                        drawCircle(petalColor, 6.4f, Offset(38f, 31f))
                        drawCircle(petalColor, 6.4f, Offset(26f, 31f))
                        drawCircle(petalColor, 6.4f, Offset(22f, 20f))
                        drawCircle(Color(0xFFF5C451), 5f, Offset(32f, 23f))
                    }
                }

                PlantStatus.CURRENT -> {
                    roundLine(Offset(32f, 52f), Offset(32f, 38f), Color(0xFF5E9C5F), 3f)
                    stemLeaves(Offset(32f, 44f), Offset(32f, 40f))
                    val bud = Path().apply {
                        moveTo(32f, 41f)
                        cubicTo(25f, 40f, 24f, 30f, 31f, 23f)
                        cubicTo(38f, 30f, 37f, 40f, 32f, 41f)
                        close()
                    }
                    drawPath(bud, color)
                    val inner = Path().apply {
                        moveTo(32f, 33f)
                        cubicTo(29f, 31f, 28f, 28f, 29f, 24f)
                        cubicTo(33f, 25f, 35f, 28f, 32f, 33f)
                        close()
                    }
                    drawPath(inner, Color(0xFF8FC47F))
                    translate(top = bob) {
                        val drop = Path().apply {
                            moveTo(45f, 15f)
                            cubicTo(47f, 18f, 49f, 20f, 49f, 23f)
                            cubicTo(49f, 25.2f, 47.2f, 27f, 45f, 27f)
                            cubicTo(42.8f, 27f, 41f, 25.2f, 41f, 23f)
                            cubicTo(41f, 20f, 43f, 18f, 45f, 15f)
                            close()
                        }
                        drawPath(drop, Color(0xFF6DB9D6))
                    }
                }

                PlantStatus.MISSED -> {
                    val stem = Path().apply {
                        moveTo(32f, 52f)
                        cubicTo(31f, 44f, 37f, 37f, 46f, 35f)
                    }
                    drawPath(stem, Color(0xFF7FA37C), style = Stroke(width = 3f, cap = StrokeCap.Round))
                    val leaf = Path().apply {
                        moveTo(36f, 45f)
                        cubicTo(31f, 45f, 28f, 42f, 27f, 37f)
                        cubicTo(32f, 37f, 35f, 40f, 36f, 45f)
                        close()
                    }
                    drawPath(leaf, Color(0xFFA7BDA4))
                    rotate(28f, pivot = Offset(47f, 32f)) {
                        val wilt = Color(0xFFD8B09D).copy(alpha = 0.78f)
                        drawCircle(wilt, 5.4f, Offset(47f, 23f))
                        drawCircle(wilt, 5.4f, Offset(56f, 32f))
                        drawCircle(wilt, 5.4f, Offset(47f, 41f))
                        drawCircle(wilt, 5.4f, Offset(38f, 32f))
                        drawCircle(Color(0xFFC6A260).copy(alpha = 0.78f), 4.3f, Offset(47f, 32f))
                    }
                }

                PlantStatus.UPCOMING -> {
                    roundLine(Offset(32f, 52f), Offset(32f, 40f), Color(0xFF79AD75), 3f)
                    val left = Path().apply {
                        moveTo(32f, 46f)
                        cubicTo(27f, 47f, 23f, 44f, 22f, 40f)
                        cubicTo(27f, 39f, 31f, 42f, 32f, 46f)
                        close()
                    }
                    val right = Path().apply {
                        moveTo(32f, 44f)
                        cubicTo(37f, 45f, 41f, 42f, 42f, 38f)
                        cubicTo(37f, 37f, 33f, 40f, 32f, 44f)
                        close()
                    }
                    drawPath(left, Color(0xFF9FC99A))
                    drawPath(right, Color(0xFFB0D3A8))
                }
            }
        }
    }
}

@Composable
fun BudSprout(
    modifier: Modifier = Modifier,
    animate: Boolean = true
) {
    val transition = rememberInfiniteTransition(label = "bud")
    val swayValue by transition.animateFloat(
        initialValue = -1.6f,
        targetValue = 2.1f,
        animationSpec = infiniteRepeatable(tween(2700), RepeatMode.Reverse),
        label = "sway"
    )
    val sway = if (animate) swayValue else 0f

    Canvas(modifier) {
        scale(size.width / 100f, size.height / 120f, pivot = Offset.Zero) {
            drawOval(
                color = Ink.copy(alpha = 0.13f),
                topLeft = Offset(27f, 106f),
                size = Size(46f, 10f)
            )
            rotate(sway, pivot = Offset(50f, 108f)) {
                val body = Path().apply {
                    moveTo(50f, 36f)
                    cubicTo(69f, 36f, 77f, 53f, 77f, 74f)
                    cubicTo(77f, 94f, 66f, 107f, 50f, 107f)
                    cubicTo(34f, 107f, 23f, 94f, 23f, 74f)
                    cubicTo(23f, 53f, 31f, 36f, 50f, 36f)
                    close()
                }
                drawPath(body, Color(0xFF8FC97A))

                val leafLeft = Path().apply {
                    moveTo(50f, 43f)
                    cubicTo(41f, 31f, 24f, 30f, 19f, 37f)
                    cubicTo(22f, 50f, 38f, 54f, 50f, 43f)
                    close()
                }
                drawPath(leafLeft, Color(0xFF4E9B5B))

                val leafRight = Path().apply {
                    moveTo(50f, 43f)
                    cubicTo(59f, 31f, 76f, 30f, 81f, 37f)
                    cubicTo(78f, 50f, 62f, 54f, 50f, 43f)
                    close()
                }
                drawPath(leafRight, Color(0xFF5EAC66))

                val leafTop = Path().apply {
                    moveTo(50f, 43f)
                    cubicTo(49f, 31f, 53f, 21f, 59f, 16f)
                    cubicTo(64f, 25f, 62f, 36f, 50f, 43f)
                    close()
                }
                drawPath(leafTop, Color(0xFF79BB69))

                drawCircle(Color(0xFF24452F), 3f, Offset(42f, 72f))
                drawCircle(Color(0xFF24452F), 3f, Offset(58f, 72f))
                val smile = Path().apply {
                    moveTo(45f, 80f)
                    quadraticBezierTo(50f, 84f, 55f, 80f)
                }
                drawPath(smile, Color(0xFF24452F), style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                drawCircle(Coral.copy(alpha = 0.35f), 3f, Offset(36f, 80f))
                drawCircle(Coral.copy(alpha = 0.35f), 3f, Offset(64f, 80f))
                val shine = Path().apply {
                    moveTo(34f, 62f)
                    quadraticBezierTo(36f, 60f, 41f, 62f)
                }
                drawPath(shine, Paper.copy(alpha = 0.35f), style = Stroke(width = 2f, cap = StrokeCap.Round))
            }
        }
    }
}

@Composable
fun WiltedPlant(
    modifier: Modifier = Modifier,
    animate: Boolean = true
) {
    val transition = rememberInfiniteTransition(label = "wilted")
    val swayValue by transition.animateFloat(
        initialValue = -1.4f,
        targetValue = 1.8f,
        animationSpec = infiniteRepeatable(tween(3500), RepeatMode.Reverse),
        label = "wiltedSway"
    )
    val sway = if (animate) swayValue else 0f

    Canvas(modifier) {
        scale(size.width / 160f, size.height / 170f, pivot = Offset.Zero) {
            drawOval(
                color = Ink.copy(alpha = 0.10f),
                topLeft = Offset(36f, 150f),
                size = Size(88f, 14f)
            )
            val pot = Path().apply {
                moveTo(53f, 130f)
                lineTo(107f, 130f)
                lineTo(100f, 155f)
                lineTo(60f, 155f)
                close()
            }
            drawPath(pot, Pot)
            drawRoundRect(
                color = PotRim,
                topLeft = Offset(49f, 125f),
                size = Size(62f, 9f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.5f, 4.5f)
            )

            rotate(sway, pivot = Offset(80f, 130f)) {
                val stem = Path().apply {
                    moveTo(80f, 126f)
                    cubicTo(78f, 102f, 88f, 81f, 111f, 75f)
                }
                drawPath(stem, Color(0xFF5E9C5F), style = Stroke(width = 4.5f, cap = StrokeCap.Round))

                val leafOne = Path().apply {
                    moveTo(85f, 106f)
                    cubicTo(72f, 108f, 63f, 102f, 61f, 93f)
                    cubicTo(73f, 90f, 83f, 96f, 85f, 106f)
                    close()
                }
                drawPath(leafOne, Color(0xFF7DB675))

                val leafTwo = Path().apply {
                    moveTo(95f, 91f)
                    cubicTo(104f, 83f, 115f, 83f, 122f, 89f)
                    cubicTo(118f, 99f, 107f, 103f, 95f, 91f)
                    close()
                }
                drawPath(leafTwo, Color(0xFF8FC47F))

                val leafThree = Path().apply {
                    moveTo(109f, 74f)
                    cubicTo(102f, 72f, 98f, 65f, 99f, 57f)
                    cubicTo(108f, 58f, 114f, 65f, 109f, 74f)
                    close()
                }
                drawPath(leafThree, Color(0xFF71AD6D))

                rotate(19f, pivot = Offset(116f, 66f)) {
                    val petal = Color(0xFFD99783)
                    drawCircle(petal, 8f, Offset(105f, 66f))
                    drawCircle(petal, 8f, Offset(116f, 56f))
                    drawCircle(petal, 8f, Offset(127f, 66f))
                    drawCircle(petal, 8f, Offset(116f, 76f))
                    drawCircle(Color(0xFFE5B65F), 7f, Offset(116f, 66f))
                    // a small sleepy face on the wilted bloom
                    val eyeOne = Path().apply {
                        moveTo(112f, 67f)
                        quadraticBezierTo(113.5f, 65.5f, 115f, 67f)
                    }
                    val eyeTwo = Path().apply {
                        moveTo(116.5f, 67f)
                        quadraticBezierTo(118f, 65.5f, 119.5f, 67f)
                    }
                    val mouth = Path().apply {
                        moveTo(113.5f, 71f)
                        quadraticBezierTo(116f, 69.8f, 118.5f, 71f)
                    }
                    val faceStroke = Stroke(width = 1.4f, cap = StrokeCap.Round)
                    drawPath(eyeOne, Color(0xFF6D4D2A), style = faceStroke)
                    drawPath(eyeTwo, Color(0xFF6D4D2A), style = faceStroke)
                    drawPath(mouth, Color(0xFF6D4D2A), style = faceStroke)
                }
            }

            val grass = Path().apply {
                moveTo(47f, 151f)
                cubicTo(44f, 146f, 45f, 142f, 48f, 139f)
                cubicTo(52f, 141f, 54f, 146f, 52f, 151f)
            }
            drawPath(grass, Color(0xFFA7BDA4).copy(alpha = 0.7f), style = Stroke(width = 2f, cap = StrokeCap.Round))
            drawCircle(Color(0xFF9FB89A).copy(alpha = 0.6f), 2.2f, Offset(38f, 106f))
            drawCircle(Color(0xFF9FB89A).copy(alpha = 0.5f), 1.6f, Offset(130f, 118f))
        }
    }
}

@Composable
fun WeekFlower(
    filled: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier) {
        scale(size.width / 44f, size.height / 50f, pivot = Offset.Zero) {
            roundLine(Offset(22f, 34f), Offset(22f, 46f), Color(0xFF6BA36C), 2.4f)
            val left = Path().apply {
                moveTo(22f, 43f)
                cubicTo(18f, 44f, 15f, 42f, 14f, 38f)
                cubicTo(18f, 37f, 21f, 39f, 22f, 43f)
                close()
            }
            val right = Path().apply {
                moveTo(22f, 41f)
                cubicTo(26f, 42f, 29f, 40f, 30f, 36f)
                cubicTo(26f, 35f, 23f, 37f, 22f, 41f)
                close()
            }
            drawPath(left, Color(0xFF9FC99A))
            drawPath(right, Color(0xFFB0D3A8))
            val petals = listOf(
                Offset(22f, 8f),
                Offset(33f, 16f),
                Offset(29f, 29f),
                Offset(15f, 29f),
                Offset(11f, 16f)
            )
            petals.forEachIndexed { index, center ->
                val on = index < filled
                drawCircle(
                    color = if (on) Coral else Color(0xFFF5F6EB),
                    radius = 6.4f,
                    center = center
                )
                if (!on) {
                    drawCircle(
                        color = Color(0xFFD7E0D0),
                        radius = 6.4f,
                        center = center,
                        style = Stroke(width = 1.1f)
                    )
                }
            }
            drawCircle(Sun, 5f, Offset(22f, 20f))
        }
    }
}

@Composable
fun SkyArt(
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    bud: @Composable () -> Unit = {}
) {
    val transition = rememberInfiniteTransition(label = "sky")
    val sunBobValue by transition.animateFloat(
        initialValue = 0f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(tween(3500), RepeatMode.Reverse),
        label = "sunBob"
    )
    val driftValue by transition.animateFloat(
        initialValue = -4f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(4500, easing = LinearEasing), RepeatMode.Reverse),
        label = "cloudDrift"
    )
    val driftSecond by transition.animateFloat(
        initialValue = 4f,
        targetValue = -6f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing), RepeatMode.Reverse),
        label = "cloudDriftTwo"
    )
    val sunBob = if (animate) sunBobValue else 0f
    val drift = if (animate) driftValue else 0f
    val drift2 = if (animate) driftSecond else 0f

    Box(modifier) {
        Canvas(Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB9E6F4),
                        Color(0xFFDDF4F3),
                        Color(0xFFF7F2D4)
                    )
                )
            )

            // sun
            val sunRadius = 27.5f * density
            val sunCenter = Offset(
                x = size.width - 38.dp.toPx() - sunRadius,
                y = 18.dp.toPx() + sunRadius + sunBob
            )
            drawCircle(Sun.copy(alpha = 0.13f), sunRadius + 11.dp.toPx(), sunCenter)
            drawCircle(Sun.copy(alpha = 0.12f), sunRadius + 22.dp.toPx(), sunCenter)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFFF5C9), Sun),
                    center = Offset(sunCenter.x - sunRadius * 0.35f, sunCenter.y - sunRadius * 0.35f),
                    radius = sunRadius * 1.6f
                ),
                radius = sunRadius,
                center = sunCenter
            )

            // clouds
            drawCloud(
                x = 138.dp.toPx() + drift,
                y = 29.dp.toPx(),
                width = 58.dp.toPx(),
                alpha = 0.9f
            )
            drawCloud(
                x = size.width - 91.dp.toPx() - 45.dp.toPx() + drift2,
                y = 69.dp.toPx(),
                width = 45.dp.toPx(),
                alpha = 0.62f
            )

            // hills
            drawOval(
                color = Color(0xFF9DCA91),
                topLeft = Offset(-44.dp.toPx(), 69.dp.toPx()),
                size = Size(248.dp.toPx(), 113.dp.toPx())
            )
            drawOval(
                color = Color(0xFF74AC77),
                topLeft = Offset(size.width + 60.dp.toPx() - 280.dp.toPx(), 50.dp.toPx()),
                size = Size(280.dp.toPx(), 132.dp.toPx())
            )
            drawOval(
                color = Color(0xFF8ABB83).copy(alpha = 0.8f),
                topLeft = Offset(118.dp.toPx(), 69.dp.toPx()),
                size = Size(214.dp.toPx(), 126.dp.toPx())
            )

            // faint sparkle dots
            drawCircle(Paper.copy(alpha = 0.55f), 1.4.dp.toPx(), Offset(96.dp.toPx(), 96.dp.toPx()))
            drawCircle(Paper.copy(alpha = 0.4f), 1.1.dp.toPx(), Offset(300.dp.toPx(), 32.dp.toPx()))
        }
        bud()
    }
}

private fun DrawScope.drawCloud(
    x: Float,
    y: Float,
    width: Float,
    alpha: Float
) {
    val height = 14.dp.toPx()
    val color = Paper.copy(alpha = alpha)
    drawRoundRect(
        color = color,
        topLeft = Offset(x, y),
        size = Size(width, height),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(height / 2f, height / 2f)
    )
    drawCircle(color, 10.5.dp.toPx(), Offset(x + 19.dp.toPx(), y + height - 1.5.dp.toPx()))
    drawCircle(color, 7.5.dp.toPx(), Offset(x + width - 17.dp.toPx(), y + height - 1.5.dp.toPx()))
}

enum class GardenIconKind {
    SUN,
    FLOWER,
    FLOWER_FILLED,
    PIN,
    CHEVRON,
    SEARCH,
    TARGET,
    BACK,
    CLOSE,
    WATER,
    LEAF,
    REFRESH,
    CHECK
}

@Composable
fun GardenIcon(
    kind: GardenIconKind,
    modifier: Modifier = Modifier,
    tint: Color = Leaf,
    strokeWidth: Float = 1.8f
) {
    Canvas(modifier) {
        val unit = size.minDimension / 24f
        translate(
            left = (size.width - 24f * unit) / 2f,
            top = (size.height - 24f * unit) / 2f
        ) {
            scale(unit, unit, pivot = Offset.Zero) {
                val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                when (kind) {
                    GardenIconKind.SUN -> {
                        drawCircle(tint, 4f, Offset(12f, 12f), style = stroke)
                        val rays = listOf(
                            12f to 2f, 12f to 22f, 2f to 12f, 22f to 12f,
                            4.9f to 4.9f, 19.1f to 19.1f, 19.1f to 4.9f, 4.9f to 19.1f
                        )
                        rays.forEachIndexed { index, point ->
                            val inner = when (index) {
                                0 -> Offset(12f, 4.6f)
                                1 -> Offset(12f, 19.4f)
                                2 -> Offset(4.6f, 12f)
                                3 -> Offset(19.4f, 12f)
                                4 -> Offset(6.7f, 6.7f)
                                5 -> Offset(17.3f, 17.3f)
                                6 -> Offset(17.3f, 6.7f)
                                else -> Offset(6.7f, 17.3f)
                            }
                            roundLine(inner, Offset(point.first, point.second), tint, strokeWidth)
                        }
                    }

                    GardenIconKind.FLOWER -> {
                        listOf(
                            Offset(12f, 7f),
                            Offset(17.4f, 11f),
                            Offset(15.3f, 17.2f),
                            Offset(8.7f, 17.2f),
                            Offset(6.6f, 11f)
                        ).forEach { center ->
                            drawCircle(tint, 3.2f, center, style = stroke)
                        }
                        drawCircle(tint, 2.3f, Offset(12f, 12.2f), style = stroke)
                    }

                    GardenIconKind.FLOWER_FILLED -> {
                        listOf(
                            Offset(12f, 7f),
                            Offset(17.4f, 11f),
                            Offset(15.3f, 17.2f),
                            Offset(8.7f, 17.2f),
                            Offset(6.6f, 11f)
                        ).forEach { center ->
                            drawCircle(tint, 3.3f, center)
                        }
                        drawCircle(Sun, 2.6f, Offset(12f, 12.2f))
                    }

                    GardenIconKind.PIN -> {
                        drawCircle(tint, 7f, Offset(12f, 10f), style = stroke)
                        roundLine(Offset(7.2f, 15.2f), Offset(12f, 22f), tint, strokeWidth)
                        roundLine(Offset(16.8f, 15.2f), Offset(12f, 22f), tint, strokeWidth)
                        drawCircle(tint, 2.4f, Offset(12f, 10f), style = stroke)
                    }

                    GardenIconKind.CHEVRON -> {
                        val path = Path().apply {
                            moveTo(9f, 6f)
                            lineTo(15f, 12f)
                            lineTo(9f, 18f)
                        }
                        drawPath(path, tint, style = stroke)
                    }

                    GardenIconKind.BACK -> {
                        val path = Path().apply {
                            moveTo(15f, 5f)
                            lineTo(8f, 12f)
                            lineTo(15f, 19f)
                        }
                        drawPath(path, tint, style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                    }

                    GardenIconKind.SEARCH -> {
                        val center = Offset(11f, 11f)
                        drawCircle(tint, 7f, center, style = stroke)
                        roundLine(Offset(16f, 16f), Offset(20.5f, 20.5f), tint, strokeWidth)
                    }

                    GardenIconKind.TARGET -> {
                        drawCircle(tint, 7.5f, Offset(12f, 12f), style = stroke)
                        drawCircle(tint, 3f, Offset(12f, 12f), style = stroke)
                        roundLine(Offset(12f, 2f), Offset(12f, 5f), tint, strokeWidth)
                        roundLine(Offset(12f, 19f), Offset(12f, 22f), tint, strokeWidth)
                        roundLine(Offset(2f, 12f), Offset(5f, 12f), tint, strokeWidth)
                        roundLine(Offset(19f, 12f), Offset(22f, 12f), tint, strokeWidth)
                    }

                    GardenIconKind.CLOSE -> {
                        roundLine(Offset(6f, 6f), Offset(18f, 18f), tint, 2.2f)
                        roundLine(Offset(18f, 6f), Offset(6f, 18f), tint, 2.2f)
                    }

                    GardenIconKind.WATER -> {
                        val body = Path().apply {
                            moveTo(4f, 10f)
                            lineTo(15f, 10f)
                            lineTo(15f, 17f)
                            cubicTo(15f, 19.2f, 13.2f, 21f, 11f, 21f)
                            lineTo(8f, 21f)
                            cubicTo(5.8f, 21f, 4f, 19.2f, 4f, 17f)
                            close()
                        }
                        drawPath(body, tint, style = stroke)
                        val handle = Path().apply {
                            moveTo(15f, 12f)
                            lineTo(17.5f, 12f)
                            cubicTo(19f, 12f, 20f, 11f, 20f, 9.5f)
                            cubicTo(20f, 8f, 19f, 7f, 17.5f, 7f)
                            lineTo(15f, 7f)
                        }
                        drawPath(handle, tint, style = stroke)
                        val topHandle = Path().apply {
                            moveTo(7f, 10f)
                            lineTo(7f, 8f)
                            cubicTo(7f, 6.3f, 8.3f, 5f, 10f, 5f)
                            lineTo(11f, 5f)
                        }
                        drawPath(topHandle, tint, style = stroke)
                        roundLine(Offset(18f, 4f), Offset(21f, 7f), tint, strokeWidth)
                        roundLine(Offset(21f, 4f), Offset(18f, 7f), tint, strokeWidth)
                    }

                    GardenIconKind.LEAF -> {
                        val leaf = Path().apply {
                            moveTo(20f, 4f)
                            cubicTo(12f, 4f, 6f, 8f, 6f, 15f)
                            cubicTo(6f, 17.8f, 8.2f, 20f, 11f, 20f)
                            cubicTo(18f, 20f, 20f, 12f, 20f, 4f)
                            close()
                        }
                        drawPath(leaf, tint, style = stroke)
                        val vein = Path().apply {
                            moveTo(4f, 21f)
                            cubicTo(6f, 16f, 9f, 13f, 13f, 11f)
                        }
                        drawPath(vein, tint, style = stroke)
                    }

                    GardenIconKind.REFRESH -> {
                        drawArc(
                            color = tint,
                            startAngle = 30f,
                            sweepAngle = 285f,
                            useCenter = false,
                            topLeft = Offset(4f, 4f),
                            size = Size(16f, 16f),
                            style = Stroke(width = 2f, cap = StrokeCap.Round)
                        )
                        val arrow = Path().apply {
                            moveTo(17.5f, 3.5f)
                            lineTo(20.5f, 6.5f)
                            lineTo(17.5f, 9f)
                        }
                        drawPath(arrow, tint, style = Stroke(width = 2f, cap = StrokeCap.Round))
                    }

                    GardenIconKind.CHECK -> {
                        val check = Path().apply {
                            moveTo(5f, 12.5f)
                            lineTo(10f, 17.5f)
                            lineTo(19f, 7f)
                        }
                        drawPath(check, tint, style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                    }
                }
            }
        }
    }
}

@Composable
fun BloomBadge(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val scaleFactor = size.minDimension / 64f
        scale(scaleFactor, scaleFactor, pivot = Offset.Zero) {
            drawCircle(Coral, 9f, Offset(32f, 14f))
            drawCircle(Coral, 9f, Offset(48f, 25f))
            drawCircle(Coral, 9f, Offset(42f, 44f))
            drawCircle(Coral, 9f, Offset(22f, 44f))
            drawCircle(Coral, 9f, Offset(16f, 25f))
            drawCircle(Sun, 8f, Offset(32f, 30f))
        }
    }
}

@Composable
fun AppBackdrop(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawRect(
            brush = Brush.verticalGradient(
                0f to Color(0xFFD8F1F8),
                0.28f to Color(0xFFEEF8F4),
                0.62f to com.salahgarden.app.ui.theme.Cream,
                1f to com.salahgarden.app.ui.theme.Cream
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Sun.copy(alpha = 0.16f), Color.Transparent),
                center = Offset(size.width * 0.13f, size.height * 0.06f),
                radius = size.width * 0.55f
            ),
            radius = size.width * 0.55f,
            center = Offset(size.width * 0.13f, size.height * 0.06f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Sky.copy(alpha = 0.7f), Color.Transparent),
                center = Offset(size.width * 0.88f, size.height * 0.03f),
                radius = size.width * 0.5f
            ),
            radius = size.width * 0.5f,
            center = Offset(size.width * 0.88f, size.height * 0.03f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Leaf.copy(alpha = 0.08f), Color.Transparent),
                center = Offset(size.width * 0.55f, size.height * 1.02f),
                radius = size.width * 0.8f
            ),
            radius = size.width * 0.8f,
            center = Offset(size.width * 0.55f, size.height * 1.02f)
        )

        val step = 15.dp.toPx()
        var y = size.height * 0.52f
        while (y < size.height) {
            var x = step / 2f
            while (x < size.width) {
                val fade = ((y / size.height) - 0.45f).coerceIn(0f, 1f)
                drawCircle(
                    color = Ink.copy(alpha = 0.06f * fade),
                    radius = 0.9.dp.toPx(),
                    center = Offset(x, y)
                )
                x += step
            }
            y += step
        }
    }
}
