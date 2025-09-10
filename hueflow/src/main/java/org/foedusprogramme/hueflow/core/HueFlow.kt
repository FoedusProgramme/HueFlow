package org.foedusprogramme.hueflow.core

import android.animation.ValueAnimator
import android.app.Application
import androidx.core.animation.doOnEnd
import org.foedusprogramme.hueflow.ANIMATION_LENGTH
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.colorapplier.factory.ViewColorAppliersFactory
import org.foedusprogramme.hueflow.colorapplier.implementation.MaterialButtonColorAppliersFactory
import org.foedusprogramme.hueflow.colorapplier.implementation.TextInputLayoutColorAppliersFactory
import org.foedusprogramme.hueflow.equalsByValue
import org.foedusprogramme.hueflow.palette.ColorPalette
import org.foedusprogramme.hueflow.lerp

object HueFlow {

    private val windowManipulator = WindowManipulator()
    var factories: List<ViewColorAppliersFactory<*>> = listOf(
        MaterialButtonColorAppliersFactory(),
        TextInputLayoutColorAppliersFactory()
    )
    var currentPalette: ColorPalette = HueFlowPresets.greenColorPalette(false)
    var paletteAnimator: ValueAnimator? = null
    private val onPaletteUpdateListeners = mutableListOf<OnPaletteUpdateListener>()

    fun applyToApplication(application: Application) =
        windowManipulator.onAcquireActivity(application)

    fun changePalette(targetPalette: ColorPalette) {
        if (paletteAnimator?.isRunning == true || targetPalette.equalsByValue(currentPalette)) return
        onPaletteUpdateListeners.forEach {
            it.onUpdate(currentPalette, targetPalette)
        }
        paletteAnimator = ValueAnimator.ofFloat(
            0F,
            1F
        ).apply {
            duration = ANIMATION_LENGTH
            addUpdateListener {
                val value = it.animatedValue as Float
                ColorRegistry.getAllAppliers().forEach { it ->
                    it.applyColor(
                        currentPalette.get(it.token)
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

    fun addOnPaletteUpdateListener(l: OnPaletteUpdateListener) {
        onPaletteUpdateListeners.removeAll { it.id == l.id }
        onPaletteUpdateListeners.add(l)
    }

    fun removeOnPaletteUpdateListener(id: Int) {
        onPaletteUpdateListeners.removeAll { it.id == id }
    }

    interface OnPaletteUpdateListener {
        val id: Int
        fun onUpdate(oldPalette: ColorPalette, newPalette: ColorPalette)
    }

}