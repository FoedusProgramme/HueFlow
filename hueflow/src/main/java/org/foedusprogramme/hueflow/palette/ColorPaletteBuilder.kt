package org.foedusprogramme.hueflow.palette

import androidx.annotation.ColorInt

@DslMarker
annotation class PaletteDsl

@PaletteDsl
class ColorPaletteBuilder(name: String) {
    private val palette = ColorPalette(name)

    infix fun ColorToken.with(@ColorInt color: Int) {
        palette.set(this, color)
    }

    fun build(): ColorPalette = palette
}

fun buildPalette(name: String, block: ColorPaletteBuilder.() -> Unit): ColorPalette {
    val builder = ColorPaletteBuilder(name)
    builder.block()
    return builder.build()
}