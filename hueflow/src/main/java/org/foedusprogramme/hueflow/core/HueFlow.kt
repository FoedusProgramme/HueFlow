package org.foedusprogramme.hueflow.core

import android.animation.ValueAnimator
import android.app.Application
import android.graphics.Color
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.palette.ColorPalette
import org.foedusprogramme.hueflow.palette.ColorToken
import org.foedusprogramme.hueflow.palette.buildPalette
import org.foedusprogramme.hueflow.lerp

object HueFlow {

    private val windowManipulator = WindowManipulator()
    var currentPalette: ColorPalette? = null

    init {
        // TODO: Test
        currentPalette = buildPalette("blue") {
            ColorToken.Surface with Color.BLUE
        }
    }

    fun applyToApplication(application: Application) =
        windowManipulator.onAcquireActivity(application)

    fun changePalette(targetPalette: ColorPalette) {
        ValueAnimator.ofFloat(
            0F,
            1F
        ).apply {
            ValueAnimator.setDuration = 10_000
            addUpdateListener {
                val value = it.animatedValue as Float
                ColorRegistry.getAllAppliers().forEach { it ->
                    it.applyColor(
                        (currentPalette?.get(it.token) ?: 0)
                            .lerp(targetPalette.get(it.token), value)
                    )
                }
            }
            start()
        }
    }

    fun changePaletteImmediately(targetPalette: ColorPalette) {
        ColorRegistry.getAllAppliers().forEach { it ->
            it.applyColor(targetPalette.get(it.token))
        }
    }

}