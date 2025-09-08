package org.foedusprogramme.hueflow.colorapplier.factory

import android.view.View
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.palette.ColorToken

interface ViewColorAppliersFactory<in V : View> {
    val viewClass: Class<out View>

    fun getMutableAttributes(view: V): List<Pair<String, String>>
    fun build(view: V, attrToToken: Map<String, ColorToken>): List<ColorApplier>
}