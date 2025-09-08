package org.foedusprogramme.hueflow.palette

import org.foedusprogramme.hueflow.colorutilities.hct.Hct
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeContent
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeExpressive
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeFidelity
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeFruitSalad
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeMonochrome
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeNeutral
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeRainbow
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeTonalSpot
import org.foedusprogramme.hueflow.colorutilities.scheme.SchemeVibrant

fun hueFlowColorPalette(
    keyColor: Int,
    isDark: Boolean,
    paletteStyle: PaletteStyle,
    contrastLevel: Double = 0.0
): ColorPalette {
    val hct = Hct.fromInt(keyColor)
    val scheme = when (paletteStyle) {
        PaletteStyle.TonalSpot -> SchemeTonalSpot(hct, isDark, contrastLevel)
        PaletteStyle.Neutral -> SchemeNeutral(hct, isDark, contrastLevel)
        PaletteStyle.Vibrant -> SchemeVibrant(hct, isDark, contrastLevel)
        PaletteStyle.Expressive -> SchemeExpressive(hct, isDark, contrastLevel)
        PaletteStyle.Rainbow -> SchemeRainbow(hct, isDark, contrastLevel)
        PaletteStyle.FruitSalad -> SchemeFruitSalad(hct, isDark, contrastLevel)
        PaletteStyle.Monochrome -> SchemeMonochrome(hct, isDark, contrastLevel)
        PaletteStyle.Fidelity -> SchemeFidelity(hct, isDark, contrastLevel)
        PaletteStyle.Content -> SchemeContent(hct, isDark, contrastLevel)
    }
    return ColorPalette(
        error = scheme.error,
        errorContainer = scheme.errorContainer,
        inverseOnSurface = scheme.inverseOnSurface,
        inversePrimary = scheme.inversePrimary,
        onBackground = scheme.onBackground,
        onError = scheme.onError,
        onErrorContainer = scheme.onErrorContainer,
        onPrimary = scheme.onPrimary,
        onPrimaryContainer = scheme.onPrimaryContainer,
        onSecondary = scheme.onSecondary,
        onSecondaryContainer = scheme.onSecondaryContainer,
        onSurface = scheme.onSurface,
        onSurfaceVariant = scheme.onSurfaceVariant,
        onTertiary = scheme.onTertiary,
        onTertiaryContainer = scheme.onTertiaryContainer,
        outline = scheme.outline,
        outlineVariant = scheme.outlineVariant,
        primary = scheme.primary,
        primaryContainer = scheme.primaryContainer,
        scrim = scheme.scrim,
        secondary = scheme.secondary,
        secondaryContainer = scheme.secondaryContainer,
        surface = scheme.surface,
        surfaceBright = scheme.surfaceBright,
        surfaceContainer = scheme.surfaceContainer,
        surfaceContainerLow = scheme.surfaceContainerLow,
        surfaceContainerLowest = scheme.surfaceContainerLowest,
        surfaceContainerHigh = scheme.surfaceContainerHigh,
        surfaceContainerHighest = scheme.surfaceContainerHighest,
        surfaceDim = scheme.surfaceDim,
        surfaceVariant = scheme.surfaceVariant,
        tertiary = scheme.tertiary,
        tertiaryContainer = scheme.tertiaryContainer
    )
}