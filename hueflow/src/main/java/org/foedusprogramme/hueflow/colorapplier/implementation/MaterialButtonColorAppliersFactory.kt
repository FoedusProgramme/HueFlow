package org.foedusprogramme.hueflow.colorapplier.implementation

import com.google.android.material.button.MaterialButton
import org.foedusprogramme.hueflow.NAMESPACE_ANDROID
import org.foedusprogramme.hueflow.NAMESPACE_APP
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.colorapplier.factory.ViewColorApplierBuilder
import org.foedusprogramme.hueflow.colorapplier.factory.ViewColorAppliersFactory
import org.foedusprogramme.hueflow.palette.ColorToken

class MaterialButtonColorAppliersFactory : ViewColorAppliersFactory<MaterialButton> {
    override val viewClass = MaterialButton::class.java

    override fun getMutableAttributes(view: MaterialButton): List<Pair<String, String>> {
        return listOf(
            "textColor" to NAMESPACE_ANDROID,
            "backgroundTint" to NAMESPACE_APP
        )
    }

    override fun build(view: MaterialButton, attrToToken: Map<String, ColorToken>): List<ColorApplier> {
        return ViewColorApplierBuilder(view).apply {
            attrToToken["textColor"]?.let { token ->
                attr(attribute = "textColor",
                    token = token) {
                    color -> setTextColor(color)
                }
            }
            attrToToken["backgroundTint"]?.let { token ->
                attr(attribute = "backgroundTint",
                    token = token) {
                    color -> setBackgroundColor(color)
                }
            }
        }.build()
    }
}