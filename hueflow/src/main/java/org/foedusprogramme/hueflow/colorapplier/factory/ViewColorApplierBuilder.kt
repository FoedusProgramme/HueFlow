package org.foedusprogramme.hueflow.colorapplier.factory

import android.view.View
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.palette.ColorToken

class ViewColorApplierBuilder<V : View>(private val view: V) {

    private val appliers = mutableListOf<ColorApplier>()

    fun attr(attribute: String, token: ColorToken, apply: V.(Int) -> Unit) {
        appliers.add(object : ColorApplier {
            override val viewId = view.id
            override val attribute = attribute
            override val token = token
            override fun applyColor(color: Int) {
                view.apply(color)
            }
        })
    }

    fun build(): List<ColorApplier> = appliers
}
