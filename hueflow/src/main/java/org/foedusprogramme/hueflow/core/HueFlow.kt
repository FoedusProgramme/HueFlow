package org.foedusprogramme.hueflow.core

import android.animation.ValueAnimator
import android.app.Application
import androidx.core.animation.doOnEnd
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.palette.ColorPalette
import org.foedusprogramme.hueflow.lerp

object HueFlow {

    private val windowManipulator = WindowManipulator()
    var currentPalette: ColorPalette? = null
    var paletteAnimator: ValueAnimator? = null

    init {
        currentPalette = HueFlowPresets.redColorPalette(false)
    }

    fun applyToApplication(application: Application) =
        windowManipulator.onAcquireActivity(application)

    fun changePalette(targetPalette: ColorPalette) {
        if (paletteAnimator?.isRunning == true) return
        paletteAnimator = ValueAnimator.ofFloat(
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