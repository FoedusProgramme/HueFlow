package org.foedusprogramme.hueflow.palette

import androidx.annotation.ColorInt

class ColorPalette(private val name: String) {
    private val colors = mutableMapOf<ColorToken, Int>()

    fun set(token: ColorToken, @ColorInt color: Int) {
        colors[token] = color
    }

    @ColorInt
    fun get(token: ColorToken): Int =
        colors[token] ?: error("Color not set for token: $token in palette: $name")

    override fun toString(): String = "ColorPalette(name=$name, colors=$colors)"
}