package org.foedusprogramme.hueflow.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.View
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.colorapplier.factory.ViewColorAppliersFactory
import org.foedusprogramme.hueflow.palette.ColorToken
import org.foedusprogramme.hueflow.palette.ColorToken.Companion.attrToTokenMapping

class HueFlowViewInflaterCallback : CallbackViewInflater.Callback {

    override fun onCreateView(
        context: Context,
        attrs: AttributeSet,
        result: View?
    ) {
        if (result == null) {
            return
        }
        var activity = context
        while (activity is ContextWrapper && activity !is Activity) {
            activity = activity.baseContext
        }
        if (activity !is Activity) {
            throw IllegalStateException("${activity.javaClass.name} is not an Activity")
        }
        HueFlow.factories.forEach { f ->
            if (f.viewClass.isInstance(result)) {
                @Suppress("UNCHECKED_CAST")
                val vf = f as ViewColorAppliersFactory<View>

                val attrValues =
                    vf.getMutableAttributes(result).mapNotNull { (attrName, ns) ->
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

                ColorRegistry.register(activity, f.build(result, attrToToken))
            }
        }
    }

}