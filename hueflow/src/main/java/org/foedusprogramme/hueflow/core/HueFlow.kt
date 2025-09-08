package org.foedusprogramme.hueflow.core

import android.animation.ValueAnimator
import android.app.Application
import android.graphics.Color
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
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
            ColorToken.Surface with 0xFFF9F9FF.toInt()
            ColorToken.Primary with 0xFF415F91.toInt()
            ColorToken.OnPrimary with 0xFFFFFFFF.toInt()
            ColorToken.PrimaryContainer with 0xFFD6E3FF.toInt()
            ColorToken.OnPrimaryContainer with 0xFF284777.toInt()
        }
    }

    fun applyToApplication(application: Application) =
        windowManipulator.onAcquireActivity(application)

    fun changePalette(targetPalette: ColorPalette) {
        ValueAnimator.ofFloat(
            0F,
            1F
        ).apply {
            duration = 10_000
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

    fun updatePaletteUponRegistration(applier: ColorApplier) {
        currentPalette?.let { applier.applyColor(it.get(applier.token)) }
    }

}