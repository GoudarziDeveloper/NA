package com.tinyDeveloper.na.ui.component

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.time.Duration.Companion.nanoseconds

internal fun Modifier.snowfall(isActive: Boolean, baseFrameDurationMillis: Long, baseSpeedPxAt60Fps: Long) = composed {
    var snowflakesState by remember {
        mutableStateOf(SnowflakesState(-1, IntSize(0, 0), isActive))
    }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { newTick ->
                val elapsedMillis =
                    (newTick - snowflakesState.tickNanos).nanoseconds.inWholeMilliseconds
                val wasFirstRun = snowflakesState.tickNanos < 0
                snowflakesState.tickNanos = newTick

                if (wasFirstRun) return@withFrameNanos
                for (snowflake in snowflakesState.snowflakes) {
                    snowflake.update(
                        elapsedMillis = elapsedMillis,
                        baseFrameDurationMillis = baseFrameDurationMillis,
                        baseSpeedPxAt60Fps = baseSpeedPxAt60Fps
                    )
                }
            }
        }
    }

    onSizeChanged { newSize -> snowflakesState = snowflakesState.resize(newSize, isActive) }
        .clipToBounds()
        .drawWithContent {
            drawContent()
            snowflakesState.draw(drawContext.canvas)
        }
}

fun ClosedRange<Float>.random() =
    ThreadLocalRandom.current().nextFloat() * (endInclusive - start) + start

fun Float.random() =
    ThreadLocalRandom.current().nextFloat() * this

fun Int.random() =
    ThreadLocalRandom.current().nextInt(this)

fun IntSize.randomPosition() =
    Offset(width.random().toFloat(), height.random().toFloat())

private const val snowflakeDensity = 0.1
private val incrementRange = 0.2f..0.6f
private val sizeRange = 1.0f..5.0f
private const val angleSeed = 15.0f
private val angleSeedRange = -angleSeed..angleSeed
private const val angleRange = 0.1f
private const val angleDivisor = 15000.0f

internal data class SnowflakesState(
    var tickNanos: Long,
    val snowflakes: List<Snowflake>,
    val isActive: Boolean
) {

    constructor(tick: Long, canvasSize: IntSize, isActive: Boolean) :
            this(tick, createSnowflakes(canvasSize), isActive = isActive)

    fun draw(canvas: Canvas) {
        snowflakes.forEach { it.draw(canvas, isActive = isActive) }
    }

    fun resize(newSize: IntSize, isActive: Boolean) = copy(snowflakes = createSnowflakes(newSize))

    companion object {
        private fun createSnowflakes(canvasSize: IntSize): List<Snowflake> {
            val canvasArea = canvasSize.width * canvasSize.height

            val normalizedDensity = snowflakeDensity.coerceIn(0.0..1.0) / 500.0
            val remover = 2
            val snowflakesCount = (canvasArea * normalizedDensity / remover).roundToInt()

            return List(snowflakesCount) {
                Snowflake(
                    incrementFactor = incrementRange.random(),
                    size = sizeRange.random(),
                    canvasSize = canvasSize,
                    position = canvasSize.randomPosition(),
                    angle = angleSeed.random() / angleSeed * angleRange + (PI / 2.0) - (angleRange / 2.0)
                )
            }
        }
    }
}

private val snowflakePaint = Paint().apply {
    isAntiAlias = true
    color = Color.White
    style = PaintingStyle.Fill
}

internal class Snowflake(
    private val incrementFactor: Float,
    private val size: Float,
    private val canvasSize: IntSize,
    position: Offset,
    angle: Double
) {
    private var position by mutableStateOf(position)
    private var angle by mutableStateOf(angle)

    fun update(elapsedMillis: Long, baseFrameDurationMillis: Long, baseSpeedPxAt60Fps: Long) {
        val increment = incrementFactor * (elapsedMillis / baseFrameDurationMillis) * baseSpeedPxAt60Fps
        val xDelta = (increment * cos(angle)).toFloat()
        val yDelta = (increment * sin(angle)).toFloat()
        position = Offset(position.x + xDelta, position.y + yDelta)
        angle += angleSeedRange.random() / angleDivisor

        if (position.y > canvasSize.height + size) {
            position = Offset(position.x, -size)
        }
    }

    fun draw(canvas: Canvas, isActive: Boolean) {
        canvas.drawCircle(position, size, snowflakePaint)
    }
}