package org.foedusprogramme.hueflow.colorapplier.implementation

import com.google.android.material.button.MaterialButton
import org.foedusprogramme.hueflow.NAMESPACE_ANDROID
import org.foedusprogramme.hueflow.NAMESPACE_APP
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
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
        val list = mutableListOf<ColorApplier>()

        attrToToken["textColor"]?.let { token ->
            list.add(object : ColorApplier {
                override val viewId = view.id
                override val attribute: String = "textColor"
                override val token: ColorToken = token
                override fun applyColor(color: Int) { view.setTextColor(color) }
            })
        }

        attrToToken["backgroundTint"]?.let { token ->
            list.add(object : ColorApplier {
                override val viewId = view.id
                override val attribute: String = "backgroundTint"
                override val token: ColorToken = token
                override fun applyColor(color: Int) { view.setBackgroundColor(color) }
            })
        }

        return list
    }

}