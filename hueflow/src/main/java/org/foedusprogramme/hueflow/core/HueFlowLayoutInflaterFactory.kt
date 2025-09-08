package org.foedusprogramme.hueflow.core

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.colorapplier.factory.ViewColorAppliersFactory
import org.foedusprogramme.hueflow.colorapplier.implementation.MaterialButtonColorAppliersFactory
import org.foedusprogramme.hueflow.palette.ColorToken
import org.foedusprogramme.hueflow.palette.ColorToken.Companion.attrToTokenMapping

class HueFlowLayoutInflaterFactory(
    private val activity: Activity,
    customFactoryList: List<ViewColorAppliersFactory<*>> = listOf()
) : LayoutInflater.Factory2 {

    private val factories: List<ViewColorAppliersFactory<*>> =
        customFactoryList +
                listOf(
                    MaterialButtonColorAppliersFactory()
                )

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = try {
            LayoutInflater.from(context).createView(name, null, attrs)
        } catch (_: Exception) { null } ?: return null

        factories.forEach { f ->
            if (f.viewClass.isInstance(view)) {
                @Suppress("UNCHECKED_CAST")
                val vf = f as ViewColorAppliersFactory<View>

                val attrValues =
                    vf.getMutableAttributes(view).mapNotNull { (attrName, ns) ->
                        val valueStr = attrs.getAttributeValue(ns, attrName)
                        if (valueStr != null) {
                            if (valueStr.startsWith("?")) attrName to valueStr.substringAfter('?').toInt()
                            else null
                        } else null
                    }.toMap()

                val usedAttrs = attrValues.filter { (_, attrValue) ->
                    attrToTokenMapping.containsKey(attrValue)
                }

                val attrToToken: Map<String, ColorToken> = usedAttrs.mapValues { (_, resId) ->
                    attrToTokenMapping[resId]!!
                }

                ColorRegistry.register(activity, f.build(view, attrToToken))
            }
        }

        return view
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? =
        onCreateView(name, context, attrs)
}

