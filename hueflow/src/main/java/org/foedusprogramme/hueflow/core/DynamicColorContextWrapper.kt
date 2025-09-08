package org.foedusprogramme.hueflow.core

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.ContextThemeWrapper
import org.foedusprogramme.hueflow.palette.ColorToken

class DynamicColorContextWrapper(
    base: Context
) : ContextThemeWrapper(base, 0) {

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        val typedValue = TypedValue()

        HueFlow.currentPalette?.get(ColorToken.Primary)?.also { color ->
            theme.resolveAttribute(
                androidx.appcompat.R.attr.colorControlActivated,
                typedValue,
                true
            )
            typedValue.data = color
        }

        return theme
    }
}
