package org.foedusprogramme.hueflow

import androidx.annotation.ColorInt
import org.foedusprogramme.hueflow.palette.ColorPalette
import kotlin.math.roundToInt

@ColorInt
fun @receiver:ColorInt Int.lerp(
    @ColorInt to: Int,
    fraction: Float
): Int {
    val f = fraction.coerceIn(0f, 1f)

    val a1 = this ushr 24
    val r1 = (this shr 16) and 0xFF
    val g1 = (this shr 8) and 0xFF
    val b1 = this and 0xFF

    val a2 = to ushr 24
    val r2 = (to shr 16) and 0xFF
    val g2 = (to shr 8) and 0xFF
    val b2 = to and 0xFF

    val a = (a1 + (a2 - a1) * f).roundToInt()
    val r = (r1 + (r2 - r1) * f).roundToInt()
    val g = (g1 + (g2 - g1) * f).roundToInt()
    val b = (b1 + (b2 - b1) * f).roundToInt()

    return (a shl 24) or (r shl 16) or (g shl 8) or b
}

fun ColorPalette.equalsByValue(colorPalette: ColorPalette): Boolean {
    return primary == colorPalette.primary && secondary == colorPalette.secondary && tertiary == colorPalette.tertiary
}