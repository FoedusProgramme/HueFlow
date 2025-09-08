package org.foedusprogramme.hueflow.colorapplier.implementation

import android.view.Window
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.palette.ColorToken

class WindowColorApplier(private val window: Window) : ColorApplier {
    override val viewId: Int = -1
    override val attribute: String = ""
    override val token = ColorToken.Surface
    override fun applyColor(color: Int) {
        window.decorView.setBackgroundColor(color)
    }
}