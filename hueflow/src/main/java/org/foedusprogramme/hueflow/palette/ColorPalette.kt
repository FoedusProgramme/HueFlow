package org.foedusprogramme.hueflow.palette

import androidx.annotation.ColorInt

class ColorPalette(
    @param:ColorInt var error: Int,
    @param:ColorInt var errorContainer: Int,
    @param:ColorInt var inverseOnSurface: Int,
    @param:ColorInt var inversePrimary: Int,
    @param:ColorInt var onBackground: Int,
    @param:ColorInt var onError: Int,
    @param:ColorInt var onErrorContainer: Int,
    @param:ColorInt var onPrimary: Int,
    @param:ColorInt var onPrimaryContainer: Int,
    @param:ColorInt var onSecondary: Int,
    @param:ColorInt var onSecondaryContainer: Int,
    @param:ColorInt var onSurface: Int,
    @param:ColorInt var onSurfaceVariant: Int,
    @param:ColorInt var onTertiary: Int,
    @param:ColorInt var onTertiaryContainer: Int,
    @param:ColorInt var outline: Int,
    @param:ColorInt var outlineVariant: Int,
    @param:ColorInt var primary: Int,
    @param:ColorInt var primaryContainer: Int,
    @param:ColorInt var scrim: Int,
    @param:ColorInt var secondary: Int,
    @param:ColorInt var secondaryContainer: Int,
    @param:ColorInt var surface: Int,
    @param:ColorInt var surfaceBright: Int,
    @param:ColorInt var surfaceContainer: Int,
    @param:ColorInt var surfaceContainerLow: Int,
    @param:ColorInt var surfaceContainerLowest: Int,
    @param:ColorInt var surfaceContainerHigh: Int,
    @param:ColorInt var surfaceContainerHighest: Int,
    @param:ColorInt var surfaceDim: Int,
    @param:ColorInt var surfaceVariant: Int,
    @param:ColorInt var tertiary: Int,
    @param:ColorInt var tertiaryContainer: Int
) {

    private val tokenMap: Map<ColorToken, Int> by lazy {
        mapOf(
            ColorToken.Error to error,
            ColorToken.ErrorContainer to errorContainer,
            ColorToken.InverseOnSurface to inverseOnSurface,
            ColorToken.InversePrimary to inversePrimary,
            ColorToken.OnBackground to onBackground,
            ColorToken.OnError to onError,
            ColorToken.OnErrorContainer to onErrorContainer,
            ColorToken.OnPrimary to onPrimary,
            ColorToken.OnPrimaryContainer to onPrimaryContainer,
            ColorToken.OnSecondary to onSecondary,
            ColorToken.OnSecondaryContainer to onSecondaryContainer,
            ColorToken.OnSurface to onSurface,
            ColorToken.OnSurfaceVariant to onSurfaceVariant,
            ColorToken.OnTertiary to onTertiary,
            ColorToken.OnTertiaryContainer to onTertiaryContainer,
            ColorToken.Outline to outline,
            ColorToken.OutlineVariant to outlineVariant,
            ColorToken.Primary to primary,
            ColorToken.PrimaryContainer to primaryContainer,
            ColorToken.Scrim to scrim,
            ColorToken.Secondary to secondary,
            ColorToken.SecondaryContainer to secondaryContainer,
            ColorToken.Surface to surface,
            ColorToken.SurfaceBright to surfaceBright,
            ColorToken.SurfaceContainer to surfaceContainer,
            ColorToken.SurfaceContainerLow to surfaceContainerLow,
            ColorToken.SurfaceContainerLowest to surfaceContainerLowest,
            ColorToken.SurfaceContainerHigh to surfaceContainerHigh,
            ColorToken.SurfaceContainerHighest to surfaceContainerHighest,
            ColorToken.SurfaceDim to surfaceDim,
            ColorToken.SurfaceVariant to surfaceVariant,
            ColorToken.Tertiary to tertiary,
            ColorToken.TertiaryContainer to tertiaryContainer,
        )
    }

    @ColorInt
    operator fun get(token: ColorToken): Int =
        tokenMap[token] ?: error("Color not defined for token: $token")
}
