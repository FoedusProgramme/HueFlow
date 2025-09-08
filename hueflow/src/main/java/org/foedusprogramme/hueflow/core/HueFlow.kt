package org.foedusprogramme.hueflow.core

import android.animation.ValueAnimator
import android.app.Application
import android.graphics.Color
import androidx.core.animation.doOnEnd
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.palette.ColorPalette
import org.foedusprogramme.hueflow.palette.ColorToken
import org.foedusprogramme.hueflow.palette.buildPalette
import org.foedusprogramme.hueflow.lerp

object HueFlow {

    private val windowManipulator = WindowManipulator()
    var currentPalette: ColorPalette? = null

    val bluePalette = buildPalette("blue") {
        ColorToken.Surface with 0xFFF9F9FF.toInt()
        ColorToken.Primary with 0xFF415F91.toInt()
        ColorToken.OnPrimary with 0xFFFFFFFF.toInt()
        ColorToken.PrimaryContainer with 0xFFD6E3FF.toInt()
        ColorToken.OnPrimaryContainer with 0xFF284777.toInt()
    }

    val redPalette = buildPalette("red") {
        ColorToken.Surface with 0xFFFFF8F7.toInt()
        ColorToken.Primary with 0xFF904A45.toInt()
        ColorToken.OnPrimary with 0xFFFFFFFF.toInt()
        ColorToken.PrimaryContainer with 0xFFFFDAD6.toInt()
        ColorToken.OnPrimaryContainer with 0xFF73332F.toInt()
    }

    init {
        currentPalette = redPalette
    }

    fun applyToApplication(application: Application) =
        windowManipulator.onAcquireActivity(application)

    fun changePalette(targetPalette: ColorPalette) {
        ValueAnimator.ofFloat(
            0F,
            1F
        ).apply {
            duration = 500
            addUpdateListener {
                val value = it.animatedValue as Float
                ColorRegistry.getAllAppliers().forEach { it ->
                    it.applyColor(
                        (currentPalette?.get(it.token) ?: 0)
                            .lerp(targetPalette.get(it.token), value)
                    )
                }
            }
            doOnEnd {
                currentPalette = targetPalette
            }
            start()
        }
    }

    fun updatePaletteUponRegistration(applier: ColorApplier) {
        currentPalette?.let { applier.applyColor(it.get(applier.token)) }
    }

}