package org.foedusprogramme.hueflow.core

import org.foedusprogramme.hueflow.palette.PaletteStyle
import org.foedusprogramme.hueflow.palette.hueFlowColorPalette

object HueFlowPresets {
    const val PRESET_COLOR_BLUE = 0xFF769CDF.toInt()
    const val PRESET_COLOR_RED = 0xFFB33B15.toInt()

    fun blueColorPalette(
        isDark: Boolean,
        paletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
        contrastLevel: Double = 0.0
    ) = hueFlowColorPalette(
        PRESET_COLOR_BLUE,
        isDark,
        paletteStyle,
        contrastLevel
    )

    fun redColorPalette(
        isDark: Boolean,
        paletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
        contrastLevel: Double = 0.0
    ) = hueFlowColorPalette(
        PRESET_COLOR_RED,
        isDark,
        paletteStyle,
        contrastLevel
    )

}